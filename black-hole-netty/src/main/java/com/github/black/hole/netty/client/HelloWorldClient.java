package com.github.black.hole.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hairen.long
 * @date 2020/5/18
 */
public class HelloWorldClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new HelloWorldClient().start(HOST, PORT);
    }

    public void start(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client =
                    new Bootstrap()
                            .group(group)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .handler(
                                    new ChannelInitializer<SocketChannel>() {
                                        @Override
                                        protected void initChannel(SocketChannel ch)
                                                throws Exception {
                                            ch.pipeline()
                                                    .addLast("decoder", new StringDecoder())
                                                    .addLast("encoder", new StringEncoder())
                                                    .addLast(new HelloWorldClientHandler());
                                        }
                                    });
            ChannelFuture future = client.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldClientHandler Active");
            Executor executor = new ScheduledThreadPoolExecutor(1);
            ((ScheduledThreadPoolExecutor) executor)
                    .scheduleAtFixedRate(
                            () -> {
                                String message =
                                        "In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. His book w"
                                                + "ill give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the process "
                                                + "of configuring and connecting all of Netty’s components to bring your learned about threading models in ge"
                                                + "neral and Netty’s threading model in particular, whose performance and consistency advantages we discuss"
                                                + "ed in detail In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. Hi"
                                                + "s book will give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the"
                                                + " process of configuring and connecting all of Netty’s components to bring your learned about threading "
                                                + "models in general and Netty’s threading model in particular, whose performance and consistency advantag"
                                                + "es we discussed in detailIn this chapter you general, we recommend Java Concurrency in Practice by Bri"
                                                + "an Goetz. His book will give We’ve reached an exciting point—in the next chapter;the counter is: 1 2222"
                                                + "sdsa ddasd asdsadas dsadasdas";
                                ctx.channel().writeAndFlush(message);
                            },
                            5,
                            60,
                            TimeUnit.SECONDS);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("HelloWorldClientHandler read Message:" + msg);
        }
    }
}
