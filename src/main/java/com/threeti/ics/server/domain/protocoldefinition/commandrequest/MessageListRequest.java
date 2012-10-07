package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 21/09/12
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class MessageListRequest {
    private Long conversationId;
    private int pageFrom;
    private int pageSize;

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public int getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(int pageFrom) {
        this.pageFrom = pageFrom;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
