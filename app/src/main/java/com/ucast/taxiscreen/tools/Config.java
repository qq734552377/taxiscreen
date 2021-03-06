package com.ucast.taxiscreen.tools;


import com.ucast.taxiscreen.exception.CrashHandler;
import com.ucast.taxiscreen.socket.entities.SendToBlackMsg;

/**
 * Created by pj on 2019/1/28.
 */
public class Config {
    public static int PORTBAUDRATE = 115200;
    public static String PORTPATHE = "";
    public static SendToBlackMsg heartMsg = new SendToBlackMsg();

    public static String BLACKBOXCLIENTNAME = "to_black";
    public static String BLACKBOXSERVERIP = "192.168.3.1";
    public static int BLACKBOXSERVERPORT = 8800;
    public static String SERVERCLIENTNAME = "to_server";

    public static boolean isHiddenBottom = false;
    public static String DBNAME = "ucast_shouyin";
    public static int ONELINEMONEYSHOWNUMBER = 20;
    public static String CHARSET = "GB18030";
    public static String LOGPATH = CrashHandler.ALBUM_PATH + "/simpleLog.txt";
    public static String LOGPATHWITHTIME = CrashHandler.ALBUM_PATH + "/simpleTimeLog.txt";
    public static boolean ISDEBUG = true;
}
