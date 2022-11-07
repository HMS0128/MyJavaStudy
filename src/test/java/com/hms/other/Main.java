package com.hms.other;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("nmae");//实例化窗体对象
        JTextArea area = new JTextArea(3, 20);//构造一个文本域
        area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
        JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(pane);
        frame.setSize(500, 800);
        frame.setLocation(300, 200);
        frame.setVisible(true);

    }
}