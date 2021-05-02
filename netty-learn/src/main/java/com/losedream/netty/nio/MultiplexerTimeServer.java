package com.losedream.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 20:02
 */
class MultiplexerTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel channel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {

            selector = Selector.open();

            channel = ServerSocketChannel.open();

            channel.configureBlocking(false);

            channel.socket().bind(new InetSocketAddress(port), 1024);

            channel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("The time server is start in port: " + port);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void run() {

        while (!stop) {

            try {

                selector.select(1000);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                SelectionKey key = null;

                while (iterator.hasNext()) {

                    key = iterator.next();

                    iterator.remove();

                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handlerInput(SelectionKey key) throws Exception{

        if (key.isValid()) {

            if (key.isAcceptable()) {
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel accept = channel.accept();
                accept.configureBlocking(false);
                accept.register(selector, SelectionKey.OP_READ);
            }

            if (key.isReadable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = channel.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(channel, currentTime);
                } else if (readBytes < 0) {
                    // 对链路端关闭
                    key.cancel();
                    channel.close();
                } else {
                    // 读到 0 字节 忽略
                }

            }

        }

    }

    private void doWrite(SocketChannel channel, String currentTime) throws IOException {
        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

    public void stop() {
        this.stop = true;
    }


}






