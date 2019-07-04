package com.wwq.socket.server;

import com.wwq.date.DateUtils;
import com.wwq.socket.ThreadPool;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Socket服务端
 * @author wwq
 * @date 2019年6月26日 11:25:49
 */
public class Server {
    public boolean ServerFlag;
    private ServerSocket serverSocket;
    private int port;
    private List<Socket> clientSockets;
    private List<ServerThread> serverThreads;
    private ThreadPool savePool;
    private ThreadPool serverPool;
//    用于初始化静态对象

    static {
        System.out.println(serverPrintString("静态块初始化"));
    }

    //    初始化块，用于整合各个构造器中重复的代码.
    {
        port = 8888;
        clientSockets = new ArrayList();
        serverThreads=new ArrayList();
        ServerFlag = true;
        System.out.println(serverPrintString("初始化"));
    }

    void initPool(){
        savePool=new ThreadPool(32);
        serverPool=new ThreadPool(32);
    }
    /**
     * 使用默认端口号进行直接启动
     */
    public Server() {
        System.out.println(serverPrintString("构造器初始化"));
        startServer();
    }

    /**
     * 设置端口号并启动
     *
     * @param port 端口号
     */
    public Server(int port) {
        System.out.println(serverPrintString("构造器初始化"));
        this.port = port;
        startServer();
    }

    /**
     * 启动服务器Socket
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println(serverPrintString("服务器已启动，端口号为:" + this.port));
            initPool();
            while (ServerFlag) {
                Socket socket = serverSocket.accept();
//                开始监听连接，有连接时将会创建一个Socket
                clientSockets.add(socket);
//                将客户端Socket放到一个List中
                ServerThread serverThread=new ServerThread(socket,savePool);
                serverPool.add(serverThread);
//                Thread thread = new Thread(serverThread, "socket" + socket.getInetAddress().toString());
////                为连接建立一个单独的线程进行处理
//                thread.start();
            }
            System.out.println(serverPrintString("服务器已退出。"));
        } catch (BindException e){
            System.out.println(serverPrintString("端口号被占用"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(serverSocket!=null&&!serverSocket.isClosed()){
                    serverSocket.close();
                }
                System.gc();
            }catch (Exception e) {
               e.printStackTrace();
            }
        }
    }

    /**
     * 拼接服务器输出的字符串
     * 格式:(时间：2019-01-01 20:00:00\n消息:服务打印数据\n)
     */
    public static String serverPrintString(String msg) {
        return "时间:" + DateUtils.getNowTimeString() + "\n消息:" + msg + "\n";
    }

}

