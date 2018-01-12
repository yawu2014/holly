package com.holly.test.netty.privateprotocal;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/11 18:39
 */

public class NettyMarshallingCodeCFactory {
    public static MarshallingDecoder buildMarshallingDecoder(){
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory,configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider,1024);
        return decoder;
    }
    public static MarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(factory,configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
    public static Marshaller buildMarshaller() throws IOException{
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Marshaller marshaller = factory.createMarshaller(configuration);
        return marshaller;
    }
    public static Unmarshaller buildUnMarshaller() throws IOException{
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final Unmarshaller unmarshaller = factory.createUnmarshaller(configuration);
        return unmarshaller;
    }
}
