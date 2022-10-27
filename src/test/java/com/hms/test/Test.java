package com.hms.test;


import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        //fo.copy("src", "1");
        //fo.cut("1*", "F:\\HMS\\Desktop\\a");
        //fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
        fo.searchByFileName("src", "1java");
    }
}
