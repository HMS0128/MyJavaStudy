package com.hms.test;

import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {
        FileOperations fo = new FileOperations();
        fo.deleteFolder("F:\\HMS\\Desktop\\test");
    }
}
