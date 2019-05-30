package com.ucast.taxiscreen;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ucast.taxiscreen.socket.NioTcpClient;
import com.ucast.taxiscreen.socket.blackBox.DataClientWithBlackBoxInitializer;
import com.ucast.taxiscreen.socket.client_connect.BlackBoxClientConnect;
import com.ucast.taxiscreen.tools.Config;
import com.ucast.taxiscreen.tools.MyTools;

public class UpdateService extends Service {
    public static boolean isStart = false;
    NioTcpClient sendToBlackClient;
    Thread sendToBlackThread;
    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Notification notification = new Notification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        startForeground(0, notification);
        super.onCreate();
        MyTools.writeSimpleLogWithTime("服务开启了");
//        EventBus.getDefault().register(this);
//        if (sendToBlackClient == null) {
//            sendToBlackClient = new NioTcpClient(Config.BLACKBOXSERVERIP, Config.BLACKBOXSERVERPORT, Config.BLACKBOXCLIENTNAME);
//            DataClientWithBlackBoxInitializer handle = new DataClientWithBlackBoxInitializer();
//            sendToBlackClient.setHandle(handle);
//            if (sendToBlackThread == null){
//                sendToBlackThread = new Thread(sendToBlackClient);
//                sendToBlackThread.start();
//            }
//        }
        BlackBoxClientConnect.startTimer();

    }

    /**
     * 当服务被杀死时重启服务
     * */
    public void onDestroy() {
        stopForeground(true);
        Intent localIntent = new Intent();
        localIntent.setClass(this, UpdateService.class);
//        EventBus.getDefault().unregister(this);
        isStart= false;
        this.startService(localIntent);    //销毁时重新启动Service
    }
}
