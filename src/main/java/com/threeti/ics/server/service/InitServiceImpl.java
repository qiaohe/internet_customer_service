package com.threeti.ics.server.service;

import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.dao.mobiledata.MobileAppDao;
import com.threeti.ics.server.dao.texttemplate.MessageTextTemplateDao;
import com.threeti.ics.server.domain.mobile.MobileApp;
import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "initService")
public class InitServiceImpl implements InitService {
    @Autowired
    private MobileAppDao mobileAppDao;
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    @Autowired
    private MessageTextTemplateDao messageTextTemplateDao;

    @PostConstruct
    public void init() {
        MobileApp app = new MobileApp();
        app.setAppKey("8890fgdkj90fdg89f88");
        app.setAppName("App0");
        mobileAppDao.create(app);
        MobileApp app1 = new MobileApp();
        app1.setAppKey("8890fgdkj90fdg89f89");
        app1.setAppName("App1");
        mobileAppDao.create(app);

        CustomerServiceUser u1 = new CustomerServiceUser();
        u1.setStatus(CustomerServiceStatus.BUSY);
        u1.setUserName("abcdef0002");
        u1.setNickName("Clark");
        u1.setHeadPortrait("http://www.3ti.us");
        u1.setRole(CustomerServiceUserRole.USER);
        u1.setPassword("123456");
        customerServiceUserDao.create(u1);

        for (int i = 0; i < 10; i++) {
            CustomerServiceUser customerServcieUser = new CustomerServiceUser();
            customerServcieUser.setStatus(CustomerServiceStatus.BUSY);
            customerServcieUser.setUserName("aaaaaa000" + String.valueOf(i));
            customerServcieUser.setNickName("Jack" + String.valueOf(i));
            customerServcieUser.setHeadPortrait("http://www.3ti.us");
            customerServcieUser.setRole(CustomerServiceUserRole.USER);
            customerServcieUser.setPassword("123456");
            customerServiceUserDao.create(customerServcieUser);
        }

        CustomerServiceUser u2 = new CustomerServiceUser();
        u2.setStatus(CustomerServiceStatus.BUSY);
        u2.setUserName("abcdef0003");
        u2.setNickName("Johnson");
        u2.setHeadPortrait("http://www.3ti.us");
        u2.setRole(CustomerServiceUserRole.USER);
        u2.setPassword("123456");
        customerServiceUserDao.create(u2);

        CustomerServiceUser admin = new CustomerServiceUser();
        admin.setStatus(CustomerServiceStatus.BUSY);
        admin.setUserName("abcdef0000");
        admin.setNickName("Jack");
        admin.setHeadPortrait("http://www.3ti.us");
        admin.setRole(CustomerServiceUserRole.ADMIN);
        admin.setPassword("123456");
        customerServiceUserDao.create(admin);

        CustomerServiceUser admin1 = new CustomerServiceUser();
        admin1.setStatus(CustomerServiceStatus.BUSY);
        admin1.setUserName("abcdef0001");
        admin1.setNickName("Eric");
        admin1.setHeadPortrait("http://www.3ti.us");
        admin1.setRole(CustomerServiceUserRole.USER);
        admin1.setPassword("123456");
        customerServiceUserDao.create(admin1);

        List<String> t1 = new ArrayList<String>();
        t1.add("我们会尽快给您发出去,请耐心等待.");
        messageTextTemplateDao.add("8890fgdkj90fdg89f88", "售后服务", t1);
        List<String> t2 = new ArrayList<String>();
        t2.add("7天包退，15天包换.");
        messageTextTemplateDao.add("8890fgdkj90fdg89f88", "退货细则", t2);
        List<String> t3 = new ArrayList<String>();
        t3.add("每花费1元RMB，即可获得1个积分.");
        messageTextTemplateDao.add("8890fgdkj90fdg89f88", "积分规则", t3);
        List<String> t4 = new ArrayList<String>();
        t4.add("稍等,我一会儿联系您.");
        messageTextTemplateDao.add("8890fgdkj90fdg89f88", "自定义", t4);

    }
}
