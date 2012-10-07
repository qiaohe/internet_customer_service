package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.conversation.ConversationTopicDao;
import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.messagequeue.QueueController;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.BuildConversationRequest;
import com.threeti.ics.server.domain.protocoldefinition.conversation.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.conversation.ConversationTopic;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import static com.threeti.ics.server.common.Constants.VISITOR_USER;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class BuildConversationCommand extends AbstractCommand implements Command {
    private static final String RESULT_CONVERSATION_ID_KEY = "conversationId";
    @Autowired
    private ConversationTopicDao conversationTopicDao;
    @Autowired
    private ConversationDao conversationDao;
    @Autowired
    private QueueController queueController;

    public BuildConversationCommand(Object request) {
        super(request);
    }

    private Conversation createConversation() {
        BuildConversationRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), BuildConversationRequest.class);
        Conversation conversation = request.getConversation();
        ConversationTopic topic = conversationTopicDao.create(conversation.getTopic());
        conversation.setTopic(topic);
        return conversationDao.create(conversation);
    }

    @Override
    public void execute(IoSession session) {
        Conversation conversation = createConversation();
        queueController.doDispatch(conversation);
        if (session.getAttribute(Constants.ISWEB) != null) {
            addResult("serviceToken", conversation.getVisitor());
        }
        addResult(RESULT_CONVERSATION_ID_KEY, conversation.getId());
        session.write(new ResponseBuilder(this).getResponse());
        session.setAttribute(VISITOR_USER, conversation.getVisitor());
    }
}
