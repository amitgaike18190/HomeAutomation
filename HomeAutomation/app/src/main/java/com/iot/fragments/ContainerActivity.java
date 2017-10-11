package com.iot.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iot.com.iot.util.ContainerListAdapter;
import com.iot.entity.ContainerInfo;

import java.util.ArrayList;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 12/30/2016.
 */

public class ContainerActivity extends Activity {
    ArrayList<ContainerInfo>containerInfoArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_screen);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        prepareListData();
        ContainerListAdapter adapter=new ContainerListAdapter(this,containerInfoArrayList);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ContainerActivity.this,ControlPanelActivity.class);
                startActivity(intent);
            }
        });

    }

    private void prepareListData() {
        containerInfoArrayList=new ArrayList<ContainerInfo>();
        for(int i=0;i<20;i++){
            ContainerInfo containerInfo=new ContainerInfo();
            containerInfo.setcontainerName("Container "+(i+1));
            containerInfoArrayList.add(containerInfo);
        }
    }

}
