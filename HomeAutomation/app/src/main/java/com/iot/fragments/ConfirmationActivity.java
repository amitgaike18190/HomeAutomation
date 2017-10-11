package com.iot.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 1/24/2017.
 */

public class ConfirmationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirmation_screen);
        Button okButton=(Button) findViewById(R.id.okButon);
        Button cancelButton=(Button)findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ConfirmationActivity.this,BeakonReceiverActivity.class);
                startActivity(intent);

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        BeakonReceiverActivity.shouldShowConfirmationPopup = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // BeakonReceiverActivity.shouldShowConfirmationPopup = true;
    }
}
