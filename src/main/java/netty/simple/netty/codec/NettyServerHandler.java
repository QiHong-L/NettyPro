package netty.simple.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-11 15:22
 * @Modified By:
 */
/*
    说明
    1.我们自定义的handler 需要继续Netty 规定好的某个HandlerAdapter
    2.这时我们自定义的Handler，才能称之为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取数据事件（这里我们读取客户端发送的消息）

    /**
     *
     * @param ctx 上下文对象，含有 管道 pipeline , 通道channel ，地址
     * @param msg 就是客户端发送的数据， 默认是object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取从客户端发送的StudentPojo.Student
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("客户端发送的数据 id = " + student.getId() + "name =" + student.getName());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是 write + flush
        // 将数据写入缓存，并刷新
        // 一般来讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~ 1",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常吗，一般需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
