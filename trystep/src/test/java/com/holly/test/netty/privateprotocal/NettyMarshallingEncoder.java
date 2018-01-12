package com.holly.test.netty.privateprotocal;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 10:30
 */

public class NettyMarshallingEncoder {
    private static final Logger logger = LoggerFactory.getLogger(NettyMarshallingEncoder.class.getName());
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;
    public NettyMarshallingEncoder() throws IOException {
        marshaller = NettyMarshallingCodeCFactory.buildMarshaller();
    }
    protected void encode(Object msg,ByteBuf out) throws IOException{
        try {
            logger.info("send buf send Object writeIndex:"+out.writerIndex());
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            logger.info("send buf send Object size:"+(out.writerIndex() - lengthPos - 4));
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
            logger.info("writerIndex"+out.writerIndex());
        }finally {
            marshaller.close();
        }
    }
}
