package com.github.black.hole.netty.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hairen.long
 * @date 2021/8/16
 */
public class BioServer {

    private int port;
    private ExecutorService executor =
            new ThreadPoolExecutor(
                    3,
                    5,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(1),
                    new ThreadFactoryBuilder().setNameFormat("bio-server-pool").build(),
                    new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        new BioServer(8080).start();
    }

    public BioServer(int port) {
        this.port = port;
    }

    private void start() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server started ......");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("accept a client : " + socket.getInetAddress().getHostAddress());
                executor.submit(
                        () -> {
                            BufferedReader reader = null;
                            PrintWriter writer = null;

                            try {
                                reader =
                                        new BufferedReader(
                                                new InputStreamReader(socket.getInputStream()));
                                writer = new PrintWriter(socket.getOutputStream(), true);

                                String threadName =
                                        Thread.currentThread().getName()
                                                + Thread.currentThread().getId();
                                String requestName = socket.getInetAddress().getHostName();
                                while (true) {
                                    String content = reader.readLine();
                                    System.out.println(
                                            "接收到客户端消息：" + content + ",处理线程：" + threadName);
                                    writer.println(requestName + " 你的请求已处理，处理线程：" + threadName);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    reader.close();
                                    writer.close();
                                    socket.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
