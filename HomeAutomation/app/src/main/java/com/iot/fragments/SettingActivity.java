package com.iot.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iot.accenture.com.iotapplication.MainActivity;
import iot.accenture.com.warehouseapplication.R;

/**
 * Created by amit.gaike on 1/19/2017.
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);
        Button saveButton = (Button) findViewById(R.id.savebutton);

        final EditText thresholdValue,timerValueEditText;
        thresholdValue = (EditText) findViewById(R.id.thresholdValue);
        timerValueEditText=(EditText) findViewById(R.id.timerValueEditText);

        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.sharedprefferencename), Context.MODE_PRIVATE);

        float savedThreshold = sharedPref.getFloat(getString(R.string.threshod_value), 25.0f);
        int timerValue = sharedPref.getInt(getString(R.string.timer_value), 15);

        thresholdValue.setText(new Float(savedThreshold).toString());
        timerValueEditText.setText(timerValue+"");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.threshodValue = thresholdValue.getText().toString();
                MainActivity.timerValue=new Integer(timerValueEditText.getText().toString());
                if (!MainActivity.threshodValue.equalsIgnoreCase("") && MainActivity.timerValue!=0 ) {

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat(getString(R.string.threshod_value), new Float(thresholdValue.getText().toString()));
                    editor.putInt(getString(R.string.timer_value), new Integer(timerValueEditText.getText().toString()));
                    editor.commit();

                    Context context = getApplicationContext();
                    String text = "Settings saved successfully";
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
