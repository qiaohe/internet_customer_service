package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
public class MessageStatusChangeRequest {
    private static final String ID_SEPERATOR = ",";
    private String messageIds;
    private String status;
    private String from;
    private String to;

    public String getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(String messageIds) {
        this.messageIds = messageIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JsonIgnore
    public String[] getMessageIdArray() {
        return StringUtils.split(messageIds, ID_SEPERATOR);
    }
}
