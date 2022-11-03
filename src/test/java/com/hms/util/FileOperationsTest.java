package com.hms.util;

import org.junit.Test;

public class FileOperationsTest {
    FileOperations fo = new FileOperations();

    @Test
    public void copy() {
        fo.copy("src","hello");
    }

    @Test
    public void delete() {
        fo.delete("temp");
    }

    @Test
    public void deleteFile() {
        fo.deleteFile("hello/src/main/resources/conf/logback.xml");
    }

    @Test
    public void cut() {
        fo.cut("hello","temp");
    }

    @Test
    public void searchByFileName() {
    }

    @Test
    public void appendFile() {
    }

    @Test
    public void findFileContent() {
    }

    @Test
    public void getAllFileInDirectory() {
    }

    @Test
    public void testGetAllFileInDirectory() {
    }

    @Test
    public void findContentByDirectory() {
    }

    @Test
    public void testFindContentByDirectory() {
    }

    @Test
    public void replaceSucceeded() {
    }
}