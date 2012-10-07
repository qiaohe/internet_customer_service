package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceUserStatusChangeRequest {
    private static final String REQUEST_KEY = "request";
    private String customerServiceUserName;
    private CustomerServiceStatus status;

    public String getCustomerServiceUserName() {
        return customerServiceUserName;
    }

    public void setCustomerServiceUserName(String customerServiceUserName) {
        this.customerServiceUserName = customerServiceUserName;
    }

    public CustomerServiceStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerServiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return customerServiceUserName + "：" + status.toString();
    }

    public Object toJsonString() {
        CommonObject result = new CommonObject(this.getClass().getSimpleName().replace(REQUEST_KEY, StringUtils.EMPTY));
        result.addProperty(REQUEST_KEY, this);
        return result.toJsonString();
    }
}
