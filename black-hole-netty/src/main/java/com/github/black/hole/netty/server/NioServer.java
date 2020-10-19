package com.github.black.hole.netty.server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

/**
 * @author hairen.long
 * @date 2020-10-19
 */
public class NioServer {

    private int port;
    private Selector selector;

    public static void main(String[] args) {
        new NioServer(8080).start();
    }

    public NioServer(int port) {
        this.port = port;
    }

    public void start() {
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server tarted....");

            while (true) {
                int event = selector.select();
                if (event > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey key = selectionKeys.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println("accept a client: " + sc.socket().getInetAddress().getHostAddress());
                        } else if (key.isReadable()) {
                            CompletableFuture.runAsync(() -> {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    socketChannel.read(buffer);
                                    buffer.flip();
                                    System.out.println("收到客户端" + socketChannel.socket().getInetAddress().getHostName() + "的数据：" + new String(buffer.array()));
                                    //将数据添加到key中
                                    ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());
                                    socketChannel.write(outBuffer);// 将消息回送给客户端
                                    key.cancel();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        // 移除已处理事件
                        selectionKeys.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ssc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
