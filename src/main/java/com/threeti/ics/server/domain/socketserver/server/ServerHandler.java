package com.threeti.ics.server.domain.socketserver.server;

import com.threeti.ics.server.domain.socketserver.command.CommandFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler extends IoHandlerAdapter {
    private static final int HEARTBEAT = 1;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
        cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);
        cfg.setWriteTimeout(1000 * 5);
        cfg.setKeepAlive(true);
        cfg.setSoLinger(0);
        SessionManager.getInstance().setService(session.getService());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
        System.out.println(cause.getMessage());
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        System.out.println(message.toString());
        CommandFactory.getInstance().createCommand(message).execute(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
//        if (status == IdleStatus.BOTH_IDLE) session.write(HEARTBEAT);
    }
}
