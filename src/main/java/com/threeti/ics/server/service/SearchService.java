package com.threeti.ics.server.service;

import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.search.TimeFrequency;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public interface SearchService {
    public List<Conversation> searchByVisitor(String customerServiceUserName, String visitorName);

    public List<Conversation> searchByTopic(String customerServiceUserName, String userName);

    public List<Conversation> searchByDate(String customerServiceUserName, TimeFrequency frequency);

    public List<Conversation> searchByMessageBody(String customerServiceUserName, String body);

    public List<Conversation> search(String userName, String queryString);
}
