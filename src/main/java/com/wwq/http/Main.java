package com.wwq.http;

import com.alibaba.fastjson.JSONObject;
import com.wwq.file.FileUtils;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void maxLoad(){
       try {
           String title="负载能力测试工具";
           System.out.println("调用"+title);
           Scanner scanner=new Scanner(System.in);
           System.out.println("请输入创建线程数量:");
           int j=scanner.nextInt();
           System.out.println("请输入每个线程循环次数:");
           int k=scanner.nextInt();
           for(int i=0;i<j;i++){
               Test test=new Test(k);
               test.start();
           }
           System.out.println("线程创建完毕");
       }catch (InputMismatchException e){
           System.out.println("请输入正确的数字!");
       }
    }

    public static void main(String[] args) {
        System.out.println("程序开始🔛");
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("请输入指令进行操作:");
            String zl=scanner.next();
            switch (zl.toLowerCase()) {
                case "exit": {
                    System.out.println("程序结束🔚");System.exit(0);break;
                }case "load": {
                    maxLoad();
                    break;
                }case "jframe": {
                    JFrame jFrame=new JFrame();
                    jFrame.setSize(500,500);
                    jFrame.setLocationRelativeTo(null);
                    jFrame.setVisible(true);
                    break;
                }default:{
                    System.out.println("\""+zl+"\"是无效指令\n");
                    break;
                }
            }
        }
    }



}

class Test extends Thread{
    private int threadNum=0;
    private String imgString="";
    private JSONObject jsonObject=new JSONObject();
    public Test(int threadNum){
        String imgUrl="E:\\程序素材库\\Image\\1561615053.8833237.jpg";
        String base64String= FileUtils.fileToBase64(imgUrl);
        String channelId= UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
        jsonObject.put("channel_id",channelId.substring(0,8));
        jsonObject.put("img",base64String);
        this.threadNum=threadNum;
    }

    @Override
    public void run() {
        int i=0;
        long startTime = System.currentTimeMillis();
        for(;i<threadNum;i++){
            Random random=new Random();
            int test=random.nextInt(10);
            long imgTime=System.currentTimeMillis();
            jsonObject.put("time",imgTime);
            String param=jsonObject.toJSONString();
//            String param="";
//            String s=HttpRequestUtils.sendGet("http://192.168.1.38:8080/api2/channels/index", param,"3d1e77b448ff44e6aee8101629e70ce6");
            String s="";
            HttpRequestUtils.sendPost("http://192.168.1.38:5000/aifence/real_img",param,"08def7daab8f452482fc81a81b8a9213");
//            new Thread(){
//                @Override
//                public void run() {
//
//                }
//            }.start();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "毫秒,执行了"+i+"次循环");
        //输出程序运行时间
    }
}
