package netty.simple.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-23 21:08
 * @Modified By:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageDecoder()); // 解码器
        pipeline.addLast(new MyMessageEncoder()); // 编码器
        pipeline.addLast(new MyServerHandler());
    }
}
