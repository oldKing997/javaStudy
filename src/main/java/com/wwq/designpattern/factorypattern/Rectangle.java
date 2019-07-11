package com.wwq.designpattern.factorypattern;

import java.util.UUID;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("this is " + getClass().getName());
    }

    public static void main(String[] args) {
        long startTime=System.nanoTime();
        for (int i=0;i<100000;i++){
            String uuidTest= UUID.randomUUID().toString();
            String s="dasdsa";
            s+="dasd";
        }
        long endTime=System.nanoTime();
        System.out.println((endTime-startTime)/1000000L);
    }
}
