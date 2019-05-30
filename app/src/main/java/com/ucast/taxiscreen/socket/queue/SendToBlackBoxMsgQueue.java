package com.ucast.taxiscreen.socket.queue;

import com.ucast.taxiscreen.socket.entities.SendToBlackMsg;
import com.ucast.taxiscreen.socket.entities.SendTools;

import java.util.ArrayList;

/**
 * Created by pj on 2019/3/27.
 */
public class SendToBlackBoxMsgQueue {
    public static final byte[] cut_paper_byte = {0x1D,0x56};
    public ArrayList<SendToBlackMsg> queue = new ArrayList<>();
    private StringBuilder sb = new StringBuilder();

    public SendToBlackBoxMsgQueue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                continueRun();
            }
        }).start();

    }
    private void continueRun(){
        while (true){
            myRun();
        }
    }

    private void myRun() {
        synchronized (this) {
            try {
                SendToBlackMsg one = getItem();
                if (one != null) {
                    if (!one.isSending()) {
                        //发送消息
                        if (SendTools.sendToBlackBox(one))
                            one.setSending(true);
                    }
                } else {
                    Thread.sleep(5);
                }
            } catch (Exception e) {

            }
        }
    }


    public void addItem(SendToBlackMsg data){
        synchronized (this){
            putData(data);
        }
    }

    public SendToBlackMsg getItemByOut(){
        synchronized (this) {
            return getItem();
        }
    }
    private SendToBlackMsg getItem(){
        if (queue.size() == 0)
            return null;
        SendToBlackMsg one = queue.get(0);
        return one;
    }

    public void putData(SendToBlackMsg msg) {
        queue.add(msg);
    }

    public void removeItem(){
        synchronized (this){
            if (queue.size() == 0)
                return;
            queue.remove(0);
        }
    }
    public void changeSendStateItem(){
        synchronized (this){
            if (queue.size() == 0)
                return;
            SendToBlackMsg one = queue.get(0);
            one.setSending(false);
        }
    }

}
