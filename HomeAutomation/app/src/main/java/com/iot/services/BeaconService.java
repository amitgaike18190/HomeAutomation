package com.iot.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.iot.fragments.BeakonReceiverActivity;
import com.iot.pojo.AssignedItems;
import com.iot.pojo.Item;

import org.altbeacon.beacon.BeaconManager;

import iot.accenture.com.iotapplication.MainActivity;
import iot.accenture.com.warehouseapplication.R;

import static android.content.ContentValues.TAG;
import static com.iot.fragments.BeakonReceiverActivity.vehicleId;

/**
 * Created by amit.gaike on 1/5/2017.
 */

public class BeaconService extends Service {
    private BeaconManager beaconManager;
    private String UUID,location;
    private static String recentUUID;
    private int itemId;

    @Nullable

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return super.onStartCommand(intent,flags,startId);
    }

    public class PollingThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true){
                final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String ipAddress=sharedPref.getString("IP", MainActivity.ipAddress);
                APIParams apiParams=new APIParams();
                apiParams.setMethod("GET");
                apiParams.setUrl("http://"+ipAddress+"/BeaconServices/webapi/item/vehicle/assigned/"+vehicleId);
                apiParams.setApiName("ASSIGNED-API");
                Log.d(TAG,"Polling thread is running with ip address "+ipAddress);
                try{
                    AssignedItems assignedItems=new ApiTest().getTheItemsForVehicle(apiParams);
                    if(assignedItems!=null){
                        if(assignedItems.getItems().size()>0){
                            BeakonReceiverActivity.isAlreadyPicked=false;
                            Log.d(TAG,"assigned items size greater than 0");
                            Log.d(TAG,"assigned items size greater than 0");

                            if(BeakonReceiverActivity.shouldShowConfirmationPopup) {
                                generateNottification();
                            }
                            /*if(BeakonReceiverActivity.shouldShowConfirmationPopup) {
                                Intent intent = new Intent(BeaconService.this, ConfirmationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                BeakonReceiverActivity.shouldShowConfirmationPopup = false;
                            }*/
                            Item item=assignedItems.getItems().get(0);
                            UUID=item.getUuid();
                            location=item.getLocation();
                            itemId=item.getItemId();

                            Log.d(TAG,"Assigned Item UUID Is "+item.getUuid());
                            Log.d(TAG,"Assigned Item item id Is "+item.getItemId());

                            /*Intent intent=new Intent();
                            intent.setAction("com.update.test");
                            intent.putExtra("UUID",UUID);
                            intent.putExtra("Item",itemId);
                            intent.putExtra("ipAddress",ipAddress);
                            sendBroadcast(intent);*/
                            if(recentUUID!=null && !recentUUID.equalsIgnoreCase(UUID)){
                                Log.d(TAG,"Stopping Beacon Receiver");
                                Intent intent=new Intent();
                                intent.setAction("com.update.beaconstatus");
                                intent.putExtra("shouldStart",false);
                                sendBroadcast(intent);

                                //stopBeaconReceiver();
                            }
                            recentUUID=UUID;
                            /*UUID="b9407f30-f5f8-466e-4ff9-25556b57fe6d";
                            recentUUID=UUID;*/
                            //startBeakonReceiver();
                            Intent intent=new Intent();
                            intent.setAction("com.update.beaconstatus");
                            intent.putExtra("shouldStart",true);
                            intent.putExtra("UUID",UUID);
                            intent.putExtra("ItemId",itemId);
                            intent.putExtra("LocationId",location);
                            sendBroadcast(intent);
                        }
                    }
                }catch (Exception e){
                    Log.d("Tag",e.toString());
                }
                try {
                    Thread.sleep(10000);
                }catch(Exception e){

                }
            }
        }



    }

    private void generateNottification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.icon);
        mBuilder.setContentTitle("Item Received");
        mBuilder.setContentText("New Item Received For Pickup!");
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(this, BeakonReceiverActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BeakonReceiverActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }




    @Override
    public void onDestroy() {
        Log.d(TAG,"In On Destroy Beacon Service");
        super.onDestroy();
    }


    @Override
    public boolean stopService(Intent name) {
        Log.d("TAG","Stopping the beacon serice");
        return super.stopService(name);
    }

}
