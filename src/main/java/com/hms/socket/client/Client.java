package com.hms.socket.client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {

    //聊天框文本域
    private static final JTextArea txt = new JTextArea(10, 20);
    // 发送按钮
    private static JButton sendButton = new JButton("发送");
    // 输入消息的文本框
    private static JTextField inputBox = new JTextField(1);
    private static boolean flag = false;

    public static void main(String[] args) throws Exception {
        new Client().run();
    }

    /**
     * 构建键盘输入流，读取键盘输入的一行内容返回
     *
     * @return 返回键盘输入的内容
     */
    public static String readLineContent() throws Exception {
        // 构建键盘输入流
        InputStream inputStream = System.in;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String content = null;
        try {
            content = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content != null && content.trim().length() == 0) {
            readLineContent();
        } else {
            // System.out.println("发送的内容是：" + content);
            return content;
        }
        return content;
    }

    public void run() throws Exception {
        clientUI();
        ServerWrite serverWrite = new ServerWrite(new Socket("127.0.0.1", 10000));
        serverWrite.start();

//        while (true) {
//            ServerWrite serverWrite = new ServerWrite(new Socket("127.0.0.1", 10000));
//            serverWrite.start();
//
//            ServerRead serverRead = new ServerRead(new Socket("127.0.0.1", 10000));
//            serverRead.start();
//        }

    }


    private void clientUI() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("测试");
        // Setting the width and height of frame
        frame.setSize(550, 1000);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        // 调用定义的方法并添加组件到面板
        placeComponents(panel);
        // 设置界面可见
        frame.setVisible(true);
    }

    /**
     * 放置组件
     *
     * @param panel 组件对象
     */
    private void placeComponents(JPanel panel) {

        // 设置布局为 null
        panel.setLayout(null);

        //聊天框文本域 设置
        txt.setBounds(5, 5, 520, 880);
        txt.setEditable(false);
        txt.setLineWrap(true);//如果内容过长，自动换行.
        panel.add(txt);
        Font font = new Font(null, Font.BOLD, 20);
        txt.setFont(font);
        txt.setVisible(true);


        // 输入消息的文本框
        inputBox.setBounds(5, 900, 420, 30);
        panel.add(inputBox);
        inputBox.setEditable(true);

        // 发送按钮
        sendButton.setBounds(450, 900, 80, 30);
        panel.add(sendButton);


    }

    /**
     * 读取服务端信息的线程。
     */
    private static class ServerRead extends Thread {
        private final Socket readSocket;

        ServerRead(Socket readSocket) {
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
                    System.out.println("读取的服务端信息是：" + str);
                    flag = false;
                    txt.insert(str + "\n", 0);
                } else {
                    System.out.println("读取服务端信息时socket连接已经关闭！");
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
     * 写入信息到服务端的线程。
     */
    private static class ServerWrite extends Thread {
        private final Socket writeSocket;

        ServerWrite(Socket writeSocket) {
            this.writeSocket = writeSocket;
        }

        @Override
        public void run() {
            super.run();
            // 信息发送到服务端
            write();
        }

        public void write() {

            try {
                // 按钮事件
                sendButton.addActionListener(e -> {
                    String content = inputBox.getText();
                    if (!writeSocket.isClosed()) {
                        if (content.equals("exit")) {
                            System.out.println("退出！");
                            // 退出！
                            System.exit(0);
                        } else {
                            BufferedWriter bufferedWriter = null;
                            try {
                                bufferedWriter = new BufferedWriter(new OutputStreamWriter(writeSocket.getOutputStream()));
                                bufferedWriter.write(content + "\r\n");
                                bufferedWriter.flush();
                                System.out.println("写入服务端的信息是：" + content);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            } finally {
                                try {
                                    if (bufferedWriter != null) {
                                        bufferedWriter.close();
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                    } else {
                        System.out.println("写入服务端信息时socket连接已经关闭！");
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}