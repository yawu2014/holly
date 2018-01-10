package com.holly.test.netty;

import com.mysql.fabric.Server;
import com.sun.xml.internal.stream.util.BufferAllocator;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.SelectKey;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/8 17:43
 */

public class NioServer {
    private static final Logger logger = LoggerFactory.getLogger("Nio");
    public static void main(String[] args) {
//        new Thread(new MultiplexerTimeServer(8088)).start();
        new Thread(new TimeClientHandler("127.0.0.1",8088),"NIO-clientChannel").start();
    }
    @Test
    public void startNioServerChannel(){
        new Thread(new MultiplexerTimeServer(8088)).start();
    }
    static class MultiplexerTimeServer implements Runnable{
        private Selector selector;
        private ServerSocketChannel serverChannel;
        private volatile boolean stop;
        public MultiplexerTimeServer(int port){
            try{
                selector = Selector.open();
                serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
                serverChannel.socket().bind(new InetSocketAddress(port),1024); //此处是socket.bind否则客户端不能接收到消息
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("server start at:"+port);
            }catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        public void stop(){
            this.stop = true;
        }
        @Override
        public void run() {
            while(!stop){
                try{
                    selector.select(1000);
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    while (it.hasNext()){
                        SelectionKey key = it.next();
                        it.remove();
                        try{
                            handlerInput(key);
                        }catch (Exception e){
                            e.printStackTrace();
                            if(key != null){
                                key.cancel();
                                if(key.channel() != null){
                                    key.channel().close();
                                }
                            }
                        }
                    }
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }
            if(selector != null){
                try{
                    selector.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        private void handlerInput(SelectionKey key)throws IOException{
            if(key.isValid()){
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    logger.info("server accept");
                }
                if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(bf);
                    if(readBytes > 0){
                        bf.flip();
                        byte[] bytes = new byte[bf.remaining()];
                        bf.get(bytes);
                        String body = new String(bytes,"UTF-8");
                        System.out.println("body:"+body);
                        String currentTime = "QTO".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BO";
                        logger.info("response:"+currentTime);
                        doWrite(socketChannel,currentTime);
                    }else if(readBytes < 0){
                        key.channel();
                        socketChannel.close();
                    }else{

                    }
                    logger.info("server read");
                }
            }
        }
        private void doWrite(SocketChannel channel,String resposne) throws IOException{
            if(StringUtils.isNotEmpty(resposne)&&StringUtils.isNotEmpty(StringUtils.trim(resposne))){
                byte[] bytes = resposne.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                channel.write(writeBuffer);
                logger.info("server write");
            }

        }
    }
    @Test
    public void startClientChannel(){
        new Thread(new TimeClientHandler("127.0.0.1",8088),"NIO-clientChannel").start();
    }
    static class TimeClientHandler implements Runnable{
        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean stop = false;
        private volatile String host;
        private int port;
        public TimeClientHandler(String host,int port){
            this.port = port;
            this.host = host;
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
            }catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        public void stop(){
            stop = true;
        }
        @Override
        public void run() {
            try{
                doConnect();
            }catch(IOException e){
                e.printStackTrace();
            }
            while(!stop){
                try{
                    selector.select(1000);
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    SelectionKey key = null;
                    while(it.hasNext()){
                        key = it.next();
                        it.remove();
                        try{
                            handleClientInput(key);
                        }catch(Exception e){
                            e.printStackTrace();
                            if(key != null){
                                key.cancel();
                                if(key.channel() != null){
                                    key.channel().close();
                                }
                            }
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            if(selector != null){
                try{
                    selector.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        private void handleClientInput(SelectionKey key) throws IOException{
            if(key.isValid()){
                SocketChannel sc = (SocketChannel) key.channel();
                if(key.isConnectable()){
                    if(sc.finishConnect()){
                        logger.info("client connect3");
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);
                        doWrite(sc);
                    }else{
                        System.exit(1);
                    }
                    logger.info("client connect2");
                }
                if(key.isReadable()){
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if(readBytes > 0){
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes,"UTF-8");
                        System.out.println("receive:"+body);
                        this.stop = true;
                    }else if(readBytes < 0){
                        key.cancel();
                        sc.close();
                    }else{

                    }
                    logger.info("client read");
                }
            }
        }
        private void doConnect() throws IOException {
            if(socketChannel.connect(new InetSocketAddress(host,port))){
                socketChannel.register(selector,SelectionKey.OP_READ);
                doWrite(socketChannel);
            }else{
                socketChannel.register(selector,SelectionKey.OP_CONNECT);
            }
            logger.info("client connect");
        }
        private void doWrite(SocketChannel sc) throws IOException{
            byte[] req = "QTO".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(req);
            writeBuffer.flip();
            sc.write(writeBuffer);
            if(!writeBuffer.hasRemaining()){
                System.out.println("send order to Server success");
            }
            logger.info("client write");
        }
    }
}
