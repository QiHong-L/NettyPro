package netty.simple.netty.simpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
        // 比如这里有一个非常耗费时间的业务 -》 异步执行 -》 提交改channel 对应的NIOEventLoop 的 taskQueue中，

        // 解决方案 1 用户程序自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~ 2",CharsetUtil.UTF_8));
                } catch (Exception ex) {
                    System.out.println("发生异常: " + ex.getMessage());
                }
            }
        });

        //2. 用户自定义定时任务 -》 该任务提交到 scheduleTaskQueue 中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~ 3",CharsetUtil.UTF_8));
                } catch (Exception ex) {
                    System.out.println("发生异常: " + ex.getMessage());
                }
            }
        },5, TimeUnit.SECONDS);

        // 3. 非当前Reactor 线程调用 Channel 的各种方式



        System.out.println("go on ...");


//        System.out.println("server ctx = " + ctx);
//
//        // 将 msg 转成一个 ByteBuf
//        // ByteBuf 是 Netty提供的 不是NIO的 ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是: " + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址: " + ctx.channel().remoteAddress());
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
