package com.hms.test;


import com.hms.util.FileOperations;

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
        ArrayList<ArrayList<String>> arrayList = fo.findContentByDirectory("E:\\HMS", "main", "java");
        for (ArrayList<String> arrS : arrayList) {
            for (String str : arrS) {
                //System.out.println(str);
            }
        }
//        File file=new File("E:\\file.doc");
//        String fileName=file.getName();
//        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1);
//        System.out.println(fileTyle);



    }

}
