package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.messagequeue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.socketserver.command.MessageTransferCommand;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import com.threeti.ics.server.listener.OperationTypeEnum;
import com.threeti.ics.server.listener.PushMessage;
import com.threeti.ics.server.listener.QueueChangeMessage;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 22/09/12
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SessionTransferHandler extends AbstractSessionHandler implements SessionHandler {
    @Autowired
    private QueueController queueController;
    @Autowired
    private ConversationDao conversationDao;


    @Override
    protected ConversationStatus getStatus() {
        return ConversationStatus.TERMINATED;
    }

    private void sendCustomerServiceChangeWelcomeMessage(SessionOperationRequest request, IoSession session) {
        String visitor = conversationDao.findBy(request.getConversationId()).getVisitor();
        Message message = request.getWelcomeMessage();
        message.setTo(visitor);
        new MessageTransferCommand(ObjectJsonMapper.getJsonStringBy(message)).execute(session);
    }

    public QueueChangeMessage createQueueChangMessage(SessionOperationRequest request) {
        return queueController.createQueueChangeMessage(request.getCustomerService(),
                request.getConversationId(),
                OperationTypeEnum.ADD);
    }


    @Override
    public Map<String, Object> handle(SessionOperationRequest request, IoSession session) {
        queueController.doSessionTransfer(request);
        conversationDao.updateCustomerServiceUserName(request.getConversationId(), request.getCustomerService());
        IoSession so = SessionManager.getInstance().getSessionByCustomerService(request.getCustomerService());
        if (so != null) {
            so.write(PushMessage.valueOf(createQueueChangMessage(request)).toJsonString());
            sendCustomerServiceChangeWelcomeMessage(request, so);
        }
        return super.handle(request, session);
    }
}