package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.messagequeue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SuspendSessionHandler extends AbstractSessionHandler implements SessionHandler {
    @Autowired
    private QueueController queueController;

    @Override
    protected ConversationStatus getStatus() {
        return ConversationStatus.SUSPENDED;
    }

    @Override
    public Map<String, Object> handle(SessionOperationRequest request, IoSession session) {
        queueController.doSuspend(request);
        return super.handle(request, session);
    }
}
