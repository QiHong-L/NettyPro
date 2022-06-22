package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 21:41
 * @Modified By:
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder.encode 被调用");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
