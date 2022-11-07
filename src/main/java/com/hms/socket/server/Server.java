package com.hms.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean flag = false;

    public static void main(String[] args) throws Exception {

        run();
    }

    /**
     * 连接客户端
     */

    private static void run() throws Exception {

        //创建服务器端socket：指定监听端口
        ServerSocket server = new ServerSocket(10000);

        do {
            try {
                Socket readSocket = server.accept();
                ClientRead clientRead = new ClientRead(readSocket);
                clientRead.start();
                clientRead.join();
                while (flag) {
                    Socket writeSocket = server.accept();
                    ClientWrite clientWrite = new ClientWrite(writeSocket);
                    clientWrite.start();
                    clientWrite.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!flag);

    }

    /**
     * 读取客户端信息的线程。
     */
    private static class ClientRead extends Thread {
        private final Socket readSocket;

        ClientRead(Socket readSocket) {
            this.readSocket = readSocket;
        }

        @Override
        public void run() {
            super.run();
            // 接收客户端信息
            read();
        }


        public void read() {
            BufferedReader bufferedReader = null;
            try {
                if (!readSocket.isClosed()) {
                    System.out.println("read...");
                    bufferedReader = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
                    String str = bufferedReader.readLine();
                    System.out.println("读取的客户端信息是：" + str);
                    flag = true;
                } else {
                    System.out.println("读取客户端信息时socket连接已经关闭！");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 写入信息到客户端的线程。
     */
    private static class ClientWrite extends Thread {
        private final Socket writeSocket;

        ClientWrite(Socket writeSocket) {
            this.writeSocket = writeSocket;
        }

        @Override
        public void run() {
            super.run();
            // 信息发送到客户端
            write();
        }

        public void write() {
            BufferedWriter bufferedWriter = null;
            try {
                if (!writeSocket.isClosed()) {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(writeSocket.getOutputStream()));
                    String str = "客户端：" + writeSocket.getInetAddress() + ":" + writeSocket.getPort();
                    bufferedWriter.write(str + "\r\n");
                    bufferedWriter.flush();
                    System.out.println("写入客户端的信息是：" + str);
                    flag = false;
                } else {
                    System.out.println("写入客户端信息时socket连接已经关闭！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}


