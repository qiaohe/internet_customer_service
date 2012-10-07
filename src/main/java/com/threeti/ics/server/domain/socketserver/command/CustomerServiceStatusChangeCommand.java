package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.identity.CustomerServiceUserDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.CustomerServiceUserStatusChangeRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 20/09/12
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class CustomerServiceStatusChangeCommand extends AbstractCommand implements Command {

    private static final String RESULT_RESPONSE_OK_KEY = "response";
    private static final String RESULT_RESPONSE_OK = "ok";
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;

    public CustomerServiceStatusChangeCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        CustomerServiceUserStatusChangeRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), CustomerServiceUserStatusChangeRequest.class);
        customerServiceUserDao.update(request);
        addResult(RESULT_RESPONSE_OK_KEY, RESULT_RESPONSE_OK);
        session.write(new ResponseBuilder(this).getResponse());
    }
}
