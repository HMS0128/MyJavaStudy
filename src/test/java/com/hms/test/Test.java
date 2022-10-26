package com.hms.test;

import com.hms.util.FileOperations;

public class Test {
    public static void main(String[] args) {
        try {
            //
            //System.out.println(new File("F:\\HMS\\Desktop\\MyJavaStudy\\src").isDirectory());
//            FileOperations.copyFolder("F:\\HMS\\Desktop\\MyJavaStudy", "f:/2");
            //System.out.println(FileOperations.isValidFileName("F:\\HMS\\Desktop?"));
            // \/:*?"<>|
            //String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//            String regEx = "[\\\\/:*?\"<>|]";
//            Pattern p = Pattern.compile(regEx);
//            String str = "1\"";
//            Matcher m = p.matcher(str);

            // System.out.println(System.getProperty("user.dir"));
            System.out.println(FileOperations.isValidFileName("a:/12/\\2323\\2*"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
