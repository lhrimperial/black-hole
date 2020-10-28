package com.github.black.hole.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/5/18
 */
public class HelloWorldServer {
    private int port;

    public static void main(String[] args) {
        new HelloWorldServer(8080).start();
    }

    public HelloWorldServer(int port) {
        this.port = port;
    }

    public void start() {
        /**
         * 创建两个EventLoopGroup，即两个线程池，boss线程池用于接收客户端的连接，一个线程监听一个端口，一般只会监听一个端口所以只需一个线程
         * work池用于处理网络连接数据读写或者后续的业务处理（可指定另外的线程处理业务，work完成数据读写）
         */
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            /**
             * 实例化一个服务端启动类， group（）指定线程组 channel（）指定用于接收客户端连接的类，对应java.nio.ServerSocketChannel
             * childHandler（）设置编码解码及处理连接的类
             */
            ServerBootstrap server =
                    new ServerBootstrap()
                            .group(boss, work)
                            .channel(NioServerSocketChannel.class)
                            .localAddress(new InetSocketAddress(port))
                            .option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true)
                            .childHandler(
                                    new ChannelInitializer<SocketChannel>() {
                                        @Override
                                        protected void initChannel(SocketChannel ch)
                                                throws Exception {
                                            ch.pipeline()
                                                    .addLast("decoder", new StringDecoder())
                                                    .addLast("encoder", new StringEncoder())
                                                    .addLast(new HelloWorldServerHandler())
                                                    .addLast(new HelloWorldServerHandler1());
                                        }
                                    });
            // 绑定端口
            ChannelFuture future = server.bind().sync();
            System.out.println("server started and listen " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldServerHandler active");
            Map<String, String> map = new HashMap(); // Maps.newHashMap();
            map.put("1", "1");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server channelRead..");
            System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
            ctx.channel().writeAndFlush("server write" + msg);
            ctx.write("server write" + msg);
            ctx.flush();

            ctx.fireChannelRead(msg);
        }
    }

    public static class HelloWorldServerHandler1 extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldServerHandler active");
            Map<String, String> map = new HashMap(); // Maps.newHashMap();
            map.put("1", "1");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server channelRead..");
            System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
            ctx.write("server write" + msg);
            ctx.flush();
        }
    }
}
