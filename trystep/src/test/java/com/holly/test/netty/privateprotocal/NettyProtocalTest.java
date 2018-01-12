package com.holly.test.netty.privateprotocal;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/12 15:35
 */

public class NettyProtocalTest {
    private final int RPORT = 9090;
    private final String RIP = "127.0.0.1";
    private final int LPORT = 9091;
    private final String LIP = "127.0.0.1";
    public static void main(String[] args)throws Exception {
//        new NettyProtocalTest().bind();
        new NettyProtocalTest().connect();
    }
    @Test
    private void connect() throws Exception {
        final Logger logger = LoggerFactory.getLogger(NettyProtocalTest.class.getName());
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b= new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4,-8,0)); //此处有坑,注意要调整2,3的参数,通过4
                    ch.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                    ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                    ch.pipeline().addLast("loginAuthHandler",new LoginAuthReqHandler());
                    ch.pipeline().addLast("heartbeatHandler",new HeartBeatReqHandler());
                }
            });
            ChannelFuture f = b.connect(new InetSocketAddress(RIP,RPORT),new InetSocketAddress(LIP,LPORT)).sync();
            f.channel().closeFuture().sync();
        }finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        connect();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,100).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4,-8,0));
                ch.pipeline().addLast(new NettyMessageEncoder());
                ch.pipeline().addLast(new ReadTimeoutHandler(50));
                ch.pipeline().addLast(new LoginAuthRespHandler());
                ch.pipeline().addLast(new HeartBeatRespHandler());
            }
        });
        b.bind(RIP,RPORT).sync();
    }
}
