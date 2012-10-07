package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.VisitorConversationRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 21/09/12
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class VisitorConversationCommand extends AbstractCommand implements Command {
    private static final String RESULT_CONVERSATION_LIST_KEY = "conversations";
    @Autowired
    private ConversationDao conversationDao;

    public VisitorConversationCommand(Object request) {
        super(request);
}

    @Override
    public void execute(IoSession session) {
        VisitorConversationRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), VisitorConversationRequest.class);
        List<Conversation> conversations = conversationDao.findByVisitor(request.getVisitor(), request.getPageFrom(), request.getPageSize());
        addResult(RESULT_CONVERSATION_LIST_KEY, conversations);
        session.write(new ResponseBuilder(this).getResponse());
    }
}
