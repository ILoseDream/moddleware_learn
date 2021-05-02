package com.losedream.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 15:59
 */
public class BrokerServer implements Runnable{


    public static int service_port = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream printStream = new PrintStream(socket.getOutputStream())
        ) {

            while (true) {

                String s = bufferedReader.readLine();
                if (s == null) {
                    continue;
                }
                System.out.println("接收到原始数据: " + s);

                if (s.equals("consumer")) {
                    String consumer = Broker.consumer();
                    printStream.println(consumer);
                    printStream.flush();
                } else {
                    Broker.produce(s);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(service_port);

        while (true) {
            BrokerServer brokerServer = new BrokerServer(serverSocket.accept());
            new Thread(brokerServer).start();
        }

    }

}








