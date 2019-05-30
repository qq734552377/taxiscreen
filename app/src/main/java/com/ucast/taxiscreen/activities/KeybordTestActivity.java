package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeybordTestActivity extends BasePermisionActivity {
    @BindView(R.id.k1_tv)
    TextView k1_tv;
    @BindView(R.id.k2_tv)
    TextView k2_tv;
    @BindView(R.id.k3_tv)
    TextView k3_tv;
    @BindView(R.id.k4_tv)
    TextView k4_tv;
    @BindView(R.id.k5_tv)
    TextView k5_tv;
    @BindView(R.id.k6_tv)
    TextView k6_tv;

    @BindView(R.id.tested_ok_bt)
    Button test_ok_bt;

    boolean isStartTest = false;
    int[] allState = new int[]{0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord_test);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start_test_bt,R.id.cancle_test_bt,R.id.tested_ok_bt})
    public void onBtClicked(View v){
        switch (v.getId()){
            case R.id.start_test_bt:
                isStartTest = true;
                initKeysState();
                break;
            case R.id.cancle_test_bt:
                isStartTest = false;
                initKeysState();
                break;
            case R.id.tested_ok_bt:
                int stateSum = 0;
                for (int i = 0; i < allState.length; i++) {
                    stateSum += allState[i];
                }
                if (stateSum != 6){
                    return;
                }
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isStartTest){
            if (keyCode == KeyEvent.KEYCODE_A){
                k1_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[0] = 1;
            }
            if (keyCode == KeyEvent.KEYCODE_B){
                k2_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[1] = 1;
            }
            if (keyCode == KeyEvent.KEYCODE_C){
                k3_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[2] = 1;
            }
            if (keyCode == KeyEvent.KEYCODE_D){
                k4_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[3] = 1;
            }
            if (keyCode == KeyEvent.KEYCODE_E){
                k5_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[4] = 1;
            }
            if (keyCode == KeyEvent.KEYCODE_F){
                k6_tv.setBackgroundColor(getResources().getColor(R.color.orange));
                allState[5] = 1;
            }
            int stateSum = 0;
            for (int i = 0; i < allState.length; i++) {
                stateSum += allState[i];
            }
            if (stateSum == 6){
                test_ok_bt.setBackgroundColor(getResources().getColor(R.color.orange));
            }
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    public void initKeysState(){
        for (int i = 0; i < allState.length; i++) {
            allState[i] = 0;
        }
        test_ok_bt.setBackgroundColor(getResources().getColor(R.color.baseBtColor));
        k1_tv.setBackgroundColor(getResources().getColor(R.color.green));
        k2_tv.setBackgroundColor(getResources().getColor(R.color.green));
        k3_tv.setBackgroundColor(getResources().getColor(R.color.green));
        k4_tv.setBackgroundColor(getResources().getColor(R.color.green));
        k5_tv.setBackgroundColor(getResources().getColor(R.color.green));
        k6_tv.setBackgroundColor(getResources().getColor(R.color.green));
    }
}
