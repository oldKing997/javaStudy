package com.wwq.socket.server;

import com.alibaba.fastjson.JSONObject;
import com.wwq.file.FileUtils;
import com.wwq.socket.ThreadPool;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Socket服务端用于处理与客户端交互的线程
 *
 * @author wwq
 * @date 2019年6月26日 11:25:49
 * @see Server
 */
public class ServerThread implements Runnable {

    private Socket socket;
    private String clientIp;
    private BufferedReader br;
    private BufferedWriter bw;
    private ThreadPool pool;


    public ServerThread(Socket socket,ThreadPool pool) {
        this.pool=pool;
        this.socket = socket;
        this.clientIp = this.socket.getInetAddress().getHostAddress();
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            String s;
            System.out.println(Server.serverPrintString("有新的连接加入,Ip为:" + this.clientIp + "\t开启线程进行交互"));
//            send("服务器返回给你");
            while (socket.isConnected() && !socket.isClosed() && (s = br.readLine()) != null) {
                JSONObject retJSON = new JSONObject();
                try {
                    String saveImgUrl = "/ssd/img/realImg/";


                    JSONObject data = JSONObject.parseObject(s);
                    final String img = data.getString("img");
                    final String time = data.getString("time");
                    final String channelId = data.getString("channel_id");
                    final String savePath = saveImgUrl + channelId;
                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
//                            FileUtils.base64ToFile(img, savePath, time + ".jpg");
                        }
                    };
                    pool.add(runnable);
//                    FileUtils.base64ToFile(img, savePath, time + ".jpg");
                    retJSON.put("code", 200);
                    retJSON.put("msg","success");
                } catch (Exception e) {
                    retJSON.put("code", 201);
                    retJSON.put("msg",e.getMessage());
                    e.printStackTrace();
                }finally {
//                    send(retJSON.toJSONString());
                    System.gc();
                }
            }
            System.out.println(Server.serverPrintString(clientIp + "已退出。"));
        } catch (SocketException e) {
            System.out.println(Server.serverPrintString(clientIp + "意外退出。"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (Exception e) {
            }finally {
                System.gc();
            }
        }
    }

    private void send(String msg) {
        try {
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopThread() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(Server.serverPrintString("触发JAVA GC。"));
    }
}