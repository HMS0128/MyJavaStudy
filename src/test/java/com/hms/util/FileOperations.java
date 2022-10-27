package com.hms.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件操作：复制、删除、剪切、读取、修改、检索
 */
public class FileOperations {
    /**
     * 复制文件(使用缓冲字节流)
     *
     * @param sourcePath 源文件或源文件夹的绝对路径
     * @param targetPath 粘贴位置
     */
    public void copy(String sourcePath, String targetPath) {
        try {
            if (!(isValidFileName(sourcePath) && isValidFileName(targetPath))) {
                throw new Exception("文件名不合法！！！");
            }
            File source = new File(sourcePath);
            if (!source.exists()) {
                throw new Exception("源文件不存在");
            }
            if (source.isDirectory()) {
                copyFolder(sourcePath, targetPath);
                return;
            }
            copyFile(sourcePath, targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String path) {
        if (!isValidFileName(path)) {
            System.out.println("文件名不合法!!!");
            return false;
        }
        File files = new File(path);
        // 文件不存在，则退出
        if (!files.exists()) {
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
     * 复制单个文件(使用缓冲字节流)
     *
     * @param sourcePath 源文件路径
     * @param targetPath 粘贴位置(不能是目录)
     */
    private void copyFile(String sourcePath, String targetPath) throws Exception {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        /*
            targetPath 为绝对路径时：如果父目录不存在则创建。
            targetPath 为相对路径时：以绝对路径创建 File 对象。
         */
        if (isAbsolutePath(targetPath)) {
            String path;
            int slashIndex = targetPath.lastIndexOf("\\");
            int backSlashIndex = targetPath.lastIndexOf("/");
            if (slashIndex > backSlashIndex) {
                path = targetPath.substring(0, slashIndex);
            } else {
                path = targetPath.substring(0, backSlashIndex);
            }
            Files.createDirectories(Paths.get(path)).toFile();
        } else {
            target = new File(System.getProperty("user.dir") + "\\" + targetPath);
        }

        if (target.isDirectory()) {
            throw new Exception("目标位置不能是目录");
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {

            in = new BufferedInputStream(new FileInputStream(source));
            out = new BufferedOutputStream(new FileOutputStream(target));
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
     * @param sourcePath    源文件夹绝对路径
     * @param pasteLocation 粘贴目标文件夹。注：粘贴位置不存在会自动创建（斜杠与反斜杠视为多级目录），粘贴位置不能是源文件夹的子目录。
     */
    private void copyFolder(String sourcePath, String pasteLocation) throws Exception {
        // 源和目标文件夹，目标文件夹不存在则创建
        File sourceFile = new File(new File(sourcePath).getAbsolutePath());
        File targetFile = new File(Files.createDirectories(Paths.get(pasteLocation)).toFile().getAbsolutePath());

        if (!targetFile.isDirectory()) {
            throw new Exception("目标文件夹不是目录");
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
            String pastePath = targetFile.getAbsolutePath() + "\\" + file.getName();
            if (file.isDirectory()) {
                //如果是目录则递归调用
                copyFolder(file.getAbsolutePath(), pastePath);
            } else {
                //如果是文件则复制文件
                copyFile(file.getAbsolutePath(), pastePath);
            }
        }
    }

    /**
     * 文件名是否合法
     *
     * @param filePath 文件名
     * @return 文件名是否合法
     */
    public boolean isValidFileName(String filePath) {

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
        if (!isAbsolutePath(filePath)) {
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
    public boolean isOffspring(File sourceFile, File targetFile) {
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