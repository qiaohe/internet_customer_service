package com.threeti.ics.server.domain.messagequeue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.threeti.ics.server.common.I18NMessages;
import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.domain.protocoldefinition.SessionOperationType;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.protocoldefinition.message.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SessionOperationRequest {
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    public static final String FROM_KEY_WITHIN_ADDITIONAL = "from";
    private String customerServiceUserName;
    private List<Long> conversationIds = new ArrayList<Long>();
    private SessionOperationType operationType;
    private Map<String, String> additional = new HashMap<String, String>();

    public SessionOperationRequest() {
    }

    public SessionOperationRequest(String customerService, SessionOperationType operationType) {
        this.customerServiceUserName = customerService;
        this.operationType = operationType;
    }

    public String getCustomerService() {
        return customerServiceUserName;
    }

    public void setCustomerServiceUserName(String customerServiceUserName) {
        this.customerServiceUserName = customerServiceUserName;
    }

    public SessionOperationType getOperationType() {
        return operationType;
    }

    public boolean isSessionBasedOperation() {
        return !(SessionOperationType.ACCEPT).equals(operationType)
                && !(SessionOperationType.APPOINT).equals(operationType);
    }

    public void setOperationType(SessionOperationType operationType) {
        this.operationType = operationType;
    }

    public List<Long> getConversationIds() {
        return conversationIds;
    }

    public void setConversationIds(List<Long> conversationIds) {
        this.conversationIds = conversationIds;
    }

    public Map<String, String> getAdditional() {
        return additional;
    }

    public void setAdditional(Map<String, String> additional) {
        this.additional = additional;
    }

    @JsonIgnore
    public Long getConversationId() {
        return getConversationIds().get(0);
    }

    @JsonIgnore
    public String getFromCustomerServiceUserName() {
        if (!additional.containsKey("from")) return null;
        return additional.get("from");
    }


    @JsonIgnore
    public boolean isFromSessionQueueWithinTerminate() {
        return SessionOperationType.TERMINATE.equals(operationType)
                && QueueType.SESSION.getName().equals(additional.get(FROM_KEY_WITHIN_ADDITIONAL));
    }

    @JsonIgnore
    public boolean isFromPublicQueueWithinAcceptAndAppoint() {
        return QueueType.PUBLIC.getName().equalsIgnoreCase(additional.get(FROM_KEY_WITHIN_ADDITIONAL));
    }

    @JsonIgnore
    public String getValueOfAdditionalFromKey() {
        if (additional.containsKey(FROM_KEY_WITHIN_ADDITIONAL))
            return additional.get(FROM_KEY_WITHIN_ADDITIONAL);
        return null;
    }

    @JsonIgnore
    public Message getWelcomeMessage() {
        Long conversationId = getConversationId();
        if (conversationId == null)
            throw new IllegalStateException("Conversation id can not be null in welcome Message");
        Message result = new Message();
        result.setConversationId(conversationId);
        result.setDate(new Date());
        result.setFrom(getCustomerService());
        result.setType("text");
        result.setStatus(MessageStatus.SENT);
        I18NMessages.getMessage("global.welcome", customerServiceUserDao.findBy(getCustomerService()).getNickName());
        return result;
    }
}
