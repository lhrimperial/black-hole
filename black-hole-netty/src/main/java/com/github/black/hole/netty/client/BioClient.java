package com.github.black.hole.netty.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

/**
 * @author hairen.long
 * @date 2021/8/16
 */
public class BioClient {

    public static void main(String[] args) {
        new BioClient().connect("127.0.0.1", 8080);
    }

    public void connect(String host, int port) {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                writer.println("Hello Server, I am " + socket.getInetAddress());
                String response = reader.readLine();
                System.out.println("server return message : " + response);

                Thread.sleep(3000);
            }
        } catch (Exception e) {
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
    }
}
