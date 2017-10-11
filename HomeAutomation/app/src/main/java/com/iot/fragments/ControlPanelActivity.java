package com.iot.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.iot.com.iot.util.Constants;
import com.iot.com.iot.util.SensorData;
import com.iot.com.iot.util.Util;

import iot.accenture.com.iotapplication.MainActivity;
import iot.accenture.com.warehouseapplication.R;

/**
 * Created by amit.gaike on 1/3/2017.
 */

public class ControlPanelActivity extends Activity{
    Switch heatingControl;
    Button temperatureBtn, humidityBtn;
    RefreshDataThread refreshDataThread;
    RefreshHeatingControlSwitchThread refreshHeatingControlSwitchThread;
    CompoundButton.OnCheckedChangeListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_panel_screen);
        temperatureBtn=(Button) findViewById(R.id.TemperatureBtn);
        ImageView myImageView = (ImageView)findViewById(R.id.backgroundImageView);
       // myImageView.setImageResource(R.drawable.home_automation_background);

        humidityBtn=(Button) findViewById(R.id.HumidityBtn);
        heatingControl = (Switch) findViewById(R.id.heating_control);
        ImageView settingView=(ImageView) findViewById(R.id.settingView);
        settingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ControlPanelActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
        heatingControl.setChecked(false);
        MainActivity.ipAddress="52.26.174.162:8081";
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.sharedprefferencename), Context.MODE_PRIVATE);

        float savedThreshold = sharedPref.getFloat(getString(R.string.threshod_value), 25.0f);
        int timerValue = sharedPref.getInt(getString(R.string.timer_value), 5);

        MainActivity.threshodValue=new Float(savedThreshold).toString();
        MainActivity.timerValue=timerValue;

        listener =new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("ControlPanelActivity","Checked="+b);
                    if (b) {
                        Util.toggle="1";
                        callWebServiceToToggleHeatingControl();
                    } else {
                        Util.toggle="2";
                        callWebServiceToToggleHeatingControl();
                    }
            }
        };
        heatingControl.setOnCheckedChangeListener(listener);
    }

    private void startThreads(){
        refreshDataThread=new RefreshDataThread();
        refreshDataThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startThreads();
    }

    class RefreshHeatingControlSwitchThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
               /* if(ControlPanelActivity.this!=null) {
                    try {
                        Thread.sleep(MainActivity.timerValue * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (heatingControl.isChecked()) {
                                heatingControl.setChecked(false);
                            } else {
                                heatingControl.setChecked(true);
                            }
                        }
                    });
                }*/
            }
        }
    }

    class RefreshDataThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                if(ControlPanelActivity.this!=null) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new getTempratureParamsAsyncTask().execute();
                }
            }
        }
    }

    private void callWebServiceToToggleHeatingControl(){
        new updateHeatingControlStatus().execute();
    }

    public class updateHeatingControlStatus extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... strings) {
            boolean result=new Util().callPostWebService();
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }

    public class getTempratureParamsAsyncTask extends AsyncTask<Void,Void,SensorData>{
        @Override
        protected SensorData doInBackground(Void... voids) {
            SensorData data=new Util().callGetWebService(new Constants().baseUrlStart+new Constants().tempratureControlUrl+new Constants().baseUrlEnd);
            return data;
        }

        @Override
        protected void onPostExecute(SensorData data) {
            super.onPostExecute(data);
            if(data!=null){
                Log.d("TAG","Got the data for temprature "+data.toString());
                String temprature= data.getTemperature();
                Log.d("Temperature=",temprature);
                temperatureBtn.setText(temprature+"°C");
                if(temprature!=null && !temprature.equalsIgnoreCase("")) {
                    if (Util.isTempratureAboveThreshold(temprature)) {
                        heatingControl.setChecked(true);
                    } else {
                        heatingControl.setChecked(false);
                    }
                }
            }
            new getHumidityAsyncTask().execute();
        }
    }


    public class getHumidityAsyncTask extends AsyncTask<Void,Void,SensorData>{
        @Override
        protected SensorData doInBackground(Void... voids) {
            SensorData data=new Util().callGetWebService(new Constants().baseUrlStart+new Constants().humidityControlUrl+new Constants().baseUrlEnd);
            return data;
        }

        @Override
        protected void onPostExecute(SensorData data) {
            super.onPostExecute(data);
            if(data!=null){
                String humidity= data.getHumidity();
                Log.d("Humidity=",humidity);
                humidityBtn.setText(humidity+"%");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
