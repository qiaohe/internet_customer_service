package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.domain.builder.ResponseBuilder;
import com.threeti.ics.server.domain.imageserver.ImageServer;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 19/09/12
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class UploadImageCommand extends AbstractCommand implements Command {
    @Autowired
    private ImageServer imageServer;
    private static final String RESULT_UPLOAD_IMAGE_KEY = "imageurl";

    public UploadImageCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) throws IOException {
        addResult(RESULT_UPLOAD_IMAGE_KEY, imageServer.store(getRequestAsString()));
        session.write(new ResponseBuilder(this).getResponse());
    }
}
