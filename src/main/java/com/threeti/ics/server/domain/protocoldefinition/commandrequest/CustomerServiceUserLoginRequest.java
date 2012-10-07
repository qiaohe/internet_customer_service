package com.threeti.ics.server.domain.protocoldefinition.commandrequest;

import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 13/09/12
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceUserLoginRequest {
    private String userName;
    private String password;
    private CustomerServiceStatus status;
    private String serviceToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerServiceStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerServiceStatus status) {
        this.status = status;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public CustomerServiceUser getCustomerServiceUser() {
        CustomerServiceUser result = new CustomerServiceUser();
        result.setPassword(password);
        result.setServiceToken(serviceToken);
        result.setStatus(status);
        result.setUserName(userName);
        return result;
    }
}
