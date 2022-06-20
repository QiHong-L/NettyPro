package netty.simple.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-20 22:11
 * @Modified By:
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 使用map 实现私聊
//    public static Map<String,Channel> channels = new HashMap<>();
//    public static Map<User,Channel> channels2 = new HashMap<>();

    // 定义一个 channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf =  new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    /**
     * 表示连接建立，一旦连接，第一个被执行
     * 将当前channel 加入到 channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天信息推送给其他在线客户端

        /**
         * writeAndFlush 该方法会将 channelGroup 中所有的channel 遍历，并发送消息
         * 我们不需要自己遍历
         */
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 加入聊天" + sdf.format(new Date()) + "\n");

        channelGroup.add(channel);
    }

    /**
     * 表示channel处于一个活动的状态，提示 xx上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    /**
     * 当channel处于一个非活动状态，提示xx下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    /**
     * 断开连接，将xx客户离开信息推送给当前在线的客户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 离开了~\n");
        System.out.println("channelGroup size : "+ channelGroup.size());
    }

    /**
     * 读取数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前channel
        Channel channel = ctx.channel();
        // 这是我们遍历channelGroup ， 根据不同的情况，会送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) { // 不是当前 channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            } else {
                ch.writeAndFlush("[自己] 发送了消息" + msg + "\n");
            }
        });
    }

    /**
     * 发生异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
