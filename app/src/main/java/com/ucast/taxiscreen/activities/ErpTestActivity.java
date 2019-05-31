package com.ucast.taxiscreen.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.widget.TextView;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.adapters.ErpAdapter;
import com.ucast.taxiscreen.adapters.TripAdapter;
import com.ucast.taxiscreen.enties.ErpShowObj;
import com.ucast.taxiscreen.enties.TripShowObj;
import com.ucast.taxiscreen.events.ERPMountEvent;
import com.ucast.taxiscreen.socket.entities.MDUSendCmd;
import com.ucast.taxiscreen.socket.entities.SendToBlackMsg;
import com.ucast.taxiscreen.socket.queue.SendToBlackBoxMsgQueuemanager;
import com.ucast.taxiscreen.tools.MyDialog;
import com.ucast.taxiscreen.tools.MyTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErpTestActivity extends BasePermisionActivity {
    ArrayList<ErpShowObj> datas = new ArrayList<>();
    @BindView(R.id.erp_recucleview)
    RecyclerView recyclerView;
    @BindView(R.id.erp_amout_tv)
    TextView mount_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erp_test);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        for (int i = 0; i < 13; i++) {
            ErpShowObj one = new ErpShowObj();
            one.setErpSurcharge("$ "+ (i+1) * 7 + ".00");
            one.setErpTime("May " + (i+1)+ " 18:28");
            datas.add(one);
        }
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

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getMountHandle(ERPMountEvent event){
        if (event.getMount().isEmpty()){
            MyDialog.showToast(ErpTestActivity.this,"数据为空");
            return;
        }
        mount_tv.setText(event.getMount());
    }

    @OnClick({R.id.get_amt_bt})
    public void onBtClicked(View view){
        switch (view.getId()){
            case R.id.get_amt_bt:
                SendToBlackMsg sendMsg = new SendToBlackMsg();
                sendMsg.setSendCmd(MDUSendCmd.QueryERPMount);
                sendMsg.setSendLength(MyTools.get3DigitNum(0));
                sendMsg.setSendContent("");
                MyTools.writeSimpleLog("发送—>" + sendMsg.toString());
                SendToBlackBoxMsgQueuemanager.getInstance().getOneQueue(0).addItem(sendMsg);
                break;

            default:

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
