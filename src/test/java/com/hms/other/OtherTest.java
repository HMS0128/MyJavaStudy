package com.hms.other;

import com.hms.socket.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OtherTest {
    public static void main(String[] args) throws Exception {
        System.out.print("输入：");
        //test();
        System.out.print("-------------------------------------------------聊天室-------------------------------------------------");
    }

    public static void test() {
        InputStream inputStream = System.in;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String content = null;
        try {
            content = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content != null && content.trim().length() == 0) {
            test();
        } else {
            System.out.println("发送的内容是：" + content);
        }
    }
}
