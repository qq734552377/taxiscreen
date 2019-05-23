package com.ucast.taxiscreen.exception;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import org.apache.log4j.Logger;
import org.xutils.x;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2016/6/12.
 */
public class ExceptionApplication extends Application {

    public static Context context;

    public static Typeface simsunTypeface;


    public void onCreate() {
        super.onCreate();
//        x.Ext.init(this);
        simsunTypeface = Typeface.createFromAsset(getAssets(),"simsun.ttc");
        context=this;

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//        LogUtil.configLog();
//        gLogger= Logger.getLogger(ExceptionApplication.class);
//        //输出MyApplication的信息
//        gLogger.info("Log4j Is Ready and ShouYin Application Was Started Successfully! ");


    }
    public static Context getInstance(){
        return context;
    }

}
