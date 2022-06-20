package netty.simple.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-16 21:59
 * @Modified By:
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个Netty提供的httpServerCodec  （codec = coder - decoder 的缩写）


        /**
         * HttpServerCodec 说明
         * 1. HttpServerCodec 是Netty 提供的处理http的 编-解码器
         */

        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        // 2. 增加一个自定义的 handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
    }
}
