package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.threeti.ics.server.domain.messagequeue.QueueType;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 18/09/12
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class QueueConversationsRequest {
    private String customerServiceUserName;
    private String queueName;
    private int pageFrom;
    private int pageSize;
    private String order;

    public QueueConversationsRequest() {
    }

    public QueueConversationsRequest(String customerServiceUserName, String queueName) {
        this.customerServiceUserName = customerServiceUserName;
        this.queueName = queueName;
    }

    public String getCustomerServiceUserName() {
        return customerServiceUserName;
    }

    public void setCustomerServiceUserName(String customerServiceUserName) {
        this.customerServiceUserName = customerServiceUserName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    public String getActualQueueName() {
        return QueueType.from(queueName).getQueueName(customerServiceUserName);
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
