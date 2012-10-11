package com.threeti.ics.server.domain.protocoldefinition.conversation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.dao.identity.ServiceTokenDao;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class Conversation {
    @Autowired
    private ServiceTokenDao serviceTokenDao;
    private Long id;
    private CustomerServiceUser customerServiceUser;
    private String visitor;
    private String visitorName;
    private ConversationTopic topic;
    private ConversationStatus status;
    private Date createDate;
    private List<Message> messages = new ArrayList<Message>();
    private long unreadMessageCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitor() {
        return visitor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public CustomerServiceUser getCustomerServiceUser() {
        return customerServiceUser;
    }

    public void setCustomerServiceUser(CustomerServiceUser customerServiceUser) {
        this.customerServiceUser = customerServiceUser;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public ConversationTopic getTopic() {
        return topic;
    }

    public void setTopic(ConversationTopic topic) {
        this.topic = topic;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    public void addMessage(Message message) {
        if (!messages.contains(message)) {
            messages.add(message);
        }
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public void removeMessage(Message message) {
        if (messages.contains(message)) {
            messages.add(message);
        }
    }

    public String getVisitorName() {
        return "V" + serviceTokenDao.findBy(visitor);
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }


    @JsonIgnore
    public String getProductId() {
        return getTopic().getProductId();
    }

    @JsonIgnore
    public String getCustomerServiceUsername() {
        return this.getCustomerServiceUser() == null ? StringUtils.EMPTY : getCustomerServiceUser().getUserName();
    }

    @JsonIgnore
    public boolean isInTerminated() {
        return StringUtils.isNotEmpty(getCustomerServiceUsername()) && ConversationStatus.TERMINATED.equals(getStatus());
    }

    public long getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(long unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }
}
