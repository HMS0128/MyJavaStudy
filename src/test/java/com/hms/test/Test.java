package com.hms.test;


import com.hms.util.FileOperations;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        //fo.copy("src", "1");
        //fo.cut("1*", "F:\\HMS\\Desktop\\a");
        //fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
        //fo.searchByFileName("E:/", ".java");
        appendFile1Test("F:\\HMS\\Desktop\\hello.txt");
        //isNewline(new File("F:\\HMS\\Desktop\\hello.txt"));
        //  https://blog.csdn.net/u011047968/article/details/107288140
    }

    /**
     * 向文件中追加内容
     */
    public static void appendFile1Test(String path) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            File file = new File(path);
            fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            if (isNewline(file)) {
                pw.append("Hello\r\n");
            } else {
                pw.append("\r\nHello\r\n");
            }
            pw.flush();
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fw != null;
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assert pw != null;
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isNewline(File file) {
        /*
            RandomAccessFile实例：创建一个随机访问文件流读，随意写来，
            参数一：指定一个文件对象。
            参数二：
            "r" 以只读方式打开。 调用结果对象的任何写入方法都将导致抛出 IOException。
            "rw" 打开用于读写。 如果该文件尚不存在，则将尝试创建它。
            "rws" 与 "rw" 一样，为读取和写入而打开，并且还要求对文件内容或元数据的每次更新都同步写入底层存储设备。
            "rwd" 像 "rw" 一样打开读写，并且还要求对文件内容的每次更新都同步写入底层存储设备。
         */
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            // 指针位置开始为0，所以最大长度为 length-1  （如：文件共10行，指针0~9，9指向最后一行）
            long fileLastPointer = randomAccessFile.length() - 1;
            // 设置文件指针偏移量，指向最后一行。
            randomAccessFile.seek(fileLastPointer);
            // 读取最后一行,第一个字符
            int readByte = randomAccessFile.readByte();
            System.out.println(readByte);
            if (0xA == readByte || 0xD == readByte) {
                System.out.println("结尾存在换行或回车");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
