package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationTopic;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 13/09/12
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class BuildConversationRequest {
    private String serviceToken;
    private String productId;
    private String productName;
    private String picture;
    private String description;

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private ConversationTopic getTopic() {
        ConversationTopic result = new ConversationTopic();
        result.setPicture(picture);
        result.setDescription(description);
        result.setProductId(productId);
        result.setProductName(productName);
        return result;
    }

     public Conversation getConversation() {
        Conversation result = new Conversation();
        result.setTopic(getTopic());
        result.setCreateDate(new Date());
        result.setStatus(ConversationStatus.NOTACCEPTED);
        result.setVisitor(serviceToken);
        return result;
    }
}
