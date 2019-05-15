package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ucast.taxiscreen.R;

public class KeybordTestActivity extends AppCompatActivity {
    TextView k1_tv;
    TextView k2_tv;
    TextView k3_tv;
    TextView k4_tv;
    TextView k5_tv;
    TextView k6_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord_test);
        getSupportActionBar().hide();
    }
}
