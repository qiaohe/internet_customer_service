package com.threeti.ics.server.service;

import com.threeti.ics.server.common.I18NMessages;
import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.threeti.ics.server.common.Constants.*;
import static com.threeti.ics.server.web.ResponseResult.*;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "customerServiceUserService")
public class CustomerServiceUserServiceImpl implements CustomerServiceUserService {
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    @Override
    public ResponseResult changePassword(String userName, String oldPwd, String newPwd) {
        CustomerServiceUser user = customerServiceUserDao.findBy(userName);
        if (user == null)
            return new ResponseResult(CUSTOMER_SERVICE_USER_NOT_EXISTS_CODE, I18NMessages.getMessage("login.customerServiceDoesNotExists", userName));
        if (!user.getPassword().equals(oldPwd))
           return new ResponseResult(CUSTOMER_SERVICE_USER_PASSWORD_WRONG_CODE, I18NMessages.getMessage("login.wrongOldPassword"));
        customerServiceUserDao.updatePassword(userName, newPwd);
        return new ResponseResult(CHANGE_PASSWORD_SUCCESS_CODE, I18NMessages.getMessage("login.changePasswordSuccess"));
    }

    @Override
    public boolean changeHeadPortrait(String userName, String imageUrl) {
        customerServiceUserDao.updateHeadPortrait(userName, imageUrl);
        return true;
    }

    @Override
    public boolean changeNickName(String userName, String nickName) {
        customerServiceUserDao.updateNickName(userName, nickName);
        return true;
    }
}
