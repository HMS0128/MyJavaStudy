package com.hms.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作：复制、删除、剪切、读取、修改、检索
 */
public class FileOperations {

    private static int index;

    /**
     * 复制文件(使用缓冲字节流)
     *
     * @param sourceAbsolutePath 源文件或源文件夹的绝对路径
     * @param pasteLocation      粘贴位置
     */
    public static void copy(String sourceAbsolutePath, String pasteLocation) throws Exception {
        if (new File(sourceAbsolutePath).isDirectory()) {
            copyFolder(sourceAbsolutePath, pasteLocation);
            return;
        }
        copyFile(sourceAbsolutePath, pasteLocation);
    }

    /**
     * 复制单个文件(使用缓冲字节流)
     *
     * @param sourceFileAbsolutePath 源文件绝对路径
     * @param pasteLocation          粘贴位置
     */
    private static void copyFile(String sourceFileAbsolutePath, String pasteLocation) {
        File file = new File(sourceFileAbsolutePath);
        File movePath = new File(pasteLocation);
        //如果是文件则复制文件
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(new FileOutputStream(movePath));
            byte[] b = new byte[1024];
            int temp = 0;
            while ((temp = in.read(b)) != -1) {
                out.write(b, 0, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assert out != null;
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 作用：复制文件夹(使用缓冲字节流)。
     * <p>
     * 【问题记录：不停的复制自己到自己里面（目标文件夹是源文件夹的子文件夹）】
     * <p>
     * 例如：当sourceAbsolutePath是当前运行位置（System.getProperty("user.dir")），
     * 并且pasteLocation值是不包含路径的值那么创建文件夹时会在当前运行目录中创建名称为 pasteLocation值的文件夹，
     * 因为递归遍历 %sourceAbsolutePath%文件夹 时其目录下的 %pasteLocation%文件夹 在变化，
     * 所以递归时在这个文件夹内出不来了（不停的复制自己到自己里面）。
     * <p>
     * 【问题解决】
     * <p>
     * 不允许这样操作，直接抛出异常。Windows系统自带的复制也存在这个问题：目标文件夹是源文件夹的子文件夹.
     * <p>
     *
     * @param sourceAbsolutePath 源文件夹绝对路径
     * @param pasteLocation      粘贴位置。注：粘贴位置不存在会自动创建（斜杠与反斜杠视为多级目录），粘贴位置不能是源文件夹的子目录。
     */
    public static void copyFolder(String sourceAbsolutePath, String pasteLocation) throws Exception {
        if (index > 3) {
            return;
        }

        //源文件夹
        File sourceFile = new File(sourceAbsolutePath);

        Path path = Paths.get(pasteLocation);
        //目标文件夹，不存会自动创建
        File targetFile = new File(Files.createDirectories(path).toFile().getAbsolutePath());


        // 文件名不能包含下列字符
        if (!(isValidFileName(sourceFile.getName()) && isValidFileName(targetFile.getName()))) {
            System.out.println(sourceFile.getName() + "和" + targetFile.getName());
            throw new Exception("文件名不能为空也不能包含下列字符:\\/:*?\"<>|");
        }

        if (targetFile.getParentFile().equals(sourceFile)) {
            throw new Exception("目标文件夹不允许是源文件夹的子文件夹");
        }

        if (!sourceFile.exists()) {
            throw new Exception("源文件夹不存在");
        }
        if (!sourceFile.isDirectory()) {
            throw new Exception("源文件夹不是目录");
        }

        if (targetFile.getParentFile().equals(sourceFile)) {
            throw new Exception("目标文件夹不允许是源文件夹的子文件夹");
        }

        if (!targetFile.isDirectory()) {
            throw new Exception("目标文件夹不是目录");
        }

        File[] files = sourceFile.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            // 文件要粘贴位置
            String pastePath = targetFile.getAbsolutePath() + "\\" + file.getName();
            if (file.isDirectory()) {
                //如果是目录则递归调用
                copyFolder(file.getAbsolutePath(), pastePath);
                index++;
            } else {
                //如果是文件则复制文件
                copyFile(file.getAbsolutePath(), pastePath);
            }
        }
    }

    /**
     * 文件名是否合法
     * <p>
     * 文件名不能包含下列字符：\/:*?"<>|
     *
     * @param fileName 文件名
     * @return 文件名是否合法
     */
    public static boolean isValidFileName(String fileName) {

        if (fileName == null || fileName.trim().length() == 0 || fileName.length() > 255) {
            return false;
        }

        // Windows绝对路径：1.第一个字符是字母   2.第二个字符是冒号  3.第三个字符是斜杠或反斜杠,后面字符排除  :*?\"<>|
        if (fileName.matches("([a-zA-z][:])([/|\\\\][^:*?\"<>|]*)")) {
            return true;
        }

        if (fileName.matches("[\\\\/:*?\"<>|]")) {

        }


//        String regEx = "[\\\\/:*?\"<>|]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(fileName);


        //return !fileName.matches("[\\\\/:*?\"<>|]");
        //return fileName.matches("([a-zA-z]:)([/|\\\\]((?![:*?\"<>|]).))*$");
        return fileName.matches("([a-zA-z][:])([/|\\\\][^:*?\"<>|]*)");
    }

}
