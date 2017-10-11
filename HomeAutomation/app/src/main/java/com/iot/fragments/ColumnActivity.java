package com.iot.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iot.com.iot.util.ColumnListAdapter;
import com.iot.entity.ColumnInfo;

import java.util.ArrayList;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 12/30/2016.
 */

public class ColumnActivity extends Activity {
    private ArrayList<ColumnInfo>columnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.column_screen);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        prepareListData();
        gridview.setAdapter(new ColumnListAdapter(this,columnList));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ColumnActivity.this,ContainerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareListData() {
        columnList=new ArrayList<ColumnInfo>();
        for(int i=0;i<20;i++){
            ColumnInfo columnImfo=new ColumnInfo();
            columnImfo.setColumnName("Column "+(i+1));
            columnList.add(columnImfo);
        }
    }
}
