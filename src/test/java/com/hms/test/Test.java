package com.hms.test;

import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        //fo.copy("src","2");
        fo.deleteFile("2");
    }
}
