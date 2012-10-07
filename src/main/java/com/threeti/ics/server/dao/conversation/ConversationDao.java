package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.dao.message.MessageDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "conversationDao")
public class ConversationDao extends AbstractGenericDaoImpl<Conversation> implements GenericDao<Conversation> {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    public ConversationDao() {
        super(Conversation.class);
    }

    protected Long getId(Conversation conv) {
        String id = template.opsForValue().get(KeyUtils.conversationProductIdAndVisitorToken(conv.getTopic().getProductId(), conv.getVisitor()));
        if (id != null) return Long.valueOf(id);
        if (conv.getId() == null) {
            return idCounter.incrementAndGet();
        }
        return conv.getId();
    }

    private ConversationTopic createConversationTopic(String topicId) {
        ConversationTopic result = new ConversationTopic();
        result.setId(Long.valueOf(topicId));
        final String k = KeyUtils.conversationTopic(topicId);
        result.setProductId(template.opsForHash().get(k, "productId").toString());
        result.setDescription(template.opsForHash().get(k, "description").toString());
        result.setProductName(template.opsForHash().get(k, "productName").toString());
        result.setPicture(template.opsForHash().get(k, "picture").toString());
        return result;

    }

    @Override
    @SuppressWarnings("unchecked")
    public Conversation create(Conversation conv) {
        final Long id = getId(conv);
        if (template.opsForValue().get(KeyUtils.conversationProductIdAndVisitorToken(conv.getTopic().getProductId(), conv.getVisitor())) != null) {
            return findBy(id);
        }
        conv.setId(id);
        template.opsForHash().put(getIdKey(id), "id", id);
        template.opsForHash().put(getIdKey(id), "topic", conv.getTopic().getId().toString());
        template.opsForHash().put(getIdKey(id), "visitor", conv.getVisitor());
        template.opsForHash().put(getIdKey(id), "visitorName", conv.getVisitorName());
        template.opsForHash().put(getIdKey(id), "customerServiceUser", conv.getCustomerServiceUsername());
        template.opsForHash().put(getIdKey(id), "status", conv.getStatus().toString());
        template.opsForHash().put(getIdKey(id), "createDate", String.valueOf(conv.getCreateDate().getTime()));
        template.opsForValue().set(KeyUtils.conversationProductIdAndVisitorToken(conv.getTopic().getProductId(), conv.getVisitor()), id.toString());
        return conv;
    }

    public Conversation findBy(Long id) {
        Conversation result = new Conversation();
        result.setId(id);
        result.setVisitor(template.opsForHash().get(getIdKey(id), "visitor").toString());
        result.setVisitorName(template.opsForHash().get(getIdKey(id), "visitorName").toString());
        result.setStatus(ConversationStatus.valueOf(template.opsForHash().get(getIdKey(id), "status").toString()));
        Date d = new Date(Long.valueOf(template.opsForHash().get(getIdKey(id), "createDate").toString()));
        String topicId = template.opsForHash().get(getIdKey(id), "topic").toString();
        result.setTopic(createConversationTopic(topicId));
        String userName = template.opsForHash().get(getIdKey(id), "customerServiceUser").toString();
        if (StringUtils.isNotEmpty(userName)) {
            result.setCustomerServiceUser(customerServiceUserDao.findBy(userName));
        }
        result.setCreateDate(d);
        return result;
    }

    public List<Conversation> findBy(final String queueName, final int pageFrom, final int pageSize, String order) {
        List<Conversation> result = new ArrayList<Conversation>();
        List<String> sort = template.sort(SortQueryBuilder.sort(queueName).order(SortParameters.Order.valueOf(StringUtils.upperCase(order))).limit(pageFrom, pageSize).build());
        for (String covId : sort) {
            Conversation c = findBy(Long.valueOf(covId));
            String messageQueue = KeyUtils.conversationMessage(Long.valueOf(covId));
            for (String msgId : template.opsForList().range(messageQueue, 0, 0)) {
                c.addMessage(messageDao.findBy(Long.valueOf(msgId)));
            }
            result.add(c);
        }
        return result;
    }

    public Conversation findByWithMessage(final Long convId) {
        Conversation result = findBy(convId);
        String messageQueue = KeyUtils.conversationMessage(convId);
        for (String msgId : template.opsForList().range(messageQueue, 0, 0)) {
            result.addMessage(messageDao.findBy(Long.valueOf(msgId)));
        }
        return result;
    }

    private Set<String> getConversationsWithMessage() {
        Set<String> result = new HashSet<String>();
        for (String k : template.keys("conversation:*:messages")) {
            result.add(StringUtils.substringBetween(k, ":"));
        }
        return result;
    }

    public List<Conversation> findByVisitor(String visitor, int pageFrom, int pageSize) {
        List<Conversation> result = new ArrayList<Conversation>();
        for (String k : template.keys(KeyUtils.conversationProductIdAndVisitorKey(visitor))) {
            template.opsForSet().add(KeyUtils.conversationVisitor(visitor), template.opsForValue().get(k));
        }
        template.opsForSet().intersectAndStore(KeyUtils.conversationVisitor(visitor), getConversationsWithMessage(), KeyUtils.conversationVisitor(visitor));
        List<String> sort = template.sort(SortQueryBuilder.sort(KeyUtils.conversationVisitor(visitor)).order(SortParameters.Order.DESC).limit(pageFrom, pageSize).build());
        for (String covId : sort) {
            Conversation c = findBy(Long.valueOf(covId));
            String messageQueue = KeyUtils.conversationMessage(Long.valueOf(covId));
            for (String msgId : template.opsForList().range(messageQueue, 0, 0)) {
                c.addMessage(messageDao.findBy(Long.valueOf(msgId)));
                result.add(c);
            }
        }
        return result;
    }

    public void updateCustomerServiceUserNameAndStatus(List<Long> conversationKeys, String customerServiceUserName, ConversationStatus status) {
        for (Long conversationId : conversationKeys) {
            template.opsForHash().put(getIdKey(conversationId), "status", status.toString());
            template.opsForHash().put(getIdKey(conversationId), "customerServiceUser", customerServiceUserName);
        }
    }

    public void updateStatus(Long conversationId, ConversationStatus status) {
        template.opsForHash().put(getIdKey(conversationId), "status", status.toString());
    }

    public void updateCustomerServiceUserName(Long conversationId, String customerServiceUserName) {
        template.opsForHash().put(getIdKey(conversationId), "customerServiceUser", customerServiceUserName);
    }
}
