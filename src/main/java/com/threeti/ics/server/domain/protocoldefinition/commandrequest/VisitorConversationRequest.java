package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 21/09/12
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class VisitorConversationRequest {
    private String visitor;
    private int pageFrom;
    private int pageSize;

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
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
