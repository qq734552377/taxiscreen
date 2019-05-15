package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.adapters.ErpAdapter;
import com.ucast.taxiscreen.adapters.TripAdapter;
import com.ucast.taxiscreen.enties.ErpShowObj;
import com.ucast.taxiscreen.enties.TripShowObj;

import java.util.ArrayList;

public class ErpTestActivity extends AppCompatActivity {
    ArrayList<ErpShowObj> datas = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erp_test);
        getSupportActionBar().hide();
        for (int i = 0; i < 13; i++) {
            ErpShowObj one = new ErpShowObj();
            one.setErpSurcharge("$ "+ (i+1) * 7 + ".00");
            one.setErpTime("May " + (i+1)+ " 18:28");
            datas.add(one);
        }
        recyclerView = findViewById(R.id.erp_recucleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(new ErpAdapter(datas));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
