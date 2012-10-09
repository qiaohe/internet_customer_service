package com.threeti.ics.server.domain.messagequeue;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.queue.QueueDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import com.threeti.ics.server.listener.QueueChangeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 17/09/12
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "queueController")
public class QueueController {
    private static final String PUSH_QUEUE = "pushQueue";
    @Autowired
    private QueueDao queueDao;
    @Autowired
    private ConversationDao conversationDao;
    @Autowired
    private RedisTemplate<String, String> template;

    public void doSuspend(SessionOperationRequest request) {
        move(QueueType.SESSION, QueueType.ACTIONREQUIRED, request);
    }

    public void doAccept(SessionOperationRequest request) {
        if (request.isFromPublicQueueWithinAcceptAndAppoint()) {
            move(QueueType.PUBLIC, QueueType.SESSION, request);
        } else {
            move(QueueType.CUSTOMERMESSAGE, QueueType.PUBLIC, request);
        }
        template.convertAndSend(PUSH_QUEUE, ObjectJsonMapper.getJsonStringBy(createQueueChangeMessage(QueueType.CUSTOMERMESSAGE.getQueueName(null),
                request.getConversationId(), QueueChangeMessage.OperationTypeEnum.REMOVE)));
    }

    public void doTerminate(SessionOperationRequest request) {
        for (Long conversationId : request.getConversationIds()) {
            if (request.isFromSessionQueueWithinTerminate()) {
                queueDao.remove(QueueType.SESSION.getQueueName(request.getCustomerService()), conversationId);
            } else {
                queueDao.remove(QueueType.ACTIONREQUIRED.getQueueName(request.getCustomerService()), conversationId);
            }
        }
    }

    public void doSessionTransfer(SessionOperationRequest request) {
        queueDao.move(QueueType.SESSION.getQueueName(request.getFromCustomerServiceUserName()),
                QueueType.SESSION.getQueueName(request.getCustomerService()), request.getConversationId());
    }

    public void doResume(SessionOperationRequest request) {
        move(QueueType.SESSION, QueueType.ACTIONREQUIRED, request);
    }

    public QueueChangeMessage createQueueChangeMessage(final String queueName, final Long conId, QueueChangeMessage.OperationTypeEnum operationType) {
        QueueChangeMessage result = new QueueChangeMessage(queueName, conId);
        result.setLength(sizeOf(queueName));
        result.setOperationType(operationType);
        return result;
    }

    public HashMap<String, Long> getSummaries(String customerServiceUserName) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        result.put(QueueType.PUBLIC.getName(), sizeOf(QueueType.PUBLIC.getQueueName(null)));
        result.put(QueueType.SESSION.getName(), sizeOf(QueueType.SESSION.getQueueName(customerServiceUserName)));
        result.put(QueueType.ACTIONREQUIRED.getName(), sizeOf(QueueType.ACTIONREQUIRED.getQueueName(customerServiceUserName)));
        result.put(QueueType.CUSTOMERMESSAGE.getName(), sizeOf(QueueType.CUSTOMERMESSAGE.getQueueName(null)));
        return result;
    }

    private boolean isCallBackWithOriginalCustomerService(Conversation conversation) {
        return conversation.isInTerminated() && SessionManager.getInstance().getSessionByCustomerService(conversation.getCustomerServiceUsername()) != null;
    }

    private void doJoin(String queueName, Conversation conversation) {
        queueDao.add(queueName, conversation.getId());
        conversationDao.updateStatus(conversation.getId(), conversation.getStatus());
    }

    public void doDispatch(Conversation conversation) {
        if (isCallBackWithOriginalCustomerService(conversation)) {
            conversation.setStatus(ConversationStatus.ACCEPTED);
            doJoin(QueueType.SESSION.getQueueName(conversation.getCustomerServiceUsername()), conversation);
        } else {
            conversation.setStatus(ConversationStatus.NOTACCEPTED);
            if (SessionManager.getInstance().hasOnlineCustomerService()) {
                doJoin(QueueType.PUBLIC.getQueueName(null), conversation);
                template.convertAndSend(PUSH_QUEUE, ObjectJsonMapper.getJsonStringBy(createQueueChangeMessage(QueueType.PUBLIC.getQueueName(null),
                        conversation.getId(), QueueChangeMessage.OperationTypeEnum.ADD)));
            } else {
                doJoin(QueueType.CUSTOMERMESSAGE.getQueueName(null), conversation);
                template.convertAndSend(PUSH_QUEUE, ObjectJsonMapper.getJsonStringBy(createQueueChangeMessage(QueueType.CUSTOMERMESSAGE.getQueueName(null),
                        conversation.getId(), QueueChangeMessage.OperationTypeEnum.ADD)));
            }
        }
    }

    public Long sizeOf(final String queueName) {
        return queueDao.sizeOf(queueName);
    }

    private void move(QueueType source, QueueType dest, SessionOperationRequest request) {
        for (Long conversationId : request.getConversationIds()) {
            String userName = request.getCustomerService();
            String sourceQueue = source.getQueueName(userName);
            String destQueue = dest.getQueueName(userName);
            queueDao.move(sourceQueue, destQueue, conversationId);

        }
    }
}
