package com.threeti.ics.server.service;

import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.web.ResponseResult;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerServiceUserService {

    public ResponseResult changePassword(String userName, String oldPwd, String newPwd);

    public boolean changeHeadPortrait(String userName, String imageUrl);

    public boolean changeNickName(String userName, String nickName);

    public List<CustomerServiceUser> findAll();

}
