package com.losedream.netty.sticking_unpacking_solve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 21:37
 */
class TimeServer {


    public void bind(int port)  throws Exception{

        // 配置服务端的 NIO 线程组 专门用于网络事件的处理 即是实际的 Reactor 的线程组
        // 这里创建两个的原因是 一个用于服务端接收客户端的连接 一个用于进行 SocketChannel 的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            // ServerBootstrap 启动 NIO 服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // 将线程组当作入参传入进去
                    .channel(NioServerSocketChannel.class) // 设置 channel
                    .option(ChannelOption.SO_BACKLOG, 1024) // 配置 TCP 参数
                    .childHandler(new ChildChannelHandler()); // 绑定 I/O 事件的处理类

            // 配置完成之后 调用 bind() 方法 监听端口 之后调用 同步阻塞方法等待绑定操作完成
            // ChannelFuture 主要用于异步操作的通知回调
            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 优雅退出 释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        /**
         * 新增了两个解码器
         * @param socketChannel
         * @throws Exception
         */
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;

        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            new TimeServer().bind(port);

        }
    }



}








