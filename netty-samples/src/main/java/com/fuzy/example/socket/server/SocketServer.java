package com.fuzy.example.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fuzy
 * @date 2021/9/16 21:22
 */
public class SocketServer {


    public static void main(String[] args)throws Exception {
        multi();
    }

    public static void multi()throws Exception{
        int port = 8080;
        int clientNo = 1;

        ServerSocket serverSocket = new ServerSocket(port);

        // 创建线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        try {

            while (true) {
                Socket socket = serverSocket.accept();
                exec.execute(new SingleServer(socket, clientNo));
                clientNo++;
            }

        } finally {
            serverSocket.close();
        }

    }

    public static void single()throws Exception{
        int port = 8080;
        //在端口上创建一个服务器套接字
        ServerSocket serverSocket = new ServerSocket(port);
        //监听客户端的链接
        Socket socket = serverSocket.accept();

        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        do {
            double length = dis.readDouble();
            System.out.println("服务器端收到的边长数据为："+length);
            double result = length*length;
            dos.writeDouble(result);
            dos.flush();
        }while (dis.readInt()!=0);
        socket.close();
        serverSocket.close();
    }
   static class SingleServer implements Runnable {

        private Socket socket;
        private int clientNo;

        public SingleServer(Socket socket, int clientNo) {
            this.socket = socket;
            this.clientNo = clientNo;
        }

        @Override
        public void run() {

            try {

                DataInputStream dis = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                DataOutputStream dos = new DataOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));

                do {

                    double length = dis.readDouble();
                    System.out.println("从客户端" + clientNo + "接收到的边长数据为：" + length);
                    double result = length * length;
                    dos.writeDouble(result);
                    dos.flush();

                } while (dis.readInt() != 0);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("与客户端" + clientNo + "通信结束");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
