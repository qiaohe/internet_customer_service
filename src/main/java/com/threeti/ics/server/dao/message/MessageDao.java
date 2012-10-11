package com.threeti.ics.server.dao.message;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.protocoldefinition.message.MessageStatus;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "messageDao")
public class MessageDao extends AbstractGenericDaoImpl<Message> implements GenericDao<Message> {

    public MessageDao() {
        super(Message.class);
    }

    protected MessageDao(Class<Message> type) {
        super(type);
    }

    protected Long getId(Message message) {
        return idCounter.incrementAndGet();
    }
    protected String getIdKey(final String id) {
        return getTypeName() + ":" + id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message create(Message message) {
        final Long cid = message.getConversationId();
        Date d = new Date();
        message.setDate(d);
        message.setDateString(Constants.getDateString(d));
        template.opsForHash().putAll(getIdKey(message.getId()), getProperties(message));
        template.opsForList().leftPush(KeyUtils.conversationMessage(cid), message.getId());
        template.convertAndSend("asyncIndexer", ObjectJsonMapper.getJsonStringBy(message));
        return message;
    }

    public List<Message> findBy(final Long conversationId, final int pageFrom, final int pageSize) {
        List<String> messageIds = template.sort(SortQueryBuilder.sort(KeyUtils.conversationMessage(conversationId)).noSort().
                order(SortParameters.Order.DESC).limit(pageFrom, pageSize).build());
        List<Message> result = new ArrayList<Message>();
        for (String messageId : messageIds) {
            result.add(findBy(messageId));
        }
        return result;
    }

    public Message findBy(String id) {
        Message result = new Message();
        result.setId(id);
        result.setFrom(template.opsForHash().get(getIdKey(id), "from").toString());
        result.setTo(template.opsForHash().get(getIdKey(id), "to").toString());
        result.setConversationId(Long.valueOf(template.opsForHash().get(getIdKey(id), "conversationId").toString()));
        Date d = new Date(Long.valueOf(template.opsForHash().get(getIdKey(id), "date").toString()));
        result.setDate(d);
        result.setDateString(Constants.getDateString(d));
        result.setMessageBody(template.opsForHash().get(getIdKey(id), "messageBody").toString());
        result.setStatus(MessageStatus.valueOf(template.opsForHash().get(getIdKey(id), "status").toString()));
        result.setType(template.opsForHash().get(getIdKey(id), "type").toString());
        return result;
    }

    public void updateStatus(String[] messageIds, String status) {
        for (String messageId : messageIds) {
            template.opsForHash().put(getIdKey(messageId), "status", status);
        }
    }
}
