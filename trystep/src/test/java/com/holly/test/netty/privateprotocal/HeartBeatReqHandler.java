package com.holly.test.netty.privateprotocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/12 15:19
 */

public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatReqHandler.class.getName());
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead");
        NettyMessage message = (NettyMessage)msg;
        if(message != null && message.getHeader().getType()==MessageType.LOGIN_RESP){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if(message != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP){
            logger.info("client receive heartbeat message:"+message);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
    private class HeartBeatTask implements Runnable{
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        @Override
        public void run() {
            NettyMessage heartBeat = buildHeartBeat();
            logger.info("client send heartbeat message to server"+heartBeat);
            ctx.writeAndFlush(heartBeat);
        }
        private NettyMessage buildHeartBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBET_REQ);
            message.setHeader(header);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
