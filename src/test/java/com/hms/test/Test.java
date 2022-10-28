package com.hms.test;


import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        //fo.copy("src", "1");
        //fo.cut("1*", "F:\\HMS\\Desktop\\a");
        //fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
        //fo.searchByFileName("E:/", ".java");
        fo.appendFile("F:\\HMS\\Desktop\\hello.txt");
        //isNewline(new File("F:\\HMS\\Desktop\\hello.txt"));
        //  https://blog.csdn.net/u011047968/article/details/107288140
    }


}
