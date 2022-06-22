package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 21:33
 * @Modified By:
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decode 会根据接收到的数据，被调用多次，直到没有新的元素被添加到List，
     *  或者ByteBuffer 没有更多的可读字节位置
     * 如果List out 不为空，就会将List的内容传递给下一个channelinboundhandler处理，该处理器的方法也会被调用多次
     *
     * @param ctx 上下文对象
     * @param byteBuf 入站的 ByteBuf
     * @param list List 集合，将解码后的数据传给下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用" +  byteBuf.toString());
        // 因为 Long 是 8个字节,需要判断有8个字节才能读取一个Long
        if (byteBuf.readableBytes() >= 8) {
            list.add(byteBuf.readLong());
        }
    }
}
