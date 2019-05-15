package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.adapters.TripAdapter;
import com.ucast.taxiscreen.enties.TripShowObj;

import java.util.ArrayList;

public class MeterTestActivity extends AppCompatActivity {
    ArrayList<TripShowObj> datas = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_test);
        getSupportActionBar().hide();
        for (int i = 0; i < 3; i++) {
            TripShowObj one = new TripShowObj();
            one.setTripId("0" + (i+1) +"");
            one.setStartTime("May " + (i+1)+ "\n18:28");
            one.setEndTime("May " + (i+1)+ "\n19:28");
            one.setTotalFare("$" + (i+2)*5 + ".00");
            datas.add(one);
        }
        recyclerView = findViewById(R.id.trip_recucleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(new TripAdapter(datas));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
