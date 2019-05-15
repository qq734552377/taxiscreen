package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.adapters.ErpAdapter;
import com.ucast.taxiscreen.adapters.GpsAdapter;
import com.ucast.taxiscreen.enties.ErpShowObj;
import com.ucast.taxiscreen.enties.GpsShowObj;

import java.util.ArrayList;

public class GpsIOTestActivity extends AppCompatActivity {
    TextView io_info_tv;
    Button open_gps_bt;
    Button close_gps_bt;
    Button reset_gps_bt;
    ArrayList<GpsShowObj> datas = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_iotest);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {
        io_info_tv = findViewById(R.id.io_info_tv);
        io_info_tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        open_gps_bt = findViewById(R.id.open_gps_bt);
        close_gps_bt = findViewById(R.id.close_gps_bt);
        reset_gps_bt = findViewById(R.id.reset_gps_bt);
        recyclerView = findViewById(R.id.gps_recucleview);
        for (int i = 0; i < 13; i++) {
            GpsShowObj one = new GpsShowObj();
            one.setLatlon("101.200\n105.265");
            one.setGpsTime("May " + (i+1)+ " 18:28");
            datas.add(one);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(new GpsAdapter(datas));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        open_gps_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLogView("这是新增消息\n");
            }
        });

    }

    public void refreshLogView(String infoMsg){
        io_info_tv.append(infoMsg);
        int offset = io_info_tv.getLineCount() * io_info_tv.getLineHeight();
        if (offset > io_info_tv.getHeight()){
            io_info_tv.scrollTo(0,offset - io_info_tv.getHeight());
        }
    }
}
