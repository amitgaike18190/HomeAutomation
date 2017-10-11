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

import com.iot.entity.ColumnInfo;
import com.iot.entity.ContainerInfo;

import java.util.ArrayList;

import iot.accenture.com.warehouseapplication.R;


public class ColumnListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ColumnInfo>columnList;
    // Constructor
    public ColumnListAdapter(Context c,ArrayList<ColumnInfo>columnList) {
        this.columnList=columnList;
        mContext = c;
    }

    @Override
    public int getCount() {
        return columnList.size();
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
            v = vi.inflate(R.layout.column_item, null);
        }

        ColumnInfo columnInfo = columnList.get(position);
        if(columnInfo!=null){
            TextView textView=(TextView) v.findViewById(R.id.columnTextView);
            textView.setText(columnInfo.getColumnName());
        }
        return v;
    }




}
