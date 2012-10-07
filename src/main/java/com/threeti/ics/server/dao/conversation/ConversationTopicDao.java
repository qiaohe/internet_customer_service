package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationTopic;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "conversationTopicDao")
public class ConversationTopicDao extends AbstractGenericDaoImpl<ConversationTopic> implements GenericDao<ConversationTopic> {

    public ConversationTopicDao() {
        super(ConversationTopic.class);
    }

    protected Long getId(ConversationTopic topic) {
        String id = template.opsForValue().get(KeyUtils.topicProductId(topic.getProductId()));
        if (id != null) return Long.valueOf(id);
        return topic.getId() == null ? idCounter.incrementAndGet() : topic.getId();
    }

    @SuppressWarnings("unchecked")
    public ConversationTopic create(ConversationTopic topic) {
        final Long id = getId(topic);
        topic.setId(id);
        template.opsForHash().putAll(getIdKey(id), getProperties(topic));
        template.opsForValue().set(KeyUtils.topicProductId(topic.getProductId()), id.toString());
        entities.addFirst(id.toString());
        return topic;
    }

    public ConversationTopic findBy(final String productId) {
        final String id = template.opsForValue().get(KeyUtils.topicProductId(productId));
        return findBy(Long.valueOf(id));
    }

    @Override
    public ConversationTopic findBy(Long id) {
        ConversationTopic result = new ConversationTopic();
        result.setId(id);
        result.setProductId(template.opsForHash().get(getIdKey(id), "productId").toString());
        result.setDescription(template.opsForHash().get(getIdKey(id), "description").toString());
        result.setProductName(template.opsForHash().get(getIdKey(id), "productName").toString());
        result.setPicture(template.opsForHash().get(getIdKey(id), "picture").toString());
        return result;
    }
}
