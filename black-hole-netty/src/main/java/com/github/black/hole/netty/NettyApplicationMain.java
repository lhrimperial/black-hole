package com.github.black.hole.netty;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author hairen.long
 * @date 2020/5/18
 */
public class NettyApplicationMain {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap("Content of the String".getBytes("utf-8"));
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer = decoder.decode(buffer);
        String s = charBuffer.toString();
        System.out.println(s);

        buffer.flip();
        String ss =new String(buffer.array(), buffer.position(), buffer.limit(), "utf-8");
        System.out.println(ss);
    }
}
