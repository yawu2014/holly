package com.holly.test.netty.privateprotocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/12 14:45
 */

public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthReqHandler.class.getName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead");
        NettyMessage message = (NettyMessage)msg;
        if(message != null && message.getHeader().getType() == MessageType.LOGIN_RESP){
            byte loginResult = (byte)message.getObject();
            if(loginResult != (byte)0){//握手失败
                logger.info("client receive handshak:"+loginResult);
                ctx.close();
            }else{
                logger.info("Login is OK:"+message);
                ctx.fireChannelRead(msg);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ);
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
