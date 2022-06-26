package netty.simple.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.simple.netty.dubborpc.provider.HelloServiceImpl;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-26 11:36
 * @Modified By:
 */
public class NettyServerhandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        // 客户端在调用服务器的api 时，我们需要定义一个协议
        // 比如我们要求 每次发消息时都必须以某个字符串开头 “HelloService#hello#”
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
