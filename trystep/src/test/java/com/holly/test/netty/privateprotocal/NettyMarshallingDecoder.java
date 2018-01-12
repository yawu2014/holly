package com.holly.test.netty.privateprotocal;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 19:36
 */

public class NettyMarshallingDecoder {
    private static final Logger logger = LoggerFactory.getLogger(NettyMarshallingDecoder.class.getName());
    private final Unmarshaller unmarshaller;

    public NettyMarshallingDecoder() throws IOException {
        this.unmarshaller = NettyMarshallingCodeCFactory.buildUnMarshaller();
    }
    public Object decode(ByteBuf in) throws Exception{
        logger.info("readerindex:"+in.readerIndex());
        int objectSize = in.readInt();
        logger.info("readObject size:"+objectSize);
        ByteBuf buf = in.slice(in.readerIndex(),objectSize);
        ByteInput input = new ChannelBufferByteInput(buf);
        try{
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex()+objectSize);
            return obj;
        }finally {
            unmarshaller.close();
        }
    }
}
