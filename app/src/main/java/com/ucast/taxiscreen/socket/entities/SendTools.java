package com.ucast.taxiscreen.socket.entities;

import com.ucast.taxiscreen.socket.NettyClientMap;
import com.ucast.taxiscreen.socket.NioTcpClient;
import com.ucast.taxiscreen.tools.Config;
import com.ucast.taxiscreen.tools.MyTools;

import org.xutils.common.util.MD5;

/**
 * Created by pj on 2019/5/24.
 */
public class SendTools {
    public static boolean sendToBlackBox(SendToBlackMsg msg){
        NioTcpClient client = NettyClientMap.GetChannel(Config.BLACKBOXCLIENTNAME);
        boolean result = false;
        if (client == null)
            return result;
        if (client.clietnStatus()){
            result = client.send(msg.getSendMsg());
        }
        MyTools.writeSimpleLogWithTime("发送一次消息   " + msg.getSendCmd());
        return result;
    }
}
