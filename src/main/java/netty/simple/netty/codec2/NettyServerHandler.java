package netty.simple.netty.codec2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import netty.simple.netty.codec.StudentPOJO;

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
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    // 读取数据事件（这里我们读取客户端发送的消息）

    /**
     *
     * @param ctx 上下文对象，含有 管道 pipeline , 通道channel ，地址
     * @param msg 就是客户端发送的数据， 默认是object
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        // 根据 dataType 显示不同的信息
        MyDataInfo.MyMessage.DateType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DateType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生 id = " + student.getId() + "学生 name =" + student.getName());

        } else if (dataType == MyDataInfo.MyMessage.DateType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人 age = " + worker.getAge() + "工人 name =" + worker.getName());
        } else  {
            System.out.println("传输与类型不正确");
        }
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
