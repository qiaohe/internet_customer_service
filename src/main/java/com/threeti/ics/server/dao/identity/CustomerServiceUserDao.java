package com.threeti.ics.server.dao.identity;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.core.AbstractGenericDaoImpl;
import com.threeti.ics.server.dao.core.GenericDao;
import com.threeti.ics.server.dao.core.KeyUtils;
import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.CustomerServiceUserStatusChangeRequest;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUserRole;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
@Repository(value = "customerServiceUserDao")
public class CustomerServiceUserDao extends AbstractGenericDaoImpl<CustomerServiceUser> implements GenericDao<CustomerServiceUser> {

    public CustomerServiceUserDao() {
        super(CustomerServiceUser.class);
    }

    protected Long getId(CustomerServiceUser user) {
        String id = template.opsForValue().get(KeyUtils.customerServiceUserUserName(user.getUserName()));
        if (id != null) {
            user.setId(Long.valueOf(id));
            entities.addFirst(id);
            return Long.valueOf(id);
        }
        return idCounter.incrementAndGet();
    }

    public CustomerServiceUser findBy(String userName) {
        final String id = template.opsForValue().get(KeyUtils.customerServiceUserUserName(userName));
        return id == null ? null : findBy(Long.valueOf(id));
    }

    public CustomerServiceUser create(CustomerServiceUser user) {
        final Long id = getId(user);
        user.setId(id);
        template.opsForHash().putAll(getIdKey(id), getProperties(user));
        template.opsForValue().set(KeyUtils.customerServiceUserUserName(user.getUserName()), id.toString());
        return user;
    }

    @Override
    public CustomerServiceUser findBy(Long id) {
        CustomerServiceUser result = new CustomerServiceUser();
        result.setId(id);
        result.setUserName(template.opsForHash().get(getIdKey(id), "userName").toString());
        result.setPassword(template.opsForHash().get(getIdKey(id), "password").toString());
        result.setNickName(template.opsForHash().get(getIdKey(id), "nickName").toString());
        result.setHeadPortrait(template.opsForHash().get(getIdKey(id), "headPortrait").toString());
        result.setRole(CustomerServiceUserRole.valueOf(template.opsForHash().get(getIdKey(id), "role").toString()));
        result.setStatus(CustomerServiceStatus.valueOf(template.opsForHash().get(getIdKey(id), "status").toString()));
        result.setServiceToken(template.opsForHash().get(getIdKey(id), "serviceToken").toString());
        return result;
    }

    public CustomerServiceUser update(CustomerServiceUser user) {
        final String id = String.valueOf(getId(user));
        template.opsForHash().putAll(id, getProperties(user));
        template.delete(KeyUtils.customerServiceUserUserName(user.getUserName()));
        template.opsForValue().set(KeyUtils.customerServiceUserUserName(user.getUserName()), id);
        return user;
    }

    public boolean remove(CustomerServiceUser user) {
        if (!entities.contains(user.getId().toString())) throw new IllegalArgumentException("record doest not exist.");
        template.opsForHash().delete("id", user.getId());
        template.opsForHash().delete("userName", user.getUserName());
        template.opsForHash().delete("password", user.getPassword());
        template.opsForHash().delete("nickName", user.getNickName());
        template.opsForHash().delete("headPortrait", user.getHeadPortrait());
        template.opsForHash().delete("serviceToken", user.getServiceToken());
        template.opsForHash().delete("status", user.getStatus());
        template.delete(getIdKey(user.getId()));
        template.delete(KeyUtils.customerServiceUserUserName(user.getUserName()));
        entities.remove(user.getId().toString());
        return true;
    }

    public void update(CustomerServiceUserStatusChangeRequest request) {
        CustomerServiceUser user = findBy(request.getCustomerServiceUserName());
        template.opsForHash().put(getIdKey(user.getId()), "status", request.getStatus().toString());
        template.convertAndSend(Constants.CUSTOMER_SERVICE_STATUS_CHANGE_QUEUE, ObjectJsonMapper.getJsonStringBy(request));
    }

    public void updatePassword(String userName, String newPwd) {
        CustomerServiceUser user = findBy(userName);
        template.opsForHash().put(getIdKey(user.getId()), "password", newPwd);
    }

    public void updateHeadPortrait(String userName, String imageUrl) {
        CustomerServiceUser user = findBy(userName);
        template.opsForHash().put(getIdKey(user.getId()), "headPortrait", imageUrl);
    }

    public void updateNickName(String userName, String nickName) {
        CustomerServiceUser user = findBy(userName);
        template.opsForHash().put(getIdKey(user.getId()), "nickName", nickName);
    }
}
