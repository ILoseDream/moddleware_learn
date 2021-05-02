/**
 * TCP 粘包/拆包问题 解决方案
 *
 * {@link io.netty.handler.codec.LineBasedFrameDecoder}
 * {@link io.netty.handler.codec.string.StringDecoder} 解决 TCP 粘包问题
 *
 * {@link io.netty.handler.codec.LineBasedFrameDecoder} 的工作原理是依次遍布{@link io.netty.buffer.ByteBuf} 中的可读字节
 * 判断是否有"\n" 或者"\r\n" 如果有 就以此位置作为结束位置 从可读索引到结束位置区间的字节就组成了一行
 * {@link io.netty.handler.codec.string.StringDecoder} 的功能非常简单 就是将接收到等待对象转换为 字符串 然后继续调用后面的handler
 * LineBasedFrameDecoder + StringDecoder 就是 按行切换的文本解码器 
 *
 *
 *
 */
package com.losedream.netty.sticking_unpacking_solve;