package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.domain.protocoldefinition.SessionOperationType;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class HandlerFactory {
    private static final HandlerFactory INSTANCE = new HandlerFactory();

    public static HandlerFactory getInstance() {
        return INSTANCE;
    }

    public SessionHandler createHandler(SessionOperationType operationType) {
        switch (operationType) {
            case ACCEPT:
                return new AcceptSessionHandler();
            case APPOINT:
                return new AppointSessionHandler();
            case SUSPEND:
                return new SuspendSessionHandler();
            case TERMINATE:
                return new TerminateSessionHandler();
            case RESUME:
                return new ResumeSessionHandler();
            case SESSIONTRANSFER:
                return new SessionTransferHandler();

            default:
                throw new UnsupportedOperationException("Unsupported Session Operation type");
        }
    }
}
