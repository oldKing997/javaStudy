package com.wwq.designpattern.factorypattern;

public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("this is " + getClass().getName());
    }
}
