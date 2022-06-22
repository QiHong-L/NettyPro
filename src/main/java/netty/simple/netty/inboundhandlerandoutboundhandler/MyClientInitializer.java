package netty.simple.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-22 21:40
 * @Modified By:
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入出站的handler 对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder());

        // 这是一个入站的解码器
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        // 加入一个自定义handler ，处理业务逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
