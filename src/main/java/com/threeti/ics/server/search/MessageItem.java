package com.threeti.ics.server.search;

import com.threeti.ics.server.domain.protocoldefinition.message.Message;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {
    private Message message;
    private String topic;
    private String visitor;

    public MessageItem(Message message, String topic, String visitor) {
        this.topic = topic;
        this.message = message;
        this.visitor = visitor;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }
}
