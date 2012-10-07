package com.threeti.ics.server.service;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.search.Searcher;
import com.threeti.ics.server.search.TimeFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "searchService")
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ConversationDao conversationDao;

    private List<Conversation> getConversations(List<Long> conversationIds) {
        List<Conversation> result = new ArrayList<Conversation>();
        for (Long id : conversationIds) {
            result.add(conversationDao.findBy(id));
        }
        return result;
    }

    @Override
    public List<Conversation> searchByVisitor(String customerServiceUserName, String visitorName) {
        return getConversations(new Searcher(customerServiceUserName).search(Searcher.VISITOR, visitorName));
    }

    @Override
    public List<Conversation> searchByTopic(String customerServiceUserName, String topic) {
        return getConversations(new Searcher(customerServiceUserName).search(Searcher.TOPIC, topic));
    }

    @Override
    public List<Conversation> searchByMessageBody(String customerServiceUserName, String messageBody) {
        return getConversations(new Searcher(customerServiceUserName).search(Searcher.MESSAGEBODY, messageBody));
    }

    @Override
    public List<Conversation> search(String userName, String queryString) {
        List<Conversation> result = new ArrayList<Conversation>();
        for (Long cId : new Searcher(userName).searchBy(queryString)) {
            result.add(conversationDao.findByWithMessage(cId));
        }
        return result;
    }

    @Override
    public List<Conversation> searchByDate(String customerServiceUserName, TimeFrequency frequency) {
        return getConversations(new Searcher(customerServiceUserName).search(Searcher.MESSAGEDATE, frequency.getDate()));
    }
}
