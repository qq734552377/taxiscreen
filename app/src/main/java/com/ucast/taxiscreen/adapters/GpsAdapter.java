package com.ucast.taxiscreen.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.enties.ErpShowObj;
import com.ucast.taxiscreen.enties.GpsShowObj;

import java.util.ArrayList;

/**
 * Created by pj on 2019/5/14.
 */
public class GpsAdapter extends RecyclerView.Adapter {
    private ArrayList<GpsShowObj> allDatas;
    public GpsAdapter(ArrayList<GpsShowObj> datas) {
        this.allDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.erp_list_item,viewGroup,false);
        return new ErpViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ErpViewHolder tripViewHolder = (ErpViewHolder) viewHolder;
        if (i%2 == 1){
            tripViewHolder.setContentBackgroupGreen();
        }else{
            tripViewHolder.setContentBackgroupBlue();
        }
        tripViewHolder.setData(allDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return allDatas.size();
    }

    public class ErpViewHolder extends RecyclerView.ViewHolder{
        private TextView conten_item_1;
        private TextView conten_item_2;
        public ErpViewHolder(@NonNull View itemView) {
            super(itemView);
            conten_item_1 = itemView.findViewById(R.id.table_content_row_1);
            conten_item_2 = itemView.findViewById(R.id.table_content_row_2);
        }

        public void setData(GpsShowObj erpShowObj){
            conten_item_1.setText(erpShowObj.getLatlon());
            conten_item_2.setText(erpShowObj.getGpsTime());
        }

        public void setContentBackgroupGreen(){
            conten_item_1.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
            conten_item_2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
        }
        public void setContentBackgroupBlue(){
            conten_item_1.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
            conten_item_2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
        }
    }

}
