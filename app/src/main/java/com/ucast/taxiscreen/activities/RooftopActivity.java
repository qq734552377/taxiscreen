package com.ucast.taxiscreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ucast.taxiscreen.BasePermisionActivity;
import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.socket.entities.MDUSendCmd;
import com.ucast.taxiscreen.socket.entities.SendToBlackMsg;
import com.ucast.taxiscreen.socket.queue.SendToBlackBoxMsgQueuemanager;
import com.ucast.taxiscreen.tools.MyTools;
import com.ucast.taxiscreen.tools.SavePasswd;
import com.ucast.taxiscreen.tools.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.netty.util.internal.StringUtil;

public class RooftopActivity extends BasePermisionActivity {

    @BindView(R.id.free_text_et)
    EditText free_et;
    @BindView(R.id.select_color_rg)
    RadioGroup select_color_rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooftop);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

    }

    @OnClick({R.id.show_hired_bt,R.id.show_taxi_bt,R.id.show_oncall_bt,R.id.show_free_text_bt})
    public void onBtClicked(View v){
        int id = v.getId();
        switch (id){
            case R.id.show_hired_bt:
                sendToBalckSysRooftop(getString(R.string.show_rooftop_hired));
                break;
            case R.id.show_taxi_bt:
                sendToBalckSysRooftop(getString(R.string.show_rooftop_taxi));
                break;
            case R.id.show_oncall_bt:
                sendToBalckSysRooftop(getString(R.string.show_rooftop_oncall));
                break;
            case R.id.show_free_text_bt:
                String msg = free_et.getText().toString().trim();
                if (msg.isEmpty()){
                    ToastUtil.showToast(RooftopActivity.this,"请填写顶灯显示的文字");
                    return;
                }
                String color = SavePasswd.getInstace().get(SavePasswd.ROOFTOPCOLOR);
                sendToBalckCustormRooftop(color + "," + msg);
                break;
            default:
                break;
        }
    }
    @OnCheckedChanged({R.id.select_red_rb,R.id.select_orange_rb,R.id.select_green_rb})
    public void onColorChange(CompoundButton view, boolean isChanged){
        if (!isChanged)
            return;
        switch (view.getId()){
            case R.id.select_red_rb:
                SavePasswd.getInstace().save(SavePasswd.ROOFTOPCOLOR,getString(R.string.rooftop_red_color));
                break;
            case R.id.select_orange_rb:
                SavePasswd.getInstace().save(SavePasswd.ROOFTOPCOLOR,getString(R.string.rooftop_orange_color));
                break;
            case R.id.select_green_rb:
                SavePasswd.getInstace().save(SavePasswd.ROOFTOPCOLOR,getString(R.string.rooftop_green_color));
                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String color = SavePasswd.getInstace().get(SavePasswd.ROOFTOPCOLOR);
        if (color.isEmpty()){
            SavePasswd.getInstace().save(SavePasswd.ROOFTOPCOLOR,getString(R.string.rooftop_red_color));
            select_color_rg.check(R.id.select_red_rb);
            return;
        }
        if (color.equals(getString(R.string.rooftop_red_color))){
            select_color_rg.check(R.id.select_red_rb);
            return;
        }
        if (color.equals(getString(R.string.rooftop_orange_color))){
            select_color_rg.check(R.id.select_orange_rb);
            return;
        }
        if (color.equals(getString(R.string.rooftop_green_color))){
            select_color_rg.check(R.id.select_green_rb);
            return;
        }

    }

    public void sendToBalckSysRooftop(String msg){
        SendToBlackMsg sendMsg = new SendToBlackMsg();
        sendMsg.setSendCmd(MDUSendCmd.RooftopSetSys);
        sendMsg.setSendLength(MyTools.get3DigitNum(msg.length()));
        sendMsg.setSendContent(msg);
        SendToBlackBoxMsgQueuemanager.getInstance().getOneQueue(0).addItem(sendMsg);
    }
    public void sendToBalckCustormRooftop(String msg){
        SendToBlackMsg sendMsg = new SendToBlackMsg();
        sendMsg.setSendCmd(MDUSendCmd.RooftopSetCustom);
        sendMsg.setSendLength(MyTools.get3DigitNum(msg.length()));
        sendMsg.setSendContent(msg);
        SendToBlackBoxMsgQueuemanager.getInstance().getOneQueue(0).addItem(sendMsg);
    }
}
