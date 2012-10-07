package com.threeti.ics.server.listener;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.queue.QueueDao;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.CustomerServiceUserStatusChangeRequest;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceStatusChangedMessagingDelegate implements MessageDelegate {
    @Autowired
    private QueueDao queueDao;

    @Override
    @SuppressWarnings("unchecked")
    public void handleMessage(String message) {
        CustomerServiceUserStatusChangeRequest request = ObjectJsonMapper.getObjectBy(message, CustomerServiceUserStatusChangeRequest.class);
        final List<String> visitors = queueDao.getVisitors(request.getCustomerServiceUserName());
        for (String v : new HashSet<String>(visitors)) {
            SessionManager.getInstance().getSessionByVisitor(v).write(request.toJsonString());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleMessage(Map message) {
    }

    @Override
    public void handleMessage(byte[] message) {
    }

    @Override
    public void handleMessage(Serializable message) {


    }

    @Override
    public void handleMessage(Serializable message, String channel) {
    }

    public static void main(String[] args) {

    }
}
