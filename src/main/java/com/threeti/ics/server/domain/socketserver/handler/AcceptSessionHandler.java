package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.messagequeue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.socketserver.command.MessageTransferCommand;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class AcceptSessionHandler extends AppointSessionHandler implements SessionHandler {


    public AcceptSessionHandler() {
    }

    @Override
    protected ConversationStatus getStatus() {
        return ConversationStatus.ACCEPTED;
    }

    @Override
    public Map<String, Object> handle(SessionOperationRequest request, IoSession session) {
        Message message = request.getWelcomeMessage();
        String visitor = conversationDao.findBy(message.getConversationId()).getVisitor();
        message.setTo(visitor);
        new MessageTransferCommand(ObjectJsonMapper.getJsonStringBy(message)).execute(session);
        return super.handle(request, session);
    }
}

