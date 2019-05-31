package com.ucast.taxiscreen.socket.client_connect;

import com.ucast.taxiscreen.UpdateService;
import com.ucast.taxiscreen.mytime.MyTimeTask;
import com.ucast.taxiscreen.mytime.MyTimer;
import com.ucast.taxiscreen.socket.NettyClientMap;
import com.ucast.taxiscreen.socket.NioTcpClient;
import com.ucast.taxiscreen.socket.blackBox.DataClientWithBlackBoxInitializer;
import com.ucast.taxiscreen.socket.entities.SendTools;
import com.ucast.taxiscreen.tools.Config;
import com.ucast.taxiscreen.tools.MyTools;

/**
 * Created by pj on 2019/5/27.
 */
public class BlackBoxClientConnect {
    private static MyTimer timer;
    private static MyTimeTask timeTask;
    private static long heartTime;
    public static Object lock = new Object();

    public static void startTimer(){
        timeTask = new MyTimeTask(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    NioTcpClient client = NettyClientMap.GetChannel(Config.BLACKBOXCLIENTNAME);
                    if (client == null) {
                        MyTools.writeSimpleLogWithTime("clien 为空创建一次");
                        client = new NioTcpClient(Config.BLACKBOXSERVERIP, Config.BLACKBOXSERVERPORT, Config.BLACKBOXCLIENTNAME);
                        DataClientWithBlackBoxInitializer handle = new DataClientWithBlackBoxInitializer();
                        NettyClientMap.Add(client);
                        client.setHandle(handle);
                        new Thread(client).start();
                        heartbeatTimeUpdate();
                        return;
                    }
                    if (!client.clietnStatus()) {
                        NettyClientMap.Remove(Config.BLACKBOXCLIENTNAME);
                        return;
                    }
                    long second = (System.currentTimeMillis() - heartTime) / 1000;
                    if (second >= 40) {
                        client.dispose();
                    } else {
                        boolean result = sendHeartBeat();
                        if (result) {
                            return;
                        }
                    }
                    NettyClientMap.Remove(Config.BLACKBOXCLIENTNAME);

                }
            }
        });
        timer = new MyTimer(timeTask,2000L,30000L);

        timer.initMyTimer().startMyTimer();
    }

    public static boolean sendHeartBeat(){
        return SendTools.sendToBlackBox(Config.heartMsg);
    }

    public static void heartbeatSafeTimeUpdate(){
        synchronized (lock){
            heartbeatTimeUpdate();
        }
    }

    public static void heartbeatTimeUpdate() {
        heartTime = System.currentTimeMillis();
    }


}
