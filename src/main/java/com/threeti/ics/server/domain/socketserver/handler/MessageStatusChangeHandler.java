package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.dao.message.MessageDao;
import com.threeti.ics.server.dao.queue.QueueDao;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.MessageStatusChangeRequest;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.protocoldefinition.message.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/10/12
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "messageStatusChangeHandler")
public class MessageStatusChangeHandler {
    @Autowired
    private QueueDao queueDao;

    @Autowired
    private MessageDao messageDao;

    public void handle(MessageStatusChangeRequest request) {
        Message message = messageDao.findBy(request.getMessageIdArray()[0]);
        Long conversationId = message.getConversationId();
        String msgTo = message.getTo();
        final String qm = KeyUtils.unreadConversationMessage(conversationId, msgTo);
        for (String messageId : request.getMessageIdArray()) {
            if (MessageStatus.NOTREAD.name().equals(request.getStatus())) {
                queueDao.add(qm, messageId);
            } else if (MessageStatus.READ.name().equals(request.getStatus())) {
                queueDao.remove(qm, messageId);
            }
        }
    }
}
