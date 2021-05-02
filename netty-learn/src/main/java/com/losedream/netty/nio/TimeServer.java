package com.losedream.netty.nio;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 20:00
 */
class TimeServer {


    public static void main(String[] args) {

        int port = 8080;

        if (args != null && args.length > 0 ) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
