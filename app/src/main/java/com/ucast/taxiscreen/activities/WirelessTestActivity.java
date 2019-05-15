package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.ucast.taxiscreen.R;

public class WirelessTestActivity extends AppCompatActivity {
    TextView info_log_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wireless_test);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {
        info_log_tv = findViewById(R.id.info_log_tv);
        info_log_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void refreshLogView(String infoMsg){
        info_log_tv.append(infoMsg);
        int offset = info_log_tv.getLineCount() * info_log_tv.getLineHeight();
        if (offset > info_log_tv.getHeight()){
            info_log_tv.scrollTo(0,offset - info_log_tv.getHeight());
        }
    }
}
