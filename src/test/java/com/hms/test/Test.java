package com.hms.test;


import com.hms.util.FileOperations;

import java.io.File;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        //fo.copy("src", "1");
        //fo.cut("1*", "F:\\HMS\\Desktop\\a");
        //fo.deleteFile("F:\\HMS\\Desktop\\test\\WorkorderController.java");
        //fo.searchByFileName("E:/", ".java");

        //fo.appendFile("F:\\HMS\\Desktop\\hello.txt", "hello");

        ArrayList<File> allFileInDirectory = fo.getAllFileInDirectory("F:\\HMS\\Desktop\\TestTextJava");
        System.out.println(allFileInDirectory);

    }


}
