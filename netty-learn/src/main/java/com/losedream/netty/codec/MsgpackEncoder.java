package com.losedream.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 *
 * 负责将 {@link Object} 类型的 POJO 对象 编码为 byte 数组对象 然后写入到{@link ByteBuf} 中去
 *
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/18 10:18
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(o);
        byteBuf.writeBytes(write);
    }

}
