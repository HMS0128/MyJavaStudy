package com.hms.other;

import java.util.Scanner;

public class OtherTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                String str1 = scanner.next();
                System.out.println("输入的数据为：" + str1);
            }
        }
      //  scanner.close();
    }
}
