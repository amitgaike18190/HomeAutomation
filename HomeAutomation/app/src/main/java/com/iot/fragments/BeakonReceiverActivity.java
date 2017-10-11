package com.iot.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.iot.com.iot.util.Util;
import com.iot.pojo.Response;
import com.iot.services.APIParams;
import com.iot.services.ApiTest;
import com.iot.services.BeaconService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import iot.accenture.com.iotapplication.MainActivity;
import iot.accenture.com.warehouseapplication.R;

import static com.iot.com.iot.util.Util.decodeSampledBitmapFromResource;
import static org.altbeacon.beacon.AltBeaconParser.TAG;


/**
 * Created by amit.gaike on 1/3/2017.
 */

public class BeakonReceiverActivity extends Activity implements BeaconConsumer {
    private BeaconManager beaconManager;
    private static final int LOCATION_SERVICE_PERMISSIONM=1;
    public static boolean shouldShowConfirmationPopup=true;
    public static boolean shouldSendStatus=false;
    BeaconService  beaconService;
    ServiceConnection mConnection;
    boolean mBounded;
    private String UUID,location;
    private static String recentUUID;
    private Region region;
    public static String vehicleId="1001";
    public static boolean isAlreadyPicked=false;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beakon_receiver_screen);

        final TextView statusTextView=(TextView) findViewById(R.id.itemStatusTextView);
        final TextView racTextView=(TextView) findViewById(R.id.racTextView);
        final TextView vehicleTextView=(TextView) findViewById(R.id.vehicleTextView);
        final TextView itemNameTextView=(TextView) findViewById(R.id.itemNameTextView);
        final ImageView itemImageView=(ImageView) findViewById(R.id.itemPicture);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_SERVICE_PERMISSIONM);

        Util.setTextStyle(statusTextView,"Status: "+"Not In Range","Status:");
        Util.setTextStyle(racTextView,"Rack Id: "+"","Rack Id:");
        Util.setTextStyle(vehicleTextView,"Vehicle No: "+"MH 14 AH 2345","Vehicle No:");

        shouldShowConfirmationPopup=false;
        shouldSendStatus=true;
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.update.ui.distance");
        filter.addAction("com.update.ui.detail");
        filter.addAction("com.update.test");
        filter.addAction("com.update.beaconstatus");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        Intent startIntent=new Intent(BeakonReceiverActivity.this,BeaconService.class);
        startService(startIntent);

        BroadcastReceiver updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase("com.update.ui.distance")) {
                    final String statusText = intent.getStringExtra("statusText");
                    final String rackLocation=intent.getStringExtra("RackLocation");
                    final int itemId=intent.getIntExtra("ItemId",0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (statusTextView != null)
                                Util.setTextStyle(statusTextView, "Status: " + statusText + " Away", "Status:");
                            Util.setTextStyle(racTextView,"Rack Id: "+"Rack "+rackLocation,"Rack Id:");
                            if(!statusText.equalsIgnoreCase("Picked")){
                                Util.setTextStyle(statusTextView, "Status: " + statusText + " Away", "Status:");
                            }else{
                                Util.setTextStyle(statusTextView, "Status: " + "Picked", "Status:");
                            }
                            if(itemId==1001){
                                itemNameTextView.setText("Item To Pick: "+"Steel Pipe");
                                itemImageView.setImageBitmap(
                                        decodeSampledBitmapFromResource(getResources(), R.drawable.rack1, 100, 100));
                            }else if(itemId==1002){
                                itemNameTextView.setText("Item To Pick: "+"Steel Pipe");
                                itemImageView.setImageBitmap(
                                        decodeSampledBitmapFromResource(getResources(), R.drawable.rack2, 100, 100));
                            }else if(itemId==1003){
                                itemNameTextView.setText("Item To Pick: "+"Motor");
                                itemImageView.setImageBitmap(
                                        decodeSampledBitmapFromResource(getResources(), R.drawable.rack3, 100, 100));
                            }else if(itemId==1004){
                                itemNameTextView.setText("Item To Pick: "+"Medicine Container");
                                itemImageView.setImageBitmap(
                                        decodeSampledBitmapFromResource(getResources(), R.drawable.rack3, 100, 100));
                            }else{
                                itemNameTextView.setText("Item To Pick: "+"Generator");
                                itemImageView.setImageBitmap(
                                        decodeSampledBitmapFromResource(getResources(), R.drawable.rack3, 100, 100));
                            }
                        }
                    });
                }else if(intent.getAction().equalsIgnoreCase("com.update.test")){
                    final String UUID=intent.getStringExtra("UUID");
                    final String itemId=intent.getStringExtra("Item");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // Toast.makeText(BeakonReceiverActivity.this, "UUID Received Is "+UUID +" And Item Id is "+itemId , Toast.LENGTH_LONG).show();
                          //  Toast.makeText(BeakonReceiverActivity.this, "server ip address is "+ipAddress  , Toast.LENGTH_LONG).show();
                        }
                    });
                }else if(intent.getAction().equalsIgnoreCase("com.update.beaconstatus")){
                    boolean shouldStart=intent.getBooleanExtra("shouldStart",false);
                    if(shouldStart){
                        UUID=intent.getStringExtra("UUID");
                        location=intent.getStringExtra("LocationId");
                        itemId=intent.getIntExtra("ItemId",0);
                        startBeakonReceiver();
                    }else{
                        stopBeaconReceiver();
                    }
                }
            }
        };
        registerReceiver(updateUIReciver,filter);
    }

    public void onBeaconServiceConnect() {
        region = new Region("My Beacon", Identifier.parse(UUID), null, null);

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }            }

            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
                try {
                    Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {

        }

        beaconManager.addRangeNotifier(new RangeNotifier() {
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    if(BeakonReceiverActivity.shouldSendStatus) {
                        if(!isAlreadyPicked) {
                            Intent intent = new Intent();
                            intent.setAction("com.update.ui.distance");
                            intent.putExtra("statusText", new Double(beacons.iterator().next().getDistance()).toString());
                            intent.putExtra("RackLocation", location);
                            intent.putExtra("ItemId", itemId);
                            sendBroadcast(intent);
                        }
                        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                        String defaultValue = getResources().getString(R.string.saved_score_default);
                        float savedDistance = sharedPref.getFloat(getString(R.string.saved_score), 0.10f);
                        double savedValue = new Float(savedDistance).doubleValue();
                        Log.d("Tag","saved distance value is"+savedValue);
                        double distance = beacons.iterator().next().getDistance();
                        //Toast.makeText(getApplicationContext(),"Your item is in range of "+new Double(beacons.iterator().next().getDistance()).toString()+" meters",Toast.LENGTH_SHORT).show();
                        if (beacons.iterator().next().getDistance() < new Float(savedDistance).doubleValue()) {
                            if(!isAlreadyPicked){
                                callWebServiceForVehicleAllocation(); //call web service for vehicle allocation.                                isAlreadyPicked=true;
                                isAlreadyPicked=true;

                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ie) {

                            }
                        }
                    }

                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });
    }


    public void startBeakonReceiver(){
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }


    private void callWebServiceForVehicleAllocation(){
        new UpdateBeakonStatus().execute(vehicleId);
    }

    private class UpdateBeakonStatus extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Log.d(TAG,"Calling web service for vehicle allocation");
            final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String ipAddress=sharedPref.getString("IP", MainActivity.ipAddress);
            String vehicleId=params[0];
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("vehicleId",vehicleId);
            jsonObject.addProperty("uuid",UUID);
            String jsonString=jsonObject.toString();
            APIParams apiParams=new APIParams();
            apiParams.setRequestData(jsonString);
            apiParams.setMethod("POST");
            apiParams.setUrl("http://"+ipAddress+"/BeaconServices/webapi/item/vehicle/");
            apiParams.setApiName("VEHICLE-ALLOCATION");
            try {
                Response response=new ApiTest().allocateTheVehicle(apiParams);
                if(response!=null){
                    if(response.getResultCode().equalsIgnoreCase("1")){
                        isAlreadyPicked=true;
                        if(itemId!=1005){
                            stopBeaconReceiver();
                        }
                        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(0);
                        return true;
                    }
                }

            }catch(IOException ie){
                Log.d("",ie.getMessage());
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Intent intent=new Intent();
                intent.setAction("com.update.ui.distance");
                intent.putExtra("statusText","Picked");
                intent.putExtra("RackLocation",location);
                intent.putExtra("ItemId",itemId);
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(),"Vehicle allocated succesfully",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_SERVICE_PERMISSIONM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent(BeakonReceiverActivity.this, BeaconService.class);
                    startService(intent);



                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void stopBeaconReceiver(){
        try {
            beaconManager.removeAllMonitorNotifiers();
            beaconManager.removeAllRangeNotifiers();
            beaconManager.unbind(this);
        }catch(Exception re){
            Log.d(TAG,"Exception "+re.toString());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        shouldShowConfirmationPopup=true;
        shouldSendStatus=false;
        if(beaconManager.isBound(this)){
            stopBeaconReceiver();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shouldSendStatus=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        shouldSendStatus=false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BeakonReceiverActivity.this,PostLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
