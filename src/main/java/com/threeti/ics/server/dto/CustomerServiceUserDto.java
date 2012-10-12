package com.threeti.ics.server.dto;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/10/12
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceUserDto {
    private String userName;
    private String nickName;
    private String headPortrait;

    public CustomerServiceUserDto() {
    }

    public CustomerServiceUserDto(String userName, String nickName, String headPortrait) {
        this.userName = userName;
        this.nickName = nickName;
        this.headPortrait = headPortrait;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
