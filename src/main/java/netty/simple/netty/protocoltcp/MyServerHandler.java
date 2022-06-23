package netty.simple.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author: liuqihong
 * @Description: 处理业务的handler
 * @Date Created in  2022-06-23 21:16
 * @Modified By:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        // 接收到数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到信息如下：");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content,CharsetUtil.UTF_8));

        System.out.println("服务器接收到消息包数量：" + (++count));

        // 回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLen = responseContent.getBytes(StandardCharsets.UTF_8).length;
        // 构建一个协议包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContent.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(messageProtocol);
    }
}
