package com.ucast.taxiscreen.socket.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pj on 2019/3/27.
 */
public class SendToBlackBoxMsgQueuemanager {
    private List<SendToBlackBoxMsgQueue> queues = new ArrayList<>();
    private static SendToBlackBoxMsgQueuemanager queueManager = null;
    private SendToBlackBoxMsgQueuemanager() {
        for (int i = 0; i < 1; i++) {
            SendToBlackBoxMsgQueue one = new SendToBlackBoxMsgQueue();
            queues.add(one);
        }
    }

    public static SendToBlackBoxMsgQueuemanager getInstance(){
        if (queueManager == null){
            synchronized (SendToBlackBoxMsgQueuemanager.class){
                if (queueManager == null){
                    queueManager = new SendToBlackBoxMsgQueuemanager();
                }
            }
        }
        return queueManager;
    }

    public SendToBlackBoxMsgQueue getOneQueue(int position){
        if (position < 0 || position >= 1) {
            return null;
        }
        return  queues.get(position);
    }
}
