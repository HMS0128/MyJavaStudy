package com.hms.test;


import com.hms.util.FileOperations;

import java.io.File;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
//        fo.copy("src", "1");
//        fo.cut("1*", "F:\\HMS\\Desktop\\a");
//        fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
//        fo.searchByFileName("E:/", ".java");
//
//        fo.appendFile("F:\\HMS\\Desktop\\hello.txt", "hello");
//
        ArrayList<File> arrayList = fo.getAllFileInDirectory("F:\\HMS\\Desktop");
        for (File file : arrayList) {
            System.out.println(file.getAbsolutePath());
        }

        ArrayList<File> arrayList1 = fo.getAllFileInDirectory("F:\\HMS\\Desktop");
        for (File file : arrayList) {
            System.out.println(file.getAbsolutePath());
        }

//        File file=new File("E:\\file.doc");
//        String fileName=file.getName();
//        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1);
//        System.out.println(fileTyle);E:\HMS\Study\Java\Code\demo1\src\main\license\License.java


    }

}
