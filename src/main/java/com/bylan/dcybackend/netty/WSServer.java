package com.bylan.dcybackend.netty;

import com.bylan.dcybackend.boot.ApplicationContextUtil;
import com.bylan.dcybackend.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;


/**
 * @author wuhuaming
 * @date 2022/06/04 16:37
 */
@Component
@Slf4j
public class WSServer {

//    private NettyProperties properties = (NettyProperties) ApplicationContextUtil.getBean("nettyProperties");
//
    private static class SingletonWSServer {
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletonWSServer.instance;
    }



    @Value("${netty.port}")
    private int nettyPort;


    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerBootstrap server;
    private ChannelFuture channelFuture;

    public WSServer() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitializer());
    }

    public void start() throws InterruptedException {
        log.info("端口:{}",nettyPort);
        this.channelFuture= server.bind(8098);
        System.err.println("netty webscoket start 启动 ....");

    }
}
