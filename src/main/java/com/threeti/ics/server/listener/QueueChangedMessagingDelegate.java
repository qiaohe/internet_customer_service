package com.threeti.ics.server.listener;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;

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
    @Override
    public void handleMessage(String message) {
        QueueChangeMessage msg = ObjectJsonMapper.getObjectBy(message, QueueChangeMessage.class);
        for (IoSession session : SessionManager.getInstance().getCustomerServiceSessions())
            session.write(ObjectJsonMapper.getJsonStringBy(PushMessage.valueOf(msg)));
    }
}
