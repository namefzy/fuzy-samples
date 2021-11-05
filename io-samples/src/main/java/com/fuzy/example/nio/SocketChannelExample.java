package com.fuzy.example.nio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * @author fuzy
 * @date 2021/11/5 20:56
 */
public class SocketChannelExample {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        String host = "localhost";

        // 创建一个套接字并将其连接到指定端口号
        Socket socket = new Socket(host, port);
        DataInputStream dis = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));

        DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(socket.getOutputStream()));

    }
}
