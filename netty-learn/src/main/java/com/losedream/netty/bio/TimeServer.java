package com.losedream.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 16:34
 */
class TimeServer {

    public static void main(String[] args) {

        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);

            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                System.out.println("the time server close");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }

    }

}
