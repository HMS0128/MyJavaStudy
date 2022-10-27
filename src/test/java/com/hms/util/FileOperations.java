package com.hms.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件操作：复制、删除、剪切、搜索、修改
 */
public class FileOperations {
    /**
     * 作用：复制单个文件或复制整个文件夹的内容(使用缓冲字节流)
     * <p>
     * 说明：源是文件夹时，目标也认定为文件夹。源是单个文件时，目标也认定为单个文件
     *
     * @param sourcePath 源路径
     * @param targetPath 目标路径（不存在会创建）。
     */
    public boolean copy(String sourcePath, String targetPath) {
        try {
            if (!(isValidFileName(sourcePath) && isValidFileName(targetPath))) {
                System.out.println("文件名不合法！！！");
                return false;
            }

            File source = new File(sourcePath);
            File target = new File(targetPath);

            if (source.exists()) {
                if (!isAbsolutePath(sourcePath)) {
                    source = new File(source.getAbsolutePath());
                }
            } else {
                System.out.println("文件：" + source + "  不存在！！！");
                return false;
            }

            // 源是文件夹时，目标认定为文件夹。源是单个文件时，目标认定为单个文件
            if (source.isDirectory()) {

                copyFolder(source, target);
            } else if (source.isFile()) {
                copyFile(source, target);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param path 单个文件路径或文件夹路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String path) {
        if (!isValidFileName(path)) {
            System.out.println("文件名不合法!!!");
            return false;
        }

        File files = new File(path);
        if (!files.exists()) {
            if (isAbsolutePath(path)) {
                System.out.println("文件：" + files.getPath() + "  不存在！！！");
            } else {
                System.out.println("文件：" + System.getProperty("user.dir") + "\\" + files.getPath() + "  不存在！！！");
            }
            return false;
        }

        if (files.isFile()) {
            return files.delete();
        } else {
            File[] listFiles = files.listFiles();
            if (listFiles == null) {
                return false;
            }
            for (File file : listFiles) {
                deleteFile(file.getPath());
            }
        }
        return files.delete();
    }

    /**
     * 作用：剪切单个文件或整个文件夹
     * <p>
     * 思路：先复制，复制成功后执行删除。
     *
     * @param source 源单个文件或文件夹的路径
     * @param target 目标单个文件或文件夹的路径
     * @return 是否剪切成功
     */
    public boolean cut(String source, String target) {
        if (copy(source, target)) {
            return deleteFile(source);
        }
        return false;
    }

    /**
     * 作用：根据文件名称模糊查询出目录中匹配的文件。
     * <p>
     * 思路：遍历目录，如果匹配就打印出来
     *
     * @param directory 目录
     * @param filName   条件
     */
    public void searchByFileName(String directory, String filName) {
        if (!isValidFileName(directory)) {
            System.out.println("目录不合法！！！");
            return;
        }
        File dir = new File(directory);
        if (!dir.exists()) {
            System.out.println("目录不存在！！！");
            return;
        }
        if (!dir.isDirectory()) {
            System.out.println("不是一个目录！！！");
            return;
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                searchByFileName(file.getAbsolutePath(), filName);
            } else if (file.getName().contains(filName)) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    /**
     * 复制单个文件(使用缓冲字节流)
     *
     * @param source 源文件路径
     * @param target 粘贴位置(是一个具体的文件)，不存在会创建。
     */
    private void copyFile(File source, File target) throws Exception {
        if (target.isDirectory()) {
            throw new Exception(target + " 是一个文件夹");
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(source));
            out = new BufferedOutputStream(new FileOutputStream(target));
            byte[] b = new byte[1024];
            int temp;
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
     * @param sourceFile 源文件夹绝对路径
     * @param targetFile 粘贴目标文件夹。注：粘贴位置不存在会自动创建（斜杠与反斜杠视为多级目录），粘贴位置不能是源文件夹的子目录。
     */
    private void copyFolder(File sourceFile, File targetFile) throws Exception {
        if (targetFile.isFile()) {
            throw new Exception("目标不能是一个文件");
        }
        // 目标文件不存在则创建
        Files.createDirectories(Paths.get(targetFile.getAbsolutePath()));

        if (!targetFile.isDirectory()) {
            throw new Exception("目标不是目录");
        } else if (isOffspring(sourceFile, targetFile)) {
            throw new Exception("目标文件夹不允许是源文件夹的子文件夹");
        } else if (sourceFile.equals(targetFile)) {
            throw new Exception("目标文件夹不允许是源文件夹");
        }

        // 获取源文件夹下所有文件和目录
        File[] files = sourceFile.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            // 文件要粘贴位置
            File pastePath = new File(targetFile.getAbsolutePath() + "\\" + file.getName());
            if (file.isDirectory()) {
                //如果是目录则递归调用
                copyFolder(file, pastePath);
            } else {
                //如果是文件则复制文件
                copyFile(file, pastePath);
            }
        }
    }

    /**
     * 文件名是否合法
     *
     * @param filePath 文件名
     * @return 文件名是否合法
     */
    private boolean isValidFileName(String filePath) {

        // Windows10 以前版本对最大路径限制为260个字符，Windows10可以修改注册表取消最大路径限制
        if (filePath == null || filePath.trim().length() == 0 || filePath.length() > 255) {
            return false;
        }
        /*
           是否是绝对路径且合法
           Windows绝对路径：1.第一个字符是盘符（是否存在）   2.第二个字符是冒号  3.第三个字符是斜杠或反斜杠,后面字符排除  :*?\"<>|
         */
        if (isAbsolutePath(filePath) && filePath.matches("([a-zA-z][:])([/|\\\\][^:*?\"<>|]*)")) {
            return true;
        }
        // 相对路径不能以 / 和 \ 开头
        if (!isAbsolutePath(filePath)) {
            if (filePath.charAt(0) == '/' || filePath.charAt(0) == '\\') {
                return false;
            }
            // 获取绝对路径：项目运行目录\文件名
            filePath = System.getProperty("user.dir") + "\\" + filePath;
            if (filePath.length() > 255) {
                return false;
            }
        }
        // 绝对路径是否合法。
        return filePath.matches("([a-zA-z][:])([/|\\\\][^:*?\"<>|]*)");
    }

    /**
     * 目标目录是否是源目录的子目录
     *
     * @param sourceFile 源目录
     * @param targetFile 目标目录
     * @return 目标目录是否是源目录的子目录
     */
    private boolean isOffspring(File sourceFile, File targetFile) {
        File[] files = sourceFile.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.equals(targetFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是绝对路径
     *
     * @param path 路径
     * @return 是否是绝对路径
     */
    private boolean isAbsolutePath(String path) {
        return new File(path.charAt(0) + ":\\").exists();
    }


}