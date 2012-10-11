package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.dao.message.MessageDao;
import com.threeti.ics.server.dao.queue.QueueDao;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class MessageTransferCommand extends AbstractCommand implements Command {
    private static final String RESULT_MESSAGE_KEY = "message";
    private static final String RESULT_MESSAGE_BROADCAST_KEY = "broadcastMessage";
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private QueueController queueController;
    @Autowired
    private QueueDao queueDao;
    @Autowired
    private ConversationDao conversationDao;

    public MessageTransferCommand(Object request) {
        super(request);
    }

    private IoSession getDestinationSession(Message message) {
        if (message.isOutgoing())
            return SessionManager.getInstance().getSessionByVisitor(message.getTo());
        return SessionManager.getInstance().getSessionByCustomerService(message.getCustomerServiceUser());
    }

    private Message createMessage() {
        final Message msg = ObjectJsonMapper.getObjectBy(getRequestAsString(), Message.class);
        if (StringUtils.isEmpty(msg.getFrom())) throw new IllegalStateException("message from can not be null.");
        if (msg.getConversationId() == null)
            throw new IllegalStateException("Conversation id can not be null for message" + msg.getId());
        return messageDao.create(msg);
    }

    @Override
    public void execute(IoSession session) {
        final Message message = createMessage();
        if (message.inPublicQueue()) {
            addResult(RESULT_MESSAGE_BROADCAST_KEY, message);
            SessionManager.getInstance().broadcast(getResults());
            return;
        }
        queueDao.add(KeyUtils.unreadConversationMessage(message.getConversationId(), message.getTo()), message.getId());
        if (!message.isOutgoing()) {
            Conversation conversation = conversationDao.findBy(message.getConversationId());
            if (conversation.isInTerminated())
                queueController.doDispatch(conversation);
        }
        IoSession destinationSession = getDestinationSession(message);
        if (destinationSession != null) {
            addResult(RESULT_MESSAGE_KEY, message);
            destinationSession.write(getResults());
        }
    }
}
