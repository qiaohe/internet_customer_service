package com.threeti.ics.server.domain.protocoldefinition.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private String id;
    private Long conversationId;
    private String from;
    private String to;
    private Date date;
    private String messageBody;
    private MessageStatus status;
    private String type;
    private String dateString;

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JsonIgnore
    public Date getDate() {
        return date;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    @JsonIgnore
    public boolean inPublicQueue() {
        return StringUtils.isEmpty(this.to);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public String getVisitor() {
        if (!StringUtils.isEmpty(from) && from.length() == 32) return this.from;
        if (!StringUtils.isEmpty(to) && to.length() == 32) return this.to;
        return null;
    }

    @JsonIgnore
    public String getCustomerServiceUser() {
        if (!StringUtils.isEmpty(from) && from.length() == 10) return this.from;
        if (!StringUtils.isEmpty(to) && to.length() == 10) return this.to;
        return null;
    }

    @JsonIgnore
    public boolean isOutgoing() {
        return StringUtils.isNotEmpty(from) && from.length() == 10;
    }
}
