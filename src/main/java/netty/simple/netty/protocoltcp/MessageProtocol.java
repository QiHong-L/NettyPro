package netty.simple.netty.protocoltcp;

/**
 * @Author: liuqihong
 * @Description:   协议包
 * @Date Created in  2022-06-23 21:31
 * @Modified By:
 */
public class MessageProtocol {
    private int len; // 关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
