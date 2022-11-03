package com.hms.socket.server;

public class RunServer {
    public static void main(String[] args) {
        Thread threadServerA = new ServerA();
        threadServerA.start();
        try {
            if(ServerA.flag){
                threadServerA.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
