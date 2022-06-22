package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 21:31
 * @Modified By:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 入站的handler 进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());
        // 入站的handler 进行编码 MyLongToByteEncoder
        pipeline.addLast(new MyLongToByteEncoder());

        // 自定义handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
