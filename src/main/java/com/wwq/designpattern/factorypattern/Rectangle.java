package com.wwq.designpattern.factorypattern;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("this is " + getClass().getName());
    }
}
