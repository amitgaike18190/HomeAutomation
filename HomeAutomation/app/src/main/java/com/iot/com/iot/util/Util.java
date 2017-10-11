package com.iot.com.iot.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.iotdata.model.GetThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowResult;
import com.amazonaws.services.iotdata.model.UpdateThingShadowRequest;
import com.amazonaws.services.iotdata.model.UpdateThingShadowResult;

import java.nio.ByteBuffer;

import iot.accenture.com.iotapplication.MainActivity;

/**
 * Created by amit.gaike on 1/13/2017.
 */

public class Util {

    AWSIotDataClient iotDataClient;
    BasicAWSCredentials basicAWSCredentials;
    GetThingShadowRequest getThingShadowRequest;
    public static String toggle = "0";

    public static void setTextStyle(TextView view, String fulltext, String subtext) {
        try {
            view.setText(fulltext, TextView.BufferType.SPANNABLE);
            Spannable str = (Spannable) view.getText();
            int i = fulltext.indexOf(subtext);
            str.setSpan(new StyleSpan(Typeface.BOLD), i, i + subtext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }catch(Exception e){

        }
        //str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static boolean isTempratureAboveThreshold(String tempratureValue){
        if(tempratureValue!=null) {
            Float tempratureFloatValue = new Float(tempratureValue);
            if (tempratureFloatValue > new Float(MainActivity.threshodValue)) {
                return true;
            }
        }
        return false;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /*public SensorData callGetWebService(String url){
        try {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestProperty("Authorization:Basic",new Constants().authorizationBasicParamValue);
            InputStream is = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (is)));

            String output = br.readLine();
            System.out.println("Output from Server .... \n" + output);
            SensorData sensorData= new Parser().parseSensorData(output);
            return sensorData;
        }catch(MalformedURLException me){
            Log.d("","");
        }catch(IOException ie){
            Log.d("","");
        }
        return null;
    }*/

    public SensorData callGetWebService(String url){
        try {
            /*if(MainActivity.ipAddress.equalsIgnoreCase("52.26.174.162:8081")){
                basicAWSCredentials = new BasicAWSCredentials("AKIAI5IR75R75MYM3EKQ", "hDV+tqPKJOF4ep6wVRHOKr/q9NCw3Wt1V/gqFPwL");
                iotDataClient = new AWSIotDataClient(basicAWSCredentials);
                iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-east-1.amazonaws.com", "iotdata", "us-east-1");
                getThingShadowRequest = new GetThingShadowRequest().withThingName("Switch");
            }else if (MainActivity.ipAddress.equalsIgnoreCase("52.35.194.238:8080")){
                basicAWSCredentials = new BasicAWSCredentials("AKIAIIRXG4L6AGYR6M6Q", "d6bowwQp4SCsM7lVmhNcKmR5eNTFGhiSbAulOyln");
                iotDataClient = new AWSIotDataClient(basicAWSCredentials);
                iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-west-2.amazonaws.com", "iotdata", "us-west-2");
                getThingShadowRequest = new GetThingShadowRequest().withThingName("raspberry3");
            }*/

            basicAWSCredentials = new BasicAWSCredentials("AKIAI5IR75R75MYM3EKQ", "hDV+tqPKJOF4ep6wVRHOKr/q9NCw3Wt1V/gqFPwL");
            iotDataClient = new AWSIotDataClient(basicAWSCredentials);
            iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-east-1.amazonaws.com", "iotdata", "us-east-1");
            getThingShadowRequest = new GetThingShadowRequest().withThingName("Bangalore_Peanut");

            GetThingShadowResult result = iotDataClient.getThingShadow(getThingShadowRequest);
            byte[] bytes = new byte[result.getPayload().remaining()];
            result.getPayload().get(bytes);
            String resultString = new String(bytes);
            Log.d("Result String: ", resultString);

            SensorData sensorData= new Parser().parseSensorData(resultString);
            return sensorData;
        }catch(Exception e){
            Log.d("","");
        }
        return null;
    }


    public boolean callPostWebService(){
        try {
            String updateState;

            updateState = String.format("{\"state\":{\"desired\":{\"Switch\":%s}}}", toggle);
            UpdateThingShadowRequest request = new UpdateThingShadowRequest();

            /*if(MainActivity.ipAddress.equalsIgnoreCase("52.26.174.162:8081")){
                basicAWSCredentials = new BasicAWSCredentials("AKIAI5IR75R75MYM3EKQ", "hDV+tqPKJOF4ep6wVRHOKr/q9NCw3Wt1V/gqFPwL");
                iotDataClient = new AWSIotDataClient(basicAWSCredentials);
                iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-east-1.amazonaws.com", "iotdata", "us-east-1");
                request.setThingName("Switch");
            }else if (MainActivity.ipAddress.equalsIgnoreCase("52.35.194.238:8080")){
                basicAWSCredentials = new BasicAWSCredentials("AKIAIIRXG4L6AGYR6M6Q", "d6bowwQp4SCsM7lVmhNcKmR5eNTFGhiSbAulOyln");
                iotDataClient = new AWSIotDataClient(basicAWSCredentials);
                iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-west-2.amazonaws.com", "iotdata", "us-west-2");
                request.setThingName("raspberry3");
            }*/

            basicAWSCredentials = new BasicAWSCredentials("AKIAI5IR75R75MYM3EKQ", "hDV+tqPKJOF4ep6wVRHOKr/q9NCw3Wt1V/gqFPwL");
            iotDataClient = new AWSIotDataClient(basicAWSCredentials);
            iotDataClient.setEndpoint("https://a2xjyaic20508y.iot.us-east-1.amazonaws.com", "iotdata", "us-east-1");
            request.setThingName("Bangalore_Peanut");

            //

            ByteBuffer payloadBuffer = ByteBuffer.wrap(updateState.getBytes());
            request.setPayload(payloadBuffer);

            UpdateThingShadowResult result = iotDataClient.updateThingShadow(request);

            byte[] bytes = new byte[result.getPayload().remaining()];
            result.getPayload().get(bytes);
            String resultString = new String(bytes);
            if(toggle == "0"){
                toggle= "1";
            }else if(toggle == "1"){
                toggle = "0";
            }
        }catch(Exception e){
            Log.d("","");
        }
        return false;
    }


}
