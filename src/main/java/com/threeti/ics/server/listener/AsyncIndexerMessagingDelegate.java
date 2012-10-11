package com.threeti.ics.server.listener;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.conversation.ConversationTopicDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.search.Indexer;
import com.threeti.ics.server.search.MessageItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 26/09/12
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public class AsyncIndexerMessagingDelegate implements MessageDelegate {
    @Autowired
    private Indexer indexer;

    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private ConversationTopicDao conversationTopicDao;

    @Override
    public void handleMessage(String message) throws IOException {
        Message m = ObjectJsonMapper.getObjectBy(message, Message.class);
        Conversation conversation = conversationDao.findBy(m.getConversationId());
        String topic = conversationTopicDao.findBy(conversation.getProductId()).getProductName();
        MessageItem item = new MessageItem(m, topic, conversation.getVisitorName());
        indexer.index(item);
    }
}
