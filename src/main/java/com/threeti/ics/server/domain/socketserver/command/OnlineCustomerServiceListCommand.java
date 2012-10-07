package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 14/09/12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class OnlineCustomerServiceListCommand extends AbstractCommand implements Command {
    private static final String RESULT_CUSTOMER_SERVICE_KEY = "customerServiceUsers";

    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    public OnlineCustomerServiceListCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        List<CustomerServiceUser> users = new ArrayList<CustomerServiceUser>();
        for (CustomerServiceUser u : SessionManager.getInstance().getCustomerServiceUsers()) {
            users.add(customerServiceUserDao.findBy(u.getUserName()));
        }
        addResult(RESULT_CUSTOMER_SERVICE_KEY, users);
        session.write(new ResponseBuilder(this).getResponse());
    }
}
