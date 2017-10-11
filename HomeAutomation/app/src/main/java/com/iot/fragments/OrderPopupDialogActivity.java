package com.iot.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 1/3/2017.
 */

public class OrderPopupDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_popup);
        Button climateControlButton=(Button)findViewById(R.id.climateControlButton);
        climateControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderPopupDialogActivity.this,ControlPanelActivity.class);
                startActivity(intent);
            }
        });
    }
}
