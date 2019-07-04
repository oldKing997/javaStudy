package com.wwq.classrelation;

public class Test {
    int j=5;
    {
        System.out.println(i);
        System.out.println(this.j);
    }
    private static int i=5;

    public static void main(String[] args) {
        new Test();
    }
}
