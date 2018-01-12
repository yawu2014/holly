package com.holly.test.netty.privateprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 10:09
 */

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    private static final Logger logger = LoggerFactory.getLogger(NettyMessageEncoder.class.getName());
    NettyMarshallingEncoder nettyMarshallingEncoder;

    public NettyMessageEncoder() throws IOException{
        nettyMarshallingEncoder = new NettyMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        logger.info("encode");
        if(msg == null || msg.getHeader() == null){
            throw new Exception("The encode message is null");
        }
        int writeBegin = out.writerIndex();
        ByteBuf sendBuf = out;
        sendBuf.writeInt((msg.getHeader()).getCrcCode());
        sendBuf.writeInt((msg.getHeader()).getLength());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for(Map.Entry<String,Object> param:msg.getHeader().getAttachment().entrySet()){
            key = param.getKey();
            keyArray = key.getBytes();
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
            nettyMarshallingEncoder.encode(value,sendBuf);

        }
        key = null;
        keyArray = null;
        value = null;
        if(msg.getObject() != null){
            nettyMarshallingEncoder.encode(msg.getObject(),sendBuf);
        }else{
            sendBuf.writeInt(0);
        }
        // 修正传输的字节大小,decoder是通过长度判断接收包大小的
        sendBuf.setInt(4,sendBuf.readableBytes()); //此处和Decode的长度有关系
        logger.info("send byte size:"+(sendBuf.writerIndex() - writeBegin));
    }
}
