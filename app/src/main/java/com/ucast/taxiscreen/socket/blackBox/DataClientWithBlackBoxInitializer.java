package com.ucast.taxiscreen.socket.blackBox;


import com.ucast.taxiscreen.socket.NioTcpClient;
import com.ucast.taxiscreen.socket.TImeoutIdleClientHandler;
import com.ucast.taxiscreen.socket.server.TcpClientWithServerHandle;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Administrator on 2016/2/4.
 */
public class DataClientWithBlackBoxInitializer extends ChannelInitializer {
    NioTcpClient client;

    public DataClientWithBlackBoxInitializer(NioTcpClient client) {
        this.client = client;
    }

    public void initChannel(Channel channel) {
        TcpClientWithBlackBoxHandle handle = new TcpClientWithBlackBoxHandle();
        channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(NioTcpClient.readTimeout, NioTcpClient.writeTimeout,NioTcpClient.allTimeout, TimeUnit.SECONDS));
        channel.pipeline().addLast("idleTimeoutHandler", new TImeoutIdleClientHandler(this.client));
        channel.pipeline().addLast("handler", handle);
    }

}
