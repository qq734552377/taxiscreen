package com.ucast.taxiscreen.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pj on 2019/3/25.
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context,String msg){
        if (toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
