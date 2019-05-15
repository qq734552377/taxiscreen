package com.ucast.taxiscreen;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ucast.taxiscreen.activities.ErpTestActivity;
import com.ucast.taxiscreen.activities.GpsIOTestActivity;
import com.ucast.taxiscreen.activities.KeybordTestActivity;
import com.ucast.taxiscreen.activities.MeterTestActivity;
import com.ucast.taxiscreen.activities.PanicTestActivity;
import com.ucast.taxiscreen.activities.RooftopActivity;
import com.ucast.taxiscreen.activities.WirelessTestActivity;
import com.ucast.taxiscreen.exception.CrashHandler;
import com.ucast.taxiscreen.exception.ExceptionApplication;
import com.ucast.taxiscreen.exception.LogUtil;

import org.apache.log4j.Logger;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    public static CrashHandler crashHandler = null;
    public static Logger gLogger = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "需要写sd卡", 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "需要读取sd卡", 1, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.INTERNET)) {
            EasyPermissions.requestPermissions(this, "需要访问网络", 1, Manifest.permission.INTERNET);
        }
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)) {
            EasyPermissions.requestPermissions(this, "需要访问网络", 1, Manifest.permission.RECEIVE_BOOT_COMPLETED);
        }

        initViews();
    }

    private void initViews() {
        findViewById(R.id.meter_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MeterTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.erp_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ErpTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.gps_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GpsIOTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.wireless_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WirelessTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.keypad_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KeybordTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.panic_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PanicTestActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.rooftop_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RooftopActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    String name = "May 1st \n18:28";
    @Override
    protected void onResume() {
        if(EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            if (crashHandler == null) {
                crashHandler = CrashHandler.getInstance();
                crashHandler.init(ExceptionApplication.getInstance());
            }
            if (gLogger == null){
                LogUtil.configLog();
                gLogger= Logger.getLogger(ExceptionApplication.class);
                //输出MyApplication的信息
                gLogger.info("Log4j Is Ready and TaxiScreen Application Was Started Successfully! ");
            }
        }
        super.onResume();
    }

}
