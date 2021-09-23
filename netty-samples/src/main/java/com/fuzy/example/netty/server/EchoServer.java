package com.fuzy.example.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author fuzy
 * @date 2021/9/23 21:00
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args)throws Exception {
        int port = 8080;
        new EchoServer(port).start();
    }

    public void start()throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建EventLoopGroup 类似于线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            //创建服务器引导类
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字
                    //当一个新的连接被接受时，一个新的channel会被创建;而ChannelInitializer将会把一个你的EchoServerHandler的实例添加到该Channel的pipeline中。
                    .childHandler(new ChannelInitializer<SocketChannel>() {//
                        // 添加一个EchoServerHandler到子Channel的pipeline
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync();//异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            f.channel().closeFuture().sync();//获取Channel的CloseFuture，并且阻塞当前线程直到它完成
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
