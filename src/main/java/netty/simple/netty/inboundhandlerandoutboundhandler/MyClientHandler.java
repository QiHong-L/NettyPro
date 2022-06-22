package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 21:43
 * @Modified By:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {

    }

    // 重写channelActive 发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
//        ctx.writeAndFlush(Unpooled.copiedBuffer(""));
//        ctx.writeAndFlush(123456l); // 发送的是一个Long

        /**
         * 分析
         * 1.abcdabcdabcdabcd 是 16个字节
         * 2.该处理器的钱一个handler 是 MyLongToByteEncoder
         * 3.MyLongToByteEncoder 的父类 MessageToByteEncoder
         * 4.父类 MessageToByteEncoder 有方法
         *     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
         *         ByteBuf buf = null;
         *
         *         try {
         *             if (this.acceptOutboundMessage(msg)) { // 判断当前msg 是不是应该处理的类型，
         *                                                          如果是就处理，如果不是就跳过encode方法
         *                 I cast = msg;
         *                 buf = this.allocateBuffer(ctx, msg, this.preferDirect);
         *
         *                 try {
         *                     this.encode(ctx, cast, buf);
         *                 } finally {
         *                     ReferenceCountUtil.release(msg);
         *                 }
         *
         *                 if (buf.isReadable()) {
         *                     ctx.write(buf, promise);
         *                 } else {
         *                     buf.release();
         *                     ctx.write(Unpooled.EMPTY_BUFFER, promise);
         *                 }
         *
         *                 buf = null;
         *             } else {
         *                 ctx.write(msg, promise);
         *             }
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
    }
}
