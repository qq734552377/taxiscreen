package com.ucast.taxiscreen.socket.entities;

import com.ucast.taxiscreen.tools.MyTools;

/**
 * Created by pj on 2019/5/24.
 */
public class SendToBlackMsg {
    boolean isSending = false;
    String sendCmd;
    String sendLength;
    String sendContent;


    public SendToBlackMsg() {
        setSendCmd(MDUSendCmd.QueryMeterTime);
        setSendLength("000");
        setSendContent("");
    }

    public SendToBlackMsg(String sengCmd, String sendLength, String sendContent) {
        this.sendCmd = sengCmd;
        this.sendLength = sendLength;
        this.sendContent = sendContent;
    }

    public boolean isSending() {
        return isSending;
    }

    public void setSending(boolean sending) {
        isSending = sending;
    }

    public String getSendCmd() {
        return sendCmd;
    }

    public void setSendCmd(String sendCmd) {
        this.sendCmd = sendCmd;
    }

    public String getSendLength() {
        return sendLength;
    }

    public void setSendLength(String sendLength) {
        this.sendLength = sendLength;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public byte[] getSendMsg(){
        String msg = this.sendCmd + this.sendLength + MyTools.encode(sendContent.getBytes());
        byte[] msgBytes = msg.getBytes();
        byte[] sendMsgs = new byte[msgBytes.length + 2];
        sendMsgs[0] = 0x02;
        System.arraycopy(msgBytes,0,sendMsgs,1,msgBytes.length);
        sendMsgs[sendMsgs.length - 1] = 0x03;
        return sendMsgs;
    }
}
