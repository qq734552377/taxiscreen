package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.adapters.ClickedTimeAdapter;
import com.ucast.taxiscreen.adapters.ErpAdapter;
import com.ucast.taxiscreen.enties.ClickedTimeShowObj;
import com.ucast.taxiscreen.enties.GpsShowObj;

import java.util.ArrayList;

public class PanicTestActivity extends AppCompatActivity {
    ArrayList<ClickedTimeShowObj> datas = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_test);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.clicked_time_recucleview);
        for (int i = 0; i < 13; i++) {
            ClickedTimeShowObj one = new ClickedTimeShowObj();
            one.setClickedTime("May " + (i+1)+ " 18:28");
            datas.add(one);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(new ClickedTimeAdapter(datas));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
