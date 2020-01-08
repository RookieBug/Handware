package xyz.tulling.esp8266.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.tulling.esp8266.config.HardwareServerInitializer;

public class HardwareServer {

    private int port;

    public HardwareServer() {
        port = 8081;
    }

    public HardwareServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.group(group, bossGroup)
                    .option(ChannelOption.SO_BACKLOG,300)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(this.port)
                    .childHandler(new HardwareServerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
            System.out.println(HardwareServer.class + " 启动正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        } catch (Exception e) {
            System.out.println("发生异常：" + e.getMessage());
        } finally {
            try {
                group.shutdownGracefully().sync();
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
