package com.hms.test;


public class Test {
    public static void main(String[] args) {
        long startTime = System.nanoTime();   //获取开始时间

        System.out.println("----------------------------------------------开始----------------------------------------------");


        System.out.println("----------------------------------------------结束----------------------------------------------");
        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("程序运行耗时： " + (endTime - startTime) / 1000000 + "毫秒");

    }

}
