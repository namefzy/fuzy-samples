package com.fuzy.example.nio;

import io.netty.buffer.ByteBuf;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fuzy
 * @date 2021/11/6 17:09
 */
public class SelectorExample {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(8080);
        ServerSocketChannel channel = socket.getChannel();
        //获取selector
        Selector selector = Selector.open();
        //设置位非阻塞连接
        channel.configureBlocking(false);
        //注册读事件
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        while (true){
            //获取已准备好的事件个数
            int readyChannels = selector.select();
            //当readyChannels等于0时，继续循环
            if(readyChannels==0){
                continue;
            }
            //获取就绪的channel集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                if(key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                //移除已处理的channel
                iterator.remove();

            }
        }
    }

    public void iterator(Selector selector) {
        Set selectedKeys = selector.selectedKeys();
        Iterator keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = (SelectionKey) keyIterator.next();
            if (key.isAcceptable()) {
                // a connection was accepted by a ServerSocketChannel.
            } else if (key.isConnectable()) {
                // a connection was established with a remote server.
            } else if (key.isReadable()) {
                // a channel is ready for reading
            } else if (key.isWritable()) {
                // a channel is ready for writing
            }
            keyIterator.remove();
        }
    }
}
