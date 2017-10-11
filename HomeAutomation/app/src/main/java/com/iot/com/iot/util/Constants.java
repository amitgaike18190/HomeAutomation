package com.iot.com.iot.util;

import iot.accenture.com.iotapplication.MainActivity;

/**
 * Created by amit.gaike on 1/19/2017.
 */

public class Constants {
    public  String heatingControlUrl="HEATING_CONTROL";
    public  String humidityControlUrl="HUMIDITY_SENSOR";
    public  String tempratureControlUrl="TEMPERATURE_SENSOR";
    public  String baseUrlStart="http://"+MainActivity.ipAddress+":"+MainActivity.port+"/om2m/gscl/applications/";
    public  String baseUrlEnd="/containers/DATA/contentInstances/latest/content";
    public  String authorizationBasicParamValue="YWRtaW46YWRtaW4";


}
