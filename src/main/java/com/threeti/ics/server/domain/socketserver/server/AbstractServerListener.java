package com.threeti.ics.server.domain.socketserver.server;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServerListener implements IoServiceListener {
    @Override
    public void serviceActivated(IoService service) throws Exception {
    }

    @Override
    public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {

    }

    @Override
    public void serviceDeactivated(IoService service) throws Exception {

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {

    }

    @Override
    public void sessionDestroyed(IoSession session) throws Exception {

    }
}
