package com.hms.socket.client;

import com.hms.util.IOOperations;

import java.io.*;
import java.net.Socket;

public class ClientA {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        BufferedReader br = null;
        try {
            //创建Socket对象，连接指定的地址（服务器端的IP和端口）
            socket = new Socket("localhost", 10000);
            //获取Socket的输入输出流接收和发送信息
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //发送信息
                String str = br.readLine();
                out.write(str + "\n");
                out.flush();
                //如果输入的信息为"end"则终止连接
                if (str.equals("end")) {
                    break;
                }
                //否则接收并输出服务器端信息
                System.out.println("服务器端说：" + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (IOOperations.close(socket, in, out, br)) {
                System.out.println("资源关闭成功");
            }
        }
    }

}