package com.ucast.taxiscreen.socket;


import com.ucast.taxiscreen.tools.MyTools;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by Administrator on 2016/2/4.
 */
public class NioTcpClient implements Runnable {

    public String ip;

    public int port;

    public String name;
    private EventLoopGroup group;

    public ChannelFuture f;
    private boolean mDispose;

    public int WaitChannel;

    public ChannelInitializer handle;

    public static long reConnectTime = 30;
    public static long readTimeout = 30;
    public static long writeTimeout = 30;
    public static long allTimeout = 30;

    public NioTcpClient() {

    }

    public NioTcpClient(String ip, int port,String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        group = new NioEventLoopGroup();
    }

    public void setHandle(ChannelInitializer handle){
        this.handle = handle;
    }

    @Override
    public void run() {
        connect();
    }

    public void connect() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
//            bootstrap.option(ChannelOption.TCP_NODELAY, true);
//            bootstrap.option(ChannelOption.SO_BACKLOG, 124);
            bootstrap.option(ChannelOption.SO_RCVBUF, 1024*1024*2);//配置接收的缓冲区大小
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);// 保持长连接
            if (this.handle != null)
                bootstrap.handler(this.handle);
            f = bootstrap.connect(this.ip, this.port).sync();
            //等待链接关闭
            f.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ChannelFuture futureListener = (ChannelFuture) future;
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        MyTools.writeSimpleLogWithTime(name + "-->  Failed to connect to server:" + ip + ":" + port + ", try connect again after " + NioTcpClient.reConnectTime + "s");
                        futureListener.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                connect();
                            }
                        }, reConnectTime, TimeUnit.SECONDS);
                    }
                }
            });
            try {
                MyTools.writeSimpleLogWithTime(this.name + " 连接成功");
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                MyTools.writeSimpleLogWithTime(this.name + " sync 抛出" + e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyTools.writeSimpleLogWithTime(this.name + " connect 抛出" + e.toString());
            close();
        } finally {

        }
    }

    public void close() {
        synchronized (this) {
            if (mDispose)
                return;
            if (group != null)
                group.shutdownGracefully();
            WaitChannel = 2;
        }
    }

    public void dispose() {
        synchronized (this) {
            if (mDispose)
                return;
            mDispose = true;
            if (group == null)
                return;
            group.shutdownGracefully();
        }
    }

    public boolean send(byte[] Data) {
        try {
            if (f == null)
                return false;
            if (!f.isSuccess())
                return false;
            Channel channel = f.channel();
            if (channel == null)
                return false;
            ByteBuf resp = Unpooled.copiedBuffer(Data);
            channel.writeAndFlush(resp);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean clietnStatus() {
        try {
            if (f == null)
                return false;
            if (!f.isSuccess())
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
