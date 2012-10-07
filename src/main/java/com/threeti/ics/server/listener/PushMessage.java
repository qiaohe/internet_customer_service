package com.threeti.ics.server.listener;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 19/09/12
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class PushMessage {
    private final String type;
    private Map<String, Object> data = new HashMap<String, Object>();
    private Date createDate = new Date();

    public PushMessage(String type) {
        this.type = type;
    }

    public PushMessage append(final String key, final Object value) {
        data.put(key, value);
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String toJsonString() {
        return ObjectJsonMapper.getJsonStringBy(this);
    }

    public static PushMessage valueOf(QueueChangeMessage message) {
        return new PushMessage("queueChanged")
                .append("queueName", message.getQueueName())
                .append("operation", message.getOperationType())
                .append("length", message.getLength())
                .append("conversation", message.getConversation());
    }


}
