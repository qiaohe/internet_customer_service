package com.threeti.ics.server.domain.socketserver.server;

import com.threeti.ics.server.config.ServerConfig;
import com.threeti.ics.server.config.SocketServerInfo;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static org.apache.mina.filter.codec.textline.LineDelimiter.WINDOWS;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "socketServer")
public final class Server {
    private SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);

    public Server() {
    }

    private ProtocolCodecFactory creteProtocolCodecFactory() {
        TextLineCodecFactory result = new TextLineCodecFactory(Charset.forName("UTF-8"), WINDOWS.getValue(), WINDOWS.getValue());
        result.setDecoderMaxLineLength(Integer.MAX_VALUE);
        result.setEncoderMaxLineLength(Integer.MAX_VALUE);
        return result;
    }

    public SocketServerInfo getSocketServerInfo() {
        return ServerConfig.getInstance().getSocketServerInfo();
    }

    @PostConstruct
    public void init() throws IOException {
        SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(creteProtocolCodecFactory()));
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(new OrderedThreadPoolExecutor(getSocketServerInfo().getThreadPoolSize())));
        acceptor.getSessionConfig().setTcpNoDelay(true);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.getSessionConfig().setReuseAddress(true);
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(getSocketServerInfo().getPort()));
    }

    @PreDestroy
    public void destroy() {
        acceptor.unbind(new InetSocketAddress(getSocketServerInfo().getPort()));
    }

    public void start() throws IOException {
        if (!acceptor.isActive() && !acceptor.isDisposed()) {
            acceptor.bind(new InetSocketAddress(getSocketServerInfo().getPort()));
        }
    }

    public void stop() {
        acceptor.unbind(new InetSocketAddress(getSocketServerInfo().getPort()));
    }
}
