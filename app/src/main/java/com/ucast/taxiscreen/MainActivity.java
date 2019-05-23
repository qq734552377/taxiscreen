package com.ucast.taxiscreen;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
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
import com.ucast.taxiscreen.tools.MyDialog;

import org.apache.log4j.Logger;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BasePermisionActivity {
    public static CrashHandler crashHandler = null;
    public static Logger gLogger = null;
    Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

      disposable = Observable.just("tom","jerry","sam")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("doOnNext", "accept: " + s );
                    }
                }).map(new Function<String, String>() {

                    @Override
                    public String apply(String s) throws Exception {
                        Thread.sleep(8000);
                        return s + "223";
                    }
                })
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Consumer<String>() {
                   @Override
                   public void accept(String s) throws Exception {
                       MyDialog.showToast(MainActivity.this,s);
                   }
               });


    }
    @OnClick(R.id.meter_bt)
    public void gotoMeterAct(){
        startOneActivity(MeterTestActivity.class);
    }
    @OnClick(R.id.erp_bt)
    public void gotoErpAct(){
        startOneActivity(ErpTestActivity.class);
    }
    @OnClick(R.id.gps_bt)
    public void gotoGpsIOAct(){
        startOneActivity(GpsIOTestActivity.class);
    }
    @OnClick(R.id.wireless_bt)
    public void gotoWirelessAct(){
        startOneActivity(WirelessTestActivity.class);
    }
    @OnClick(R.id.keypad_bt)
    public void gotoKeybordAct(){
        startOneActivity(KeybordTestActivity.class);
    }
    @OnClick(R.id.panic_bt)
    public void gotoPanicAct(){
        startOneActivity(PanicTestActivity.class);
    }
    @OnClick(R.id.rooftop_bt)
    public void gotoRooftopdAct(){
        startOneActivity(RooftopActivity.class);
    }

    public void startOneActivity(Class<?> cls){
        Intent i = new Intent(MainActivity.this, cls);
        startActivity(i);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
