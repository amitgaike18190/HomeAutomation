package com.iot.com.iot.util;

/**
 * Created by amit.gaike on 12/30/2016.
 */
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.entity.ContainerInfo;

import java.util.ArrayList;

import iot.accenture.com.warehouseapplication.R;


public class ContainerListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContainerInfo>containerList;
    // Constructor
    public ContainerListAdapter(Context c,ArrayList<ContainerInfo>containerList) {
        this.containerList=containerList;
        mContext = c;
    }

    @Override
    public int getCount() {
        return containerList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.container_item, null);
        }

        ContainerInfo containerInfo = containerList.get(position);
        if(containerInfo!=null){
           TextView textView=(TextView) v.findViewById(R.id.containerTextView);
            textView.setText(containerInfo.getcontainerName());
        }
        return v;
    }




}
