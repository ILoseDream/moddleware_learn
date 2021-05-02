package com.losedream.netty.codec;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/18 9:52
 */
public class UserInfo implements Serializable {

    private int userId;

    private String userName;

    public UserInfo buildUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public UserInfo buildUserName(String userName) {
        this.userName = userName;
        return this;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] codec() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codec(ByteBuffer buffer) {
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }



}
