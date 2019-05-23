package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WirelessTestActivity extends BasePermisionActivity {
    @BindView(R.id.info_log_tv)
    TextView info_log_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wireless_test);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
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
