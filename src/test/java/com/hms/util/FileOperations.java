package com.hms.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 文件操作：复制、删除、剪切、搜索、修改
 */
public class FileOperations {

    /**
     * 获取指定目录下的文件及子孙目录下的文件（排除所有文件夹本身）。
     */
    private ArrayList<File> allFileInDirectory = new ArrayList<>();

    /**
     * 作用：复制单个文件或复制整个文件夹的内容(使用缓存字节流)
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
     * 复制单个文件(使用缓存字节流)
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

    /**
     * 文件结尾是否存在换行或回车
     *
     * @param file 文件对象
     * @return 文件结尾是否存在换行或回车
     */
    private boolean isExistNewline(File file) {
        /*
            RandomAccessFile实例：创建一个随机访问文件流读，随意写来，
            参数一：指定一个文件对象。
            参数二：
            "r" 以只读方式打开。 调用结果对象的任何写入方法都将导致抛出 IOException。
            "rw" 打开用于读写。 如果该文件尚不存在，则将尝试创建它。
            "rws" 与 "rw" 一样，为读取和写入而打开，并且还要求对文件内容或元数据的每次更新都同步写入底层存储设备。
            "rwd" 像 "rw" 一样打开读写，并且还要求对文件内容的每次更新都同步写入底层存储设备。
         */
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            // 指针位置开始为0，所以最大长度为 length-1  （如：文件共10行，指针0~9，9指向最后一行）
            long fileLastPointer = randomAccessFile.length() - 1;
            // 设置文件指针偏移量，指向最后一行。
            randomAccessFile.seek(fileLastPointer);
            // 读取最后一行,第一个字符
            int readByte = randomAccessFile.readByte();
            // 结尾是否有换行或回车
            if (0xA == readByte || 0xD == readByte) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 向文件中追加内容到新行（BufferedWriter）
     *
     * @param filePath 文件路径
     * @param content  追加的内容
     */
    public void appendFile(String filePath, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(filePath);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            if (isExistNewline(file)) {
                bufferedWriter.write(content + "\r\n");
            } else {
                bufferedWriter.write("\r\n" + content + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedWriter != null;
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 作用：根据指定字符串，查找指定文件中是否包含该字符串，出现在第几行。
     * <p>
     * 思路：使用 BufferedReader 读取文件，将查询结果保存在 ArrayList数组中。
     *
     * @param path    文件路径
     * @param content 查找的内容
     * @return 返回包含查找内容的所有行的数组
     */
    public ArrayList<String> findFileContent(String path, String content) {
        if (!isValidFileName(path)) {
            System.out.println("文件名:" + path + "  不合法！！！");
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件:" + path + "  不存在！！！");
            return null;
        }
        if (file.isDirectory()) {
            System.out.println("文件:" + path + "  是一个目录，而不是具体的文件！！！");
            return null;
        }
        BufferedReader bufferedReader = null;
        ArrayList<String> result = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String conStr;
            int i = 0;
            while ((conStr = bufferedReader.readLine()) != null) {
                i++;
                if (conStr.contains(content)) {
                    result.add(content + " 出现在文件 " + path + "  第 " + i + " 行，该内容是：" + conStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取指定目录下的文件及子孙目录下的文件（排除所有文件夹本身）。
     *
     * @param directoryPath 目录位置
     * @return 所有文件
     */
    private ArrayList<File> setAllFileInDirectory(String directoryPath) {
        if (!isValidDirectory(directoryPath)) {
            return null;
        }
        File[] files = new File(directoryPath).listFiles();
        // 递归结束条件
        if (files == null || files.length == 0) {
            return allFileInDirectory;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                setAllFileInDirectory(file.getAbsolutePath());
            } else {
                allFileInDirectory.add(file);
            }
        }
        return allFileInDirectory;
    }

    public ArrayList<File> getAllFileInDirectory(String directoryPath) {
        ArrayList<File> result = setAllFileInDirectory(directoryPath);
        allFileInDirectory = new ArrayList<>();
        return result;
    }

    /**
     * 获取指定目录下的文件及子孙目录下的文件中是指定拓展名的文件。
     *
     * @param directoryPath     目录位置
     * @param fileNameExtension 文件拓展名
     * @return 所有文件
     */
    public ArrayList<File> getAllFileInDirectory(String directoryPath, String fileNameExtension) {
        ArrayList<File> result = setAllFileInDirectory(directoryPath, fileNameExtension);
        allFileInDirectory = new ArrayList<>();
        return result;
    }

    /**
     * 获取指定目录下的文件及子孙目录下的文件中是指定拓展名的文件。
     *
     * @param directoryPath     目录位置
     * @param fileNameExtension 文件拓展名
     * @return 所有文件
     */
    private ArrayList<File> setAllFileInDirectory(String directoryPath, String fileNameExtension) {
        if (!isValidDirectory(directoryPath)) {
            return null;
        }
        File[] files = new File(directoryPath).listFiles();
        // 递归结束条件
        if (files == null || files.length == 0) {
            return allFileInDirectory;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                setAllFileInDirectory(file.getAbsolutePath(), fileNameExtension);
            } else {
                String fileName = file.getName();
                if (fileName.substring(fileName.lastIndexOf(".") + 1).equals(fileNameExtension)) {
                    allFileInDirectory.add(file);
                }
            }
        }
        return allFileInDirectory;
    }

    /**
     * 作用：根据指定字符串，查找指定目录中有那些文件中包含该字符串，出现在第几行。
     * <p>
     * 思路：1、获取指定目录下的文件及子孙目录下的文件（排除所有文件夹本身）。2、获取每个文件的查询结果集。3、将每个文件的查询结果集保存在 ArrayList 数组中（二维数组）。
     *
     * @param path    目录位置
     * @param content 查找内容
     * @return 返回一个查找结果数组
     */
    public ArrayList<ArrayList<String>> findContentByDirectory(String path, String content) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<File> files = getAllFileInDirectory(path);
        for (File file : files) {
            result.add(findFileContent(file.getAbsolutePath(), content));
        }
        return result;
    }

    /**
     * 作用：根据指定字符串，查找指定目录中指定后缀名的文件中那些文件中包含该字符串，出现在第几行。
     * <p>
     * 思路：1、获取指定目录下的文件及子孙目录下的文件（排除所有文件夹本身）。2、获取每个文件的查询结果集。3、将每个文件的查询结果集保存在 ArrayList 数组中（二维数组）。
     *
     * @param path              目录位置
     * @param content           查找内容
     * @param fileNameExtension 文件扩展名
     * @return 返回一个查找结果数组
     */
    public ArrayList<ArrayList<String>> findContentByDirectory(String path, String content, String fileNameExtension) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<File> files = getAllFileInDirectory(path, fileNameExtension);
        for (File file : files) {
            result.add(findFileContent(file.getAbsolutePath(), content));
        }
        return result;
    }

    /**
     * 判断给定的路径是否是一个合法且存在的目录
     *
     * @param directoryPath 目录路径
     */
    public boolean isValidDirectory(String directoryPath) {
        if (!isValidFileName(directoryPath)) {
            System.out.println("目录名: " + directoryPath + "  不合法！！！");
            return false;
        }
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            System.out.println("目录: " + directoryPath + "  不存在！！！");
            return false;
        }
        if (dir.isFile()) {
            System.out.println(directoryPath + "  是一个文件，而不是一个目录！！！");
            return false;
        }
        return true;
    }


}