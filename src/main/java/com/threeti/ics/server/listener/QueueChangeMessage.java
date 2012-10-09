package com.threeti.ics.server.listener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/5/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class QueueChangeMessage {
    @Autowired
    @JsonIgnore
    private ConversationDao conversationDao;
    private String queueName;
    private OperationTypeEnum operationType;
    private long length;
    private Long conversationId;

    public QueueChangeMessage() {
    }

    public QueueChangeMessage(String queueName, Long conversationId) {
        this();
        this.queueName = queueName;
        this.conversationId = conversationId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    @JsonIgnore
    public Conversation getConversation() {
        return conversationDao.findBy(conversationId);
    }

    public static enum OperationTypeEnum {
        ADD, REMOVE
    }
}
