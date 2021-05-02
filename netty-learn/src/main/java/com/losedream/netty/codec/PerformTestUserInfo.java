package com.losedream.netty.codec;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/18 10:04
 */
public class PerformTestUserInfo {

    public static void main(String[] args) throws IOException {

        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUserName("losedream");
        int loop = 1000000;
        ByteOutputStream bos = null;
        ObjectOutputStream oos = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(userInfo);
            oos.flush();
            oos.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is : " + (end - start) + " ms");

        System.out.println("====================================================");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] codec = userInfo.codec(buffer);
        }
        end = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is : " + (end - start) + " ms");
    }

}







