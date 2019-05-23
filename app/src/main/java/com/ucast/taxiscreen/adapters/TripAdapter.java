package com.ucast.taxiscreen.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucast.taxiscreen.R;
import com.ucast.taxiscreen.enties.TripShowObj;

import java.util.ArrayList;

/**
 * Created by pj on 2019/5/14.
 */
public class TripAdapter extends RecyclerView.Adapter {
    private ArrayList<TripShowObj> allTripshowobjs;
    public TripAdapter(ArrayList<TripShowObj> datas) {
        this.allTripshowobjs = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_list_item,viewGroup,false);
        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TripViewHolder tripViewHolder = (TripViewHolder) viewHolder;
        if (i%2 == 1){
            tripViewHolder.setContentBackgroupGreen();
        }else{
            tripViewHolder.setContentBackgroupBlue();
        }
        tripViewHolder.setData(allTripshowobjs.get(i));
    }

    @Override
    public int getItemCount() {
        return allTripshowobjs.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder{
        private TextView conten_item_1;
        private TextView conten_item_2;
        private TextView conten_item_3;
        private TextView conten_item_4;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            conten_item_1 = itemView.findViewById(R.id.table_content_row_1);
            conten_item_2 = itemView.findViewById(R.id.table_content_row_2);
            conten_item_3 = itemView.findViewById(R.id.table_content_row_3);
            conten_item_4 = itemView.findViewById(R.id.table_content_row_4);
        }

        public void setData(TripShowObj tripShowObj){
            conten_item_1.setText(tripShowObj.getTripId());
            conten_item_2.setText(tripShowObj.getStartTime());
            conten_item_3.setText(tripShowObj.getEndTime());
            conten_item_4.setText(tripShowObj.getTotalFare());
        }

        public void setContentBackgroupGreen(){
            conten_item_1.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
            conten_item_2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
            conten_item_3.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
            conten_item_4.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.greendark));
        }
        public void setContentBackgroupBlue(){
            conten_item_1.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
            conten_item_2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
            conten_item_3.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
            conten_item_4.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.baseBtColor));
        }
    }

}
