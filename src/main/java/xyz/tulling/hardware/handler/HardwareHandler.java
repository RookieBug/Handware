package xyz.tulling.hardware.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import xyz.tulling.hardware.dao.RegisterInfoDao;
import xyz.tulling.hardware.entry.RegisterInfo;
import xyz.tulling.hardware.exception.UnKnowCardException;
import xyz.tulling.hardware.util.Utils;

import java.io.UnsupportedEncodingException;

public class HardwareHandler extends ChannelInboundHandlerAdapter {
    private String msg;
    private Logger logger = Logger.getLogger(HardwareHandler.class);

    /**
     * 通道活跃
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
    }

    /**
     * 通道不活跃
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");


    }

    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
        System.out.println("客户端收到服务器数据:");
        ByteBuf rev = (ByteBuf) msg;
        this.msg = getMessage(rev);     // 读取到消息
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("服务端接收数据完毕..");
        System.out.println(msg);
        // 记录签到信息
        RegisterInfoDao rid = Utils.getMapper(RegisterInfoDao.class);
        try {
            RegisterInfo registerInfo = Utils.getRegisterInfo(msg);
            Integer rows = rid.insRegisterInfo(registerInfo);
            if (rows >= 0) {
                logger.info("签到成功！");
            } else {
                logger.info("签到失败！");
            }
        } catch (UnKnowCardException uke) {
            logger.info(uke.getMsg());
        }
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        // tx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
