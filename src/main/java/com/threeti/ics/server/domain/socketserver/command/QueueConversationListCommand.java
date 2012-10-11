package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.QueueConversationsRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 18/09/12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class QueueConversationListCommand extends AbstractCommand implements Command {
    private static final String RESULT_CONVERSATION_LIST_KEY = "conversations";
    private static final String QUEUE_NAME = "queueName";
    @Autowired
    private ConversationDao conversationDao;
    @Autowired
    private QueueController queueController;


    public QueueConversationListCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        QueueConversationsRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), QueueConversationsRequest.class);
        List<Conversation> conversations = conversationDao.findBy(request.getActualQueueName(), request.getPageFrom(), request.getPageSize(), request.getOrder());
        for (Conversation c : conversations) {
            c.setUnreadMessageCount(queueController.sizeOf(KeyUtils.unreadConversationMessage(c.getId(), request.getCustomerServiceUserName())));
        }
        addResult(RESULT_CONVERSATION_LIST_KEY, conversations);
        addResult(QUEUE_NAME, request.getQueueName());
        session.write(new ResponseBuilder(this).getResponse());
    }
}
