package com.ucast.taxiscreen.socket.blackBox;



import com.ucast.taxiscreen.socket.entities.MCUSendCmd;
import com.ucast.taxiscreen.socket.entities.SendToBlackMsg;
import com.ucast.taxiscreen.socket.queue.SendToBlackBoxMsgQueuemanager;
import com.ucast.taxiscreen.tools.MyTools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Administrator on 2016/2/4.
 */
public class TcpClientWithBlackBoxHandle extends ChannelInboundHandlerAdapter {
    //用于存放打印返回信息
    private byte[] allBuffer;
    //用于监控fanBuffer的初始偏移量
    private int offSet = 0;
    //用于反应当前应截取的位置
    private int cutPosition = 0;
    //设置存放消息数组的设定长度
    private int allBufferLen = 1024 * 8;
    public TcpClientWithBlackBoxHandle() {
        allBuffer = new byte[allBufferLen];
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接来了:"+ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buff = (ByteBuf) msg;
            int len = buff.readableBytes();
            byte[] buffer = new byte[len];
            buff.readBytes(buffer);
            // 处理server过来的数据
//            analyzeDataWithString(buffer);
            analyzeDataWithByte(buffer);
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            MyTools.writeSimpleLogWithTime("channelRead异常停止--》" + e.toString());
            ctx.close();
        }
    }

    private void analyzeDataWithByte(byte[] buffer) {
        //添加串口数据
        jointBuffer(buffer);
//        MyTools.writeSimpleLogWithTime(MyTools.printHexString(buffer));
        while (offSet > 0) {
            int index = getIndexByByte((byte) 0x06);
            if (index == 0){
                MyTools.writeSimpleLogWithTime("移掉一条消息");
                SendToBlackBoxMsgQueuemanager.getInstance().getOneQueue(0).removeItem();
                cutPosition = index + 1;
                cutBuffer();
                continue;
            }
            index = getIndexByByte((byte) 0x15);
            if (index == 0){
                MyTools.writeSimpleLogWithTime("将一条消息变为重新发送状态");
                SendToBlackBoxMsgQueuemanager.getInstance().getOneQueue(0).changeSendStateItem();
                cutPosition = index + 1;
                cutBuffer();
                continue;
            }

            int startIndex = getIndexByByte((byte) 0x02);
            if (startIndex <= -1) {
                break;
            }
            int endIndex = getIndexByByte((byte) 0x03);
            if (endIndex <= -1) {
                break;
            }
            if (endIndex < startIndex) {
                cutPosition = endIndex + 1;
                cutBuffer();
                continue;
            }
            byte[] printBuffer = getPrintbyte(startIndex , endIndex);
            handleProtocol(printBuffer);
            cutBuffer();
        }
    }

    private void handleProtocol(byte[] printBuffer) {
        //TODO 处理协议
        String str = new String (printBuffer);
        String cmd = str.substring(0,3);
        String dataLen = str.substring(3,6);
        String data = "";
        if (Integer.parseInt(dataLen) != 0){
            data = MyTools.decodeReturnStr(str.substring(6,str.length()));
        }
        SendToBlackMsg msg = new SendToBlackMsg();
        msg.setSendCmd(cmd);
        msg.setSendLength(dataLen);
        msg.setSendContent(data);
        switch (msg.getSendCmd()){
            case "MSB":

                break;


        }

    }

    private int getIndexByByte( byte b) {
        for (int i = 0; i < offSet; i++) {
            if (allBuffer[i] == b) {
                return i;
            }
        }
        return -1;
    }

    private void jointBuffer(byte[] buffer) {
        if (offSet + buffer.length  > allBuffer.length) {
            // 扩容 为原来的两倍
            byte[] temp = new byte[allBuffer.length];
            System.arraycopy(allBuffer,0,temp,0, allBuffer.length);
            allBuffer = new byte[allBuffer.length * 2];
            System.arraycopy(temp,0, allBuffer,0,temp.length);
        }
        System.arraycopy(buffer,0, allBuffer,offSet,buffer.length);
        offSet = offSet + buffer.length;
    }



    //返回一个byte对象 用于发送消息 该数组不会包含 头和尾 即0x02和0x03
    private byte[] getPrintbyte(int start, int end) {
        byte[] printByte = new byte[end - start - 1];
        int position = start + 1;
        System.arraycopy(allBuffer,position,printByte,0,printByte.length);
        cutPosition = end + 1;
        return printByte;
    }

    //用于重新截取fanhuiBuffer的数据
    private void cutBuffer() {
        System.arraycopy(allBuffer,cutPosition, allBuffer,0,offSet - cutPosition);
        offSet = offSet - cutPosition;
        if(allBuffer.length > allBufferLen && offSet < allBufferLen /2){
            byte[] temp = new byte[offSet];
            System.arraycopy(allBuffer,0,temp,0,offSet);
            allBuffer = new byte[allBufferLen];
            System.arraycopy(temp,0, allBuffer,0,offSet);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent event = (IdleStateEvent) evt;
        if(event.state() == IdleState.READER_IDLE) {
            ctx.close();
        }
    }
}
