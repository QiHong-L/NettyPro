package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-11 15:02
 * @Modified By:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        // 创建BossGroup 和 workerGroup
        // 说明
        // 1. 创建两个线程组 BoosGroup 和 workerGroup
        // 2. boosGroup 只处理连接请求，真正和客户端业务处理会交给 workerGroup完成
        // 3. 两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建服务器端的启动对象，配置参数
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 使用链式编程来进行设置
            bootstrap.group(bossGroup,workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NNioSocketChannel 作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列，等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象(匿名对象)
                        // 给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("客户socketchannel hashcode = " + socketChannel.hashCode());
                            // 可以使用一个集合管理 SocketChannel ， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue
                            // 或者 scheduleTaskQueue

                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup 的 EventLoopGroup 对应的管道设置处理器

            System.out.println("......服务器 is ready...");

            // 绑定一个端口并且同步，生成了一个ChannelFuture 对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 给 cf 注册一个监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
