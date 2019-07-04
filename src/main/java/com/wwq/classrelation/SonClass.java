package com.wwq.classrelation;

public class SonClass extends ParentClass {

    /**
     * 重写
     */
    @Override
    public SonClass overrideReadTest() {
        return this;
    }


    private String test(){
        return "";
    }

    /**
     * 重载
     */
    public int overrideReadTest(int i) {
        return 2;
    }
}
