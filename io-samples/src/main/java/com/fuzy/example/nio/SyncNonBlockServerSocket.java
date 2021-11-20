package com.fuzy.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fuzy
 * @date 2021/11/13 16:56
 */
public class SyncNonBlockServerSocket implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public SyncNonBlockServerSocket()throws Exception{
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (true){
            try {
                int readyChannels = selector.select();//获取就绪连接
                if(readyChannels==0){
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    //如果有就绪连接，处理事件
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }catch (Exception e){

            }
        }
    }

    private void dispatch(SelectionKey key)throws IOException {
        if(key.isAcceptable()){ //是连接事件？
            System.out.println("连接事件");
            register(key);
        }else if(key.isReadable()){ //读事件
            System.out.println("读事件");
            read(key);
        }else if(key.isWritable()){ //写事件
            //TODO
            System.out.println("写事件");
        }
    }

    private void register(SelectionKey key) throws IOException {
        ServerSocketChannel channel= (ServerSocketChannel) key.channel(); //客户端连接
        SocketChannel socketChannel=channel.accept(); //获得客户端连接
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
    }
    private void read(SelectionKey key) throws IOException {
        //得到的是socketChannel
        SocketChannel channel= (SocketChannel) key.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        System.out.println("Server Receive Msg:"+new String(byteBuffer.array()));
    }

    public static void main(String[] args) throws Exception {
        SyncNonBlockServerSocket syncNonBlockServerSocket = new SyncNonBlockServerSocket();
        new Thread(syncNonBlockServerSocket).start();
    }
}
