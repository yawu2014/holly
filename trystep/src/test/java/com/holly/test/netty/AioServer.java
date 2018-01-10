package com.holly.test.netty;

import jdk.nashorn.internal.runtime.logging.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/9 10:48
 */

public class AioServer {

    public static void main(String[] args) {

    }
    static class AsyncTimeServerHandler implements Runnable{
        private static final Logger logger = LoggerFactory.getLogger("asyncTimeServerHandler");
        private int port;
        CountDownLatch latch;
        AsynchronousServerSocketChannel asynchronousServerSocketChannel;
        public AsyncTimeServerHandler(int port){
            this.port = port;
            try {
                asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
                asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
                logger.info("server start at:"+port);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        @Override
        public void run() {
            latch = new CountDownLatch(1);
            doAccept();
            try {
                latch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        public void doAccept(){
//            asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
        }
//        class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,? super A>{
//
//        }
    }
}
