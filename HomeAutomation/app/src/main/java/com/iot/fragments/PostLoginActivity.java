package com.iot.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.iot.com.iot.util.DividerItemDecoration;
import com.iot.com.iot.util.RacAdapter;
import com.iot.com.iot.util.RecyclerItemClickListener;
import com.iot.entity.RacInfo;
import com.iot.services.BeaconService;

import java.util.ArrayList;
import java.util.List;

import iot.accenture.com.iotapplication.MainActivity;
import iot.accenture.com.warehouseapplication.R;

public class PostLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<RacInfo> racList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RacAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                        Intent intent=new Intent(PostLoginActivity.this,ColumnActivity.class);
                        startActivity(intent);
                    }
                })
        );

        prepareListData();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDefaultValueToSharedPreference();

        Intent intent=new Intent(PostLoginActivity.this, BeaconService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setDefaultValueToSharedPreference(){
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        float savedDistance = sharedPref.getFloat(getString(R.string.saved_score),  0.10f);
        String ip_temp = sharedPref.getString("IP", "52.26.174.162:8081");
        MainActivity.ipAddress=ip_temp;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(getString(R.string.saved_score), savedDistance);
        editor.putString("IP", ip_temp);
        editor.commit();
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent intent=new Intent(PostLoginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_login, menu);
        return true;
    }

    @Override*/
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(PostLoginActivity.this,SettingActivity.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent(PostLoginActivity.this,BeakonReceiverActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_settings) {
            Intent intent = new Intent(PostLoginActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
