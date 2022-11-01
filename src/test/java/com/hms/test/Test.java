package com.hms.test;


import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) throws Exception {

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
        //System.out.println(fo.replaceSucceeded("", "", ""));
        //fo.delete( "F:\\HMS\\Desktop\\b\\a");
        fo.cut("F:\\HMS\\Desktop\\a","F:\\HMS\\Desktop\\c");
       // System.out.println(fo.delete("F:\\HMS\\Desktop\\b"));
        long endTime = System.nanoTime(); //获取结束时间

        //Files.delete(Paths.get("F:\\HMS\\Desktop\\b\\a\\MyJavaStudy\\.git\\objects\\3a\\a83f28e488a9908ece35e09c4d961677d66b7d"));
        System.out.println("程序运行耗时： " + (endTime - startTime) / 1000000 + "毫秒");

    }

}
