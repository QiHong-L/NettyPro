package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 22:27
 * @Modified By:
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        // 在 不需要判断数据是否足够读取，内部会进行判断处理
        list.add(in.readLong());
    }
}
