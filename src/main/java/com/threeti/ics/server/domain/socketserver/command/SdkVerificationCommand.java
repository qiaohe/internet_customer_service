package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.identity.ServiceTokenDao;
import com.threeti.ics.server.dao.mobiledata.MobileDeviceDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.mobile.ServiceToken;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.SdkVerificationRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SdkVerificationCommand extends AbstractCommand implements Command {
    private static final String RESULT_KEY_SERVICE_TOKEN = "serviceToken";
    @Autowired
    private MobileDeviceDao mobileDeviceDao;
    @Autowired
    private ServiceTokenDao serviceTokenDao;


    public SdkVerificationCommand() {
        super();
    }

    public SdkVerificationCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        SdkVerificationRequest sr = ObjectJsonMapper.getObjectBy(getRequestAsString(), SdkVerificationRequest.class);
        mobileDeviceDao.create(sr.getMobileDevice());
        ServiceToken token = serviceTokenDao.create(sr.getServiceToken());
        if (sr.isFromWeb()) {
            session.setAttribute(Constants.ISWEB, true);
            this.addResult(SdkVerificationRequest.UID, sr.getUid());
        }
        this.addResult(RESULT_KEY_SERVICE_TOKEN, token.getToken());
        session.write(new ResponseBuilder(this).getResponse());
    }
}
