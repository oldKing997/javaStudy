package com.wwq.socket;

import com.wwq.socket.client.Client;

public class ClientTest {
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            new Thread() {
                @Override
                public void run() {
                    new Client();
                }
            }.start();
        }
    }
}
