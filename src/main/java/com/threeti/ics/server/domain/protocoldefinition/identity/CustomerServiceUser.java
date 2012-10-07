package com.threeti.ics.server.domain.protocoldefinition.identity;

import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */

public class CustomerServiceUser implements ICSUser {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private String headPortrait;
    private CustomerServiceStatus status;
    private CustomerServiceUserRole role;
    private String serviceToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public CustomerServiceStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerServiceStatus status) {
        this.status = status;
    }

    public CustomerServiceUserRole getRole() {
        return role;
    }

    public void setRole(CustomerServiceUserRole role) {
        this.role = role;
    }

    @Override
    public String getServiceToken() {
        return this.serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }
}
