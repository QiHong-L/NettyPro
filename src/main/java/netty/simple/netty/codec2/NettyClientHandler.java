package netty.simple.netty.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import netty.simple.netty.codec.StudentPOJO;

import java.util.Random;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-12 17:59
 * @Modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发该通道
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 随机的发送Student 或者 Workder 对象到服务器
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (0 == random) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DateType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("玉麒麟 卢俊义").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DateType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(5).setName("老李").build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    /**
     * 当通道有读取事件时，会触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
