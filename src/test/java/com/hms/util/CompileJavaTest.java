package com.hms.util;

import org.junit.Test;

public class CompileJavaTest {

    @Test
    public void compileJavaFile() {
        CompileJava.compileJavaFile("UTF-8", "F:\\HMS\\Desktop\\hello", "F:\\HMS\\Desktop\\hello\\Hello.java");
    }
}