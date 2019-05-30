package com.ucast.taxiscreen.socket.entities;

/**
 * Created by pj on 2019/5/27.
 */
public class MDUSendCmd {
    public static String QueryMeterState = "MTB";
    public static String QueryMeterTime = "MCI";
    public static String SynMeterTime = "MTC";
    public static String QueryMeterBlock = "MLM";
    public static String QueryMeterUnBlock = "MLN";

    //0, Taxi 1, Hired 2, On call 3, Busy
    public static String RooftopSetSys = "RNS";
    //    color + ‘,’  + customer chars
    //    Color:0 green 1 red 2 yellow
    public static String RooftopSetCustom = "RCS";

}
