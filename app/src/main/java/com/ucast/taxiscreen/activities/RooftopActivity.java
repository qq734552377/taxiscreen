package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;

public class RooftopActivity extends BasePermisionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooftop);
        getSupportActionBar().hide();
    }
}
