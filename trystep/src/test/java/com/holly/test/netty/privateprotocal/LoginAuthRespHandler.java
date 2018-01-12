package com.holly.test.netty.privateprotocal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.WindowIDProvider;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/12 14:19
 */

public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(LoginAuthRespHandler.class.getName());
    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<String,Boolean>();
    private String[] whiteList = {"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead");
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp = buildResponse((byte) -1);
            }else{
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for(String WIP:whiteList){
                    if(WIP.equals(ip)){
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK?buildResponse((byte)0):buildResponse((byte)-1);
                if(isOK){
                    nodeCheck.put(nodeIndex,true);
                }
            }
            logger.info("login response is:"+loginResp+":body:"+loginResp.getObject());
            ctx.writeAndFlush(loginResp);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
    private NettyMessage buildResponse(byte result){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP);
        message.setHeader(header);
        message.setObject(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
