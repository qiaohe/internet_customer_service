package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.messagequeue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 18/09/12
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public abstract class AbstractSessionHandler implements SessionHandler {
    private static final String RESULT_OPERATION_TYPE_KEY = "operationType";
    private static final String RESULT_RESPONSE_KEY = "response";
    private static final String RESULT_RESPONSE_OK = "ok";
    @Autowired
    ConversationDao conversationDao;
    private Map<String, Object> result = new HashMap<String, Object>();

    protected void addResult(final String key, final Object value) {
        result.put(key, value);
    }

    protected abstract ConversationStatus getStatus();

    public AbstractSessionHandler() {
    }

    @Override
    public Map<String, Object> handle(SessionOperationRequest request, IoSession session) {
        addResult(RESULT_OPERATION_TYPE_KEY, request.getOperationType().toString());
        addResult(RESULT_RESPONSE_KEY, RESULT_RESPONSE_OK);
        conversationDao.updateCustomerServiceUserNameAndStatus(request.getConversationIds(), request.getCustomerService(), getStatus());
        return result;
    }
}
