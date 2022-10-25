package com.hms.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 编译 JavaWeb 项目
 */
public class CompileJavaWebProject {
    /**
     * 项目中所有java文件的绝对路径集合
     */
    private ArrayList<String> allJavaFileAbsolutePath = new ArrayList<String>();

    /**
     * 构造方法
     *
     * @param projectAbsolutePath JavaWeb项目的绝对路径
     * @param outputDirectory     编译输出的目录位置
     * @param appName             JavaWeb项目的应用名称
     */
    public CompileJavaWebProject(String projectAbsolutePath, String outputDirectory, String appName) throws Exception {
        init(projectAbsolutePath);
        String encoding = "UTF-8";

        //将 src\webapp 目录内容，同步到 target\应用名称 目录下
        //copyFolder("F:\\HMS\\Desktop\\testBuid\\src\\main\\webapp", "F:\\HMS\\Desktop\\testBuid\\target\\pc");
        FileOperations.copyFolder("", outputDirectory);
        //将 src\main\resources 目录内容，同步到 target\应用名称\WEB-INF\classes 文件夹
        // copyFolder("F:\\HMS\\Desktop\\testBuid\\src\\main\\resources", "F:\\HMS\\Desktop\\testBuid\\target\\pc\\WEB-INF\\classes");
        //将 指定的jar包目录内容，同步到 target\应用名称\WEB-INF\lib 文件夹
        //copyFolder("F:\\HMS\\Desktop\\testBuid\\lib", "F:\\HMS\\Desktop\\testBuid\\target\\pc\\WEB-INF\\lib");

        String classesPath = "F:\\HMS\\Desktop\\testBuid\\target\\pc\\WEB-INF\\classes";
        String jarPath = "F:\\HMS\\Desktop\\testBuid\\lib";
        String javaFilePath = "";
        compileJavaFile(encoding, classesPath, jarPath, javaFilePath);

    }

    /**
     * 初始化属性值：allJavaFileAbsolutePath
     *
     * @param javaFileRootDirectory java文件的根目录
     */
    private void init(String javaFileRootDirectory) {
        File dir = new File(javaFileRootDirectory);
        File[] files = dir.listFiles();

        // 递归结束条件
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            boolean isJavaFile = files[i].getName().contains(".java");
            if (isJavaFile) {
                allJavaFileAbsolutePath.add(files[i].getAbsolutePath());
            }
            if (files[i].isDirectory()) {
                init(files[i].getAbsolutePath());
            }
        }
    }

    /**
     * 获取所有Java文件的路径
     */
    private ArrayList<String> getAllJavaFilePath() {
        return allJavaFileAbsolutePath;
    }

    /**
     * 编译Java文件
     *
     * @param encoding     源文件编码
     * @param classesPath  目标文件存放目录
     * @param jarPath      引用的jar包目录
     * @param javaFilePath java源文件位置
     */
    private void compileJavaFile(String encoding, String classesPath, String jarPath, String javaFilePath) {
        try {
            // 调用cmd.exe执行javac编译命令
            // javac命令示例：javac -encoding UTF-8  -d F:\HMS\Desktop\testBuid\target\pc\WEB-INF\classes -Djava.ext.dirs=F:\HMS\Desktop\testBuid\lib  F:\HMS\Desktop\myDirectory\*.java
            Runtime.getRuntime().exec("cmd.exe /C javac "
                    + "-encoding " + encoding
                    + " -d " + classesPath
                    + " -Djava.ext.dirs=" + jarPath
                    + " " + javaFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
