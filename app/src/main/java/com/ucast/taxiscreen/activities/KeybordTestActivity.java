package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord_test);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }
}
