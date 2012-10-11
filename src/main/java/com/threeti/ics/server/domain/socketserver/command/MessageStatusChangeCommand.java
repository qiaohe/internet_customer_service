package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.message.MessageDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.MessageStatusChangeRequest;
import com.threeti.ics.server.domain.socketserver.handler.MessageStatusChangeHandler;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class MessageStatusChangeCommand extends AbstractCommand implements Command {
    private static final String ID_DELIMITER = ",";
    private static final String STATUS_CHANGED_KEY = "messagestatuschange";
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private MessageStatusChangeHandler messageStatusChangeHandler;

    public MessageStatusChangeCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        MessageStatusChangeRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), MessageStatusChangeRequest.class);
        messageDao.updateStatus(request.getMessageIdArray(), request.getStatus());
        messageStatusChangeHandler.handle(request);
        IoSession ses = SessionManager.getInstance().getSession(request.getTo());
        if (ses != null) {
            addResult(STATUS_CHANGED_KEY, request);
            ses.write(new ResponseBuilder(this).getResponse());
        }
    }
}
