package com.wwq.designpattern.factorypattern;

public class Test {
    public static void main(String[] args) {
        FactoryPattern factoryPattern=new FactoryPattern();

        //获取 Circle 的对象，并调用它的 draw 方法
        Shape shape1 = factoryPattern.getShape("CIRCLE");

        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取 Rectangle 的对象，并调用它的 draw 方法
        Shape shape2 = factoryPattern.getShape("RECTANGLE");

        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取 Square 的对象，并调用它的 draw 方法
        Shape shape3 = factoryPattern.getShape("SQUARE");

        //调用 Square 的 draw 方法
        shape3.draw();
    }
}
