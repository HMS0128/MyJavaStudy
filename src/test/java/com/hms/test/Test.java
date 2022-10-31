package com.hms.test;


import com.hms.util.FileOperations;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
       //fo.copy("src", "1");
       //fo.cut("1", "F:\\HMS\\Desktop\\a");
//        fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
//        fo.searchByFileName("E:/", ".java");
//
//        fo.appendFile("F:\\HMS\\Desktop\\hello.txt", "hello");
//

        //fo.deleteFile("1");
//        File file=new File("E:\\file.doc");
//        String fileName=file.getName();
//        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1);
//        System.out.println(fileTyle);E:\HMS\Study\Java\Code\demo1\src\main\license\License.java
        File file = new File("F:\\HMS\\Desktop\\a");
        fo.deleteFile("F:\\HMS\\Desktop\\a");


    }

}
