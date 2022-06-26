package netty.simple.netty.hearbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-20 22:47
 * @Modified By:
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在 bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入一个Netty提供的 IdleStateHandler
                            /**
                             * 说明
                             * 1. IdleStateHandler 是Netty提供的处理空闲处理器
                             * 2. long readerIdleTIme: 表示多长时间没有读， 就会发送一个心跳检测包检测是否连接
                             * 3. long writerIdleTIme: 表示多长时间没有写操作，就会发送一个心跳检测包检测是否连接
                             * 4. long allIdleTime: 表示多长时间既没有读又没有写，就会发送一个心跳检测包检测是否连接
                             * 5. 当 IdleStateEvent 出发后，就会传递给管道 的下一个 handler 去处理
                             * 通过调用（出发）下一个handler的 userEventTiggered，在改方法中去处理 IdleStateEvent（读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(10,7000,7000, TimeUnit.SECONDS));
                            // 加入一个队空闲检测进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
