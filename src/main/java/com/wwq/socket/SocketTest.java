package com.wwq.socket;

import com.wwq.socket.client.Client;
import com.wwq.socket.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) throws Exception{
        new Thread(){
            @Override
            public void run() {
                new Server(8089);
            }
        }.start();
    }
}