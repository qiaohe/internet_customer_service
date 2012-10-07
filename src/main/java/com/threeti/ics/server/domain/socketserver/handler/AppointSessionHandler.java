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
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class AppointSessionHandler extends AbstractSessionHandler implements SessionHandler {
    //should be removed
    private static final String[] OUT_PUT_KEYS = new String[]{"customerServiceUserName", "conversationId"};
    @Autowired
    private QueueController queueController;

    protected ConversationStatus getStatus() {
        return ConversationStatus.APPOINTED;
    }

    @Override
    public Map<String, Object> handle(SessionOperationRequest request, IoSession session) {
        queueController.doAccept(request);
        addResult(OUT_PUT_KEYS[0], request.getCustomerService());
        addResult(OUT_PUT_KEYS[1], request.getConversationId());
        return super.handle(request, session);
    }
}
