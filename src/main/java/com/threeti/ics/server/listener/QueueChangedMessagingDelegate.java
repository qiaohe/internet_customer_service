package com.threeti.ics.server.listener;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 19/09/12
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class QueueChangedMessagingDelegate implements MessageDelegate {
    @Autowired
    private ConversationDao conversationDao;

    @Override
    public void handleMessage(String message) {
    }

    @Override
    public void handleMessage(Map message) {
    }

    @Override
    public void handleMessage(byte[] message) {
    }

    @Override
    public void handleMessage(Serializable message) {
        QueueChangeMessage msg = (QueueChangeMessage) message;
        for (IoSession session : SessionManager.getInstance().getCustomerServiceSessions())
            session.write(PushMessage.valueOf(msg));
    }

    @Override
    public void handleMessage(Serializable message, String channel) {
    }


}
