package netty.simple.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-20 21:10
 * @Modified By:
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        // 使用相关的方法
        if (byteBuf.hasArray()) { // true

            byte[] content = byteBuf.array();

            // 将 content 转成字符串
            System.out.println(new String(content,Charset.forName("utf-8")));

            System.out.println("buteBuf= " + byteBuf);

            System.out.println(byteBuf.arrayOffset()); // 0
            System.out.println(byteBuf.readerIndex()); // 0
            System.out.println(byteBuf.writerIndex()); // 12

            System.out.println(byteBuf.readByte()); //

            int len = byteBuf.readableBytes(); // 可读的字节数 12
            System.out.println("len = " + len);

            // 使用for 取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));

        }
    }
}
