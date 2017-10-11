package com.iot.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.iot.com.iot.util.DividerItemDecoration;
import com.iot.com.iot.util.RacAdapter;
import com.iot.com.iot.util.RecyclerItemClickListener;
import com.iot.entity.RacInfo;

import java.util.ArrayList;
import java.util.List;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 12/29/2016.
 */

public class RackScreenFragment extends Activity {
    private List<RacInfo> racList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RacAdapter mAdapter;



    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rac_screen_fragment);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RacAdapter(racList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                     public void onItemClick(View view, int position) {
                        RacInfo racInfo=racList.get(position);
                         Intent intent=new Intent(RackScreenFragment.this,ColumnActivity.class);
                         startActivity(intent);
                     }
                })
        );

        prepareListData();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    private void prepareListData(){
        RacInfo racInfo=new RacInfo("Rac 1",1,"Column: 20","Container: 30");
        racList.add(racInfo);

        racInfo=new RacInfo("Rac 2",1,"Column: 20","Container: 30");
        racList.add(racInfo);

        racInfo=new RacInfo("Rac 3",1,"Column: 20","Container: 30");
        racList.add(racInfo);

        racInfo=new RacInfo("Rac 4",1,"Column: 20","Container: 30");
        racList.add(racInfo);


        racInfo=new RacInfo("Rac 5",1,"Column: 20","Container: 30");
        racList.add(racInfo);


        racInfo=new RacInfo("Rac 6",1,"Column: 20","Container: 30");
        racList.add(racInfo);


        racInfo=new RacInfo("Rac 7",1,"Column: 20","Container: 30");
        racList.add(racInfo);


        racInfo=new RacInfo("Rac 8",1,"Column: 20","Container: 30");
        racList.add(racInfo);


        racInfo=new RacInfo("Rac 9",1,"Column: 20","Container: 30");
        racList.add(racInfo);

        racInfo=new RacInfo("Rac 10",1,"Column: 20","Container: 30");
        racList.add(racInfo);

        mAdapter.notifyDataSetChanged();

    }
}