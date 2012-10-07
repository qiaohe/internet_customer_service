package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.ServiceToken;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveServiceTokenCommand extends AbstractCommand implements Command {
    public RetrieveServiceTokenCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        ServiceToken token = ObjectJsonMapper.getObjectBy(getRequestAsString(), ServiceToken.class);
        final String ts = token.getTokenAsString();
        session.write(ts);
    }
}
