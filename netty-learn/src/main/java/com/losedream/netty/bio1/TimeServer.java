package com.losedream.netty.bio1;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 18:27
 */
class TimeServer {

    public static void main(String[] args) throws Exception{

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

            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);

            while (true) {
                socket = serverSocket.accept();
                pool.execute(new TimeServerHandler(socket));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (serverSocket != null) {
                System.out.println("the time server close");
                serverSocket.close();
                serverSocket = null;
            }

        }

    }

}
