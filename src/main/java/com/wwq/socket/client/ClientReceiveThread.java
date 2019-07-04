package com.wwq.socket.client;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Client客户端用于接收的线程.
 * @author wwq
 * @date 2019年6月26日 14:36:27
 * @see Client
 */
public class ClientReceiveThread implements Runnable {
    /**
     * 客户端socket
     */
    private Socket socket;


    /**
     * 用于接收
     */
    private BufferedReader br;

    /**
     * 初始化Socket
     */
    public ClientReceiveThread(Socket socket) {
        this.socket = socket;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String s;
        try {
            int i=1;
            while (socket.isConnected()&&!socket.isClosed() && (s = br.readLine()) != null) {
                i++;
                if(i>198){
                    System.out.println(i);
                }
                JSONObject data = JSONObject.parseObject(s);
                int code=data.getInteger("code");
                String msg=data.getString("msg");
                if(code==201){
                    System.out.println("出现异常");
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("接受线程已关闭");
        }
    }


    /**
     * 结束该线程的死循环来退出线程
     */
    public void stopThread() {
        try {
            this.br.close();
            if(!socket.isClosed()){
                this.socket.close();
            }
        } catch (IOException e) {

        }
    }
}
