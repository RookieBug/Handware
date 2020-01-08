package xyz.tulling.esp8266.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import xyz.tulling.esp8266.handler.HardwareHandler;

import java.nio.charset.Charset;

public class HardwareServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        // 1.生成日志信息
        System.out.println("报告");
        System.out.println("信息：有一客户端链接到本服务端");
        System.out.println("IP:" + ch.localAddress().getHostName());
        System.out.println("Port:" + ch.localAddress().getPort());
        System.out.println("报告完毕");

        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        ch.pipeline().addLast(new HardwareHandler()); // 客户端触发操作
        ch.pipeline().addLast(new ByteArrayEncoder());
    }
}
