package com.hms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * IO操作
 */
public class IOOperations {

    /**
     * 关闭资源
     *
     * @param socket socket
     * @param in     BufferedReader
     * @param out    BufferedWriter
     * @param br     BufferedReader
     * @return true or false
     */
    public static boolean close(Socket socket, BufferedReader in, BufferedWriter out, BufferedReader br) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (br != null) {
                br.close();
            }
            if (socket != null) {
                socket.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
