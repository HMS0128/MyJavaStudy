package com.hms.socket.server;

import com.hms.util.IOOperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerA extends Thread {

    public static boolean flag = false;

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        BufferedReader br = null;
        try {
            //创建服务器端套接字：指定监听端口
            ServerSocket server = new ServerSocket(10000);
            //监听并等待客户端的连接
            socket = server.accept();
            //获取Socket的输入输出流接收和发送信息
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //接收客户端发送的信息
                String str = in.readLine();
                if (str == null) {
                    System.out.println("客户端A已退出！");
                    // 等待客户端连接
                    flag = true;
                    break;
                } else if (!str.equals("end")) {
                    System.out.println("客户端说：" + str);
                    String str2 = "";
                    //否则发送反馈信息
                    str2 = br.readLine();
                    //读到\n为止,因此一定要输入换行符
                    out.write(str2 + "\n");
                    out.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (IOOperations.close(socket, in, out, br)) {
                System.out.println("资源关闭成功");
            }
        }
    }


}