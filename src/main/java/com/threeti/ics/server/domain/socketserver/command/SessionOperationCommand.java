package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.messagequeue.SessionOperationRequest;
import com.threeti.ics.server.domain.socketserver.handler.HandlerFactory;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class SessionOperationCommand extends AbstractCommand implements Command {
    public SessionOperationCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        SessionOperationRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), SessionOperationRequest.class);
        HandlerFactory.getInstance().createHandler(request.getOperationType());
        addResult(HandlerFactory.getInstance().createHandler(request.getOperationType()).handle(request, session));
        addResult(SessionOperationRequest.FROM_KEY_WITHIN_ADDITIONAL, request.getValueOfAdditionalFromKey());
        if (!request.isSessionBasedOperation()) {
            for (IoSession s : SessionManager.getInstance().getCustomerServiceSessions()) {
                s.write(new ResponseBuilder(this).getResponse());
            }
        } else {
            session.write(new ResponseBuilder(this).getResponse());
        }
    }
}
