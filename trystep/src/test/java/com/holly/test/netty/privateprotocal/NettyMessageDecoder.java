package com.holly.test.netty.privateprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 10:09
 */

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{
    private static final Logger logger = LoggerFactory.getLogger(NettyMessageDecoder.class.getName());
    NettyMarshallingDecoder nettyMarshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        nettyMarshallingDecoder = new NettyMarshallingDecoder();
    }

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        nettyMarshallingDecoder = new NettyMarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        logger.info("decode size:"+in.readableBytes());
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);
        if(frame == null){
            logger.error("decode error");
            return null;
        }
        logger.info("frame size:"+frame.readableBytes());
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        int size = frame.readInt();
        if(size > 0){
            Map<String,Object> attch = new HashMap<String,Object>();
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for(int i=0;i<size;i++){
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray,"UTF-8");
                attch.put(key,nettyMarshallingDecoder.decode(frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attch);
        }
        if(frame.readableBytes() > 4){
            message.setObject(nettyMarshallingDecoder.decode(frame));
        }
        message.setHeader(header);
        return message;
    }
}
