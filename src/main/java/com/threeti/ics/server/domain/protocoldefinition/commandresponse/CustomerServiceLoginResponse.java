package com.threeti.ics.server.domain.protocoldefinition.commandresponse;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 13/09/12
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceLoginResponse implements CommandResponse {
    private CustomerServiceUser customerServiceUser;
    private List<Conversation> conversations;
    private HashMap<String, Long> queueSummary;

    public CustomerServiceLoginResponse() {
    }

    public CustomerServiceLoginResponse(CustomerServiceUser customerServiceUser, List<Conversation> conversations, HashMap<String, Long> queueSummary) {
        this.customerServiceUser = customerServiceUser;
        this.conversations = conversations;
        this.queueSummary = queueSummary;
    }

    public static CustomerServiceLoginResponse valueOf(CustomerServiceUser customerServiceUser, List<Conversation> conversations, HashMap<String, Long> queueSummary) {
        return new CustomerServiceLoginResponse(customerServiceUser, conversations, queueSummary);
    }

    public CustomerServiceUser getCustomerServiceUser() {
        return customerServiceUser;
    }

    public void setCustomerServiceUser(CustomerServiceUser customerServiceUser) {
        this.customerServiceUser = customerServiceUser;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setQueueSummary(HashMap<String, Long> queueSummary) {
        this.queueSummary = queueSummary;
    }

    public HashMap<String, Long> getQueueSummary() {
        return queueSummary;
    }

    public String toJson() {
        CommonObject co = new CommonObject();
        co.setType("customerservicelogin");
        co.addProperty("customerServiceUser", customerServiceUser);
        co.addProperty("conversations", conversations);
        co.addProperty("queueSummary", getQueueSummary());
        return ObjectJsonMapper.getJsonStringBy(co);
    }

    @Override
    public String getResult() {
        return null;
    }
}
