package com.losedream.netty.codec;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/18 9:57
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {

        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUserName("losedream");
        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(userInfo);
        oos.flush();
        oos.close();

        byte[] bytes = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + bytes.length);
        bos.close();

        System.out.println("====================================================");

        System.out.println("The byte array serializable length is : " + userInfo.codec().length);

    }

}
