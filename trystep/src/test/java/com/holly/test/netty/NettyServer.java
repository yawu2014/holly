package com.holly.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.tomcat.util.net.NioChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.Date;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/9 13:54
 */

public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger("NettyServer");
    public static void main(String[] args) throws Exception {
//        new NettyServer().bind(9090);
        new NettyServer().connect(9090,"127.0.0.1");
    }
    public void bind(int port)throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,1024).childHandler(new ChildChannelHandler());
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }
    class TimeServerHandler extends ChannelHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("server read msg");
            ByteBuf byteBuf = (ByteBuf)msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            String body = new String(bytes,"UTF-8");
            logger.info("server receive"+body);
            String currentTime = "QTO".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BO";
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.write(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("server Exception:"+cause.getMessage());
            ctx.close();
        }

    }
    public void connect(int port,String host) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();;
        }finally {
            group.shutdownGracefully();
        }
    }
    class TimeClientHandler extends ChannelHandlerAdapter{
        private final ByteBuf firstMessage;
        public TimeClientHandler(){
            byte[] req = "QTO".getBytes();
            firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("client active send message");
            ctx.writeAndFlush(firstMessage);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("client read message");
            ByteBuf buf = (ByteBuf)msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req,"UTF-8");
            logger.info("client receive:"+body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error(cause.getMessage());
            ctx.close();
        }
    }
}
