package com.hms.util;

import java.io.IOException;

/**
 * java编译
 */
public class CompileJava {

    /**
     * 编译Java文件
     *
     * @param encoding     源文件编码
     * @param classesPath  目标文件存放目录
     * @param jarPath      引用的jar包目录
     * @param javaFilePath java源文件位置
     */
    public static void compileJavaFile(String encoding, String classesPath, String jarPath, String javaFilePath) {
        try {
            // javac -encoding [encoding]  -d [classesPath] -Djava.ext.dirs=[jarPath]  [javaFilePath]
            Runtime.getRuntime().exec("cmd.exe /C javac "
                    + "-encoding " + encoding
                    + " -d " + classesPath
                    + " -Djava.ext.dirs=" + jarPath
                    + " " + javaFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编译Java文件
     *
     * @param encoding     源文件编码
     * @param classesPath  目标文件存放目录
     * @param javaFilePath java源文件位置
     */
    public static void compileJavaFile(String encoding, String classesPath, String javaFilePath) {
        try {
            // javac -encoding [encoding]  -d [classesPath]  [javaFilePath]
            String command = "cmd.exe /C javac "
                    + "-encoding " + encoding
                    + " -d " + classesPath
                    + " " + javaFilePath;
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
