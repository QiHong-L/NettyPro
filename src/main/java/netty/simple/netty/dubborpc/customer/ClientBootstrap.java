package netty.simple.netty.dubborpc.customer;

import netty.simple.netty.dubborpc.netty.NettyClient;
import netty.simple.netty.dubborpc.publicInterface.HelloService;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-26 12:19
 * @Modified By:
 */
public class ClientBootstrap {

    // 这里我们定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        // 创建一个消费者
        NettyClient customer = new NettyClient();
        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        for(;;) {
            Thread.sleep(2 * 1000);
            // 通过代理对象调用服务提供者的方法（服务）
            String res = service.hello("你好，dubbo~");
            System.out.println("调用的结果=" + res);
        }

    }
}
