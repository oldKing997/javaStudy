package com.wwq.socket.client;

import com.alibaba.fastjson.JSONObject;
import com.wwq.file.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;

/**
 * Socket客户端
 * @author wwq
 * @date 2019年6月26日 11:25:49
 */
public class Client {
    BufferedWriter bw;
    ClientReceiveThread clientReceiveThread;
    Socket socket;

    public Client() {
        System.out.println("开始发送图片");
        try {
            String imgUrl="E:\\程序素材库\\Image\\1561615053.8833237.jpg";
            String base64String= FileUtils.fileToBase64(imgUrl);
            String channelId=UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
            socket = new Socket("192.168.1.5", 8089);
            bw= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientReceiveThread = new ClientReceiveThread(socket);
            new Thread(clientReceiveThread).start();
            int i = 0;
            long time1=System.currentTimeMillis();
            while (!socket.isClosed()) {
                if(i==500){
                    break;
                }
//                Thread.sleep(1);
                JSONObject jsonObject = new JSONObject();
                i++;

                jsonObject.put("channel_id",channelId);
                jsonObject.put("img",base64String);
                jsonObject.put("time",System.currentTimeMillis());
                bw.write(jsonObject + "\n");
                bw.flush();
            }
            long time2=System.currentTimeMillis();
            System.out.println((time2-time1)+"毫秒");
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                bw.close();
                clientReceiveThread.stopThread();
                if(!socket.isClosed()){
                    socket.close();
                }
            }catch (Exception e){

            }
        }
    }

}
