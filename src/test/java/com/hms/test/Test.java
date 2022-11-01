package com.hms.test;


import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();   //获取开始时间

        System.out.println("----------------------------------------------开始----------------------------------------------");
        FileOperations fo = new FileOperations();
        //fo.replaceSucceeded("F:\\HMS\\Desktop\\ApiUtil.java", "import", "HelloWorld");
       // System.out.println(fo.delete("F:\\HMS\\Desktop\\ApiUtil.java"));
//        System.out.println(fo.cut("F:\\HMS\\Desktop\\a","F:\\HMS\\Desktop\\b"));
        /**
         * 线程
         */

        System.out.println("----------------------------------------------结束----------------------------------------------");
        long endTime = System.nanoTime(); //获取结束时间

        System.out.println("程序运行耗时： " + (endTime - startTime) / 1000000 + "毫秒");

    }

}
