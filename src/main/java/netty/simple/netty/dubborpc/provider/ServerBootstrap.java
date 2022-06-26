package netty.simple.netty.dubborpc.provider;

import netty.simple.netty.dubborpc.netty.NettyServer;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-26 11:29
 * @Modified By:
 */
// ServerBootstrap 会启动一个服务提供者，就是NettyServer
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }
}
