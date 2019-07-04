package com.wwq.classrelation;

public class ParentClass {

    private int parentParameter=10;
    {
        System.out.println(this.getClass().getName());
    }
    public ParentClass overrideReadTest(){
        return this;
    }

    private void test(){
        System.out.println("test");
    }
}
