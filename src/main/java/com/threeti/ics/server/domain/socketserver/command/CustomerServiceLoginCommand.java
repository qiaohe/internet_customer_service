package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.I18NMessages;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.messagequeue.QueueType;
import com.threeti.ics.server.domain.protocoldefinition.commandresponse.CustomerServiceLoginResponse;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import static com.threeti.ics.server.common.Constants.CUSTOMER_SERVICE_USER;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class CustomerServiceLoginCommand extends AbstractCommand implements Command {
    private static final String LOGIN_CODE = "login";
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;
    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private QueueController queueController;


    public CustomerServiceLoginCommand(Object request) {
        super(request);
    }

    private CustomerServiceUser getCustomerServiceUserFromLoginProcess() {
        CustomerServiceUser request = ObjectJsonMapper.getObjectBy(getRequestAsString(), CustomerServiceUser.class);
        CustomerServiceUser result = customerServiceUserDao.findBy(request.getUserName());
        if (result == null) {
            addResult(LOGIN_CODE, I18NMessages.getMessage("login.customerServiceDoesNotExists", request.getNickName()));
            return null;
        }
        if (!result.getPassword().equals(request.getPassword())) {
            addResult(LOGIN_CODE, I18NMessages.getMessage("login.wrongOldPassword"));
            return null;
        }
        return result;
    }

    @Override
    public void execute(IoSession session) {
        CustomerServiceUser user = getCustomerServiceUserFromLoginProcess();
        if (user == null) {
            session.write(new ResponseBuilder(this).getResponse());
            return;
        }

        IoSession existUserSession = SessionManager.getInstance().getSessionByCustomerService(user.getUserName());
        if (existUserSession != null) {
            existUserSession.close(true);
        }

        CustomerServiceLoginResponse response = CustomerServiceLoginResponse.valueOf(user,
                conversationDao.findBy(QueueType.PUBLIC.getQueueName(null), 0, 10, "asc"),
                queueController.getSummaries(user.getUserName()));
        session.write(response.toJson());
        session.setAttribute(CUSTOMER_SERVICE_USER, user);
    }
}

