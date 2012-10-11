package com.threeti.ics.server.domain.socketserver.server;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.threeti.ics.server.common.Constants.CUSTOMER_SERVICE_USER;
import static com.threeti.ics.server.common.Constants.VISITOR_USER;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private IoService service;

    private SessionManager() {
    }


    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public void setService(IoService service) {
        this.service = service;
    }

    public List<CustomerServiceUser> getCustomerServiceUsers() {
        List<CustomerServiceUser> result = new ArrayList<CustomerServiceUser>();
        for (IoSession session : service.getManagedSessions().values()) {
            Object u = session.getAttribute(CUSTOMER_SERVICE_USER);
            if (u != null && u instanceof CustomerServiceUser) {
                result.add((CustomerServiceUser) u);
            }
        }
        return result;
    }

    public List<IoSession> getCustomerServiceSessions() {
        List<IoSession> result = new ArrayList<IoSession>();
        for (IoSession s : service.getManagedSessions().values()) {
            Object u = s.getAttribute(CUSTOMER_SERVICE_USER);
            if (u != null && u instanceof CustomerServiceUser) {
                result.add(s);
            }
        }
        return result;
    }

    public IoSession getSessionByVisitor(String serviceToken) {
        for (IoSession s : service.getManagedSessions().values()) {
            Object u = s.getAttribute(VISITOR_USER);
            if (u != null && serviceToken.equals(u.toString())) {
                return s;
            }
        }
        return null;
    }

    public boolean hasOnlineCustomerService() {
        return !CollectionUtils.isEmpty(getCustomerServiceUsers());
    }

    public IoSession getSessionByCustomerService(String customerServiceUsername) {
        for (IoSession session : service.getManagedSessions().values()) {
            Object u = session.getAttribute(CUSTOMER_SERVICE_USER);
            if (u != null && u instanceof CustomerServiceUser) {
                if (customerServiceUsername.equalsIgnoreCase(((CustomerServiceUser) u).getUserName())) {
                    return session;
                }
            }
        }
        return null;
    }

    public IoSession getSession(final String destination) {
        if (!StringUtils.isEmpty(destination) && destination.length() == 10) {
            return getSessionByCustomerService(destination);
        }
        return getSessionByVisitor(destination);
    }

    public void broadcast(final String message) {
        for (IoSession session : getCustomerServiceSessions()) {
            session.write(message);
        }
    }
}