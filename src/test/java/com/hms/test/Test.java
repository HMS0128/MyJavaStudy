package com.hms.test;


import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {

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
//        File file = new File("F:\\HMS\\Desktop\\b");
//        File[] files = file.listFiles();
//        System.out.println(files == null);

        long startTime = System.nanoTime();   //获取开始时间

        FileOperations fo = new FileOperations();
        System.out.println(fo.delete("a"));

        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("程序运行耗时： " + (endTime - startTime) + "毫秒！");
        //while: 17454400毫秒！ 18148900毫秒！ 16742200毫秒！ 18827500毫秒！22605800毫秒！
        //if: 17137300毫秒！ 17552900毫秒！17353600毫秒！17016500毫秒！24313700毫秒！


    }

}
