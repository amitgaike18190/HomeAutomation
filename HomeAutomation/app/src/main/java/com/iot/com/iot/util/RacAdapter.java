package com.iot.com.iot.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iot.entity.RacInfo;

import java.util.List;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 12/30/2016.
 */

public class RacAdapter extends RecyclerView.Adapter<RacAdapter.MyViewHolder> {

    private List<RacInfo> racList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView racTitle, racColumn, racComtainer;

        public MyViewHolder(View view) {
            super(view);
            racTitle = (TextView) view.findViewById(R.id.racNameTextView);
            racColumn = (TextView) view.findViewById(R.id.racColumnTextView);
            racComtainer = (TextView) view.findViewById(R.id.racContainerTextView);
        }
    }


    public RacAdapter(List<RacInfo> racList) {
        this.racList = racList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rac_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RacInfo racInfo = racList.get(position);
        holder.racTitle.setText(racInfo.getRacName());
        holder.racColumn.setText(racInfo.getRacColumn());
        holder.racComtainer.setText(racInfo.getRacContainer());
    }

    @Override
    public int getItemCount() {
        return racList.size();
    }
}