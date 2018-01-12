package com.holly.test.netty.privateprotocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/12 15:30
 */

public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatRespHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        if(message != null && message.getHeader().getType() == MessageType.HEARTBET_REQ){
            NettyMessage heartBeat = buildHeartBeat();
            logger.info("server receive heartbeat:"+heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
    private NettyMessage buildHeartBeat(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP);
        message.setHeader(header);
        return message;
    }
}
