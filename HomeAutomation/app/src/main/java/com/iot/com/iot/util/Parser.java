package com.iot.com.iot.util;

/**
 * Created by amit.gaike on 1/19/2017.
 */

import android.util.Log;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*public class Parser {
    static final String KEY_CATEGORY = "category";
    static final String KEY_DATA = "data";
    static final String KEY_UNIT = "unit";
    public static SensorData sensorData;

    public SensorData parseSensorData(String responseData) {
        if (sensorData == null) {
            sensorData = new SensorData();
        }
        Document doc = getDomElement(responseData);
        Element elementStr = (Element) doc.getElementsByTagName("Str").item(0);
        NodeList nl;
        int i;
        String name;
        String value;
        if (elementStr.getAttribute("val").equalsIgnoreCase("TEMPERATURE_SENSOR")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setTemperature(((Element) nl.item(1)).getAttribute("val"));
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("HEATING_CONTROL")) {
            nl = doc.getElementsByTagName("int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setLuminosity(value);
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("NOISE")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setNoise(((Element) nl.item(1)).getAttribute("val"));
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("HUMIDITY_SENSOR")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setHumidity(((Element) nl.item(1)).getAttribute("val"));
            }
        }
        return sensorData;
    }*/

public class Parser {
    static final String KEY_CATEGORY = "category";
    static final String KEY_DATA = "data";
    static final String KEY_UNIT = "unit";
    public static SensorData sensorData;

    public SensorData parseSensorData(String responseData) {
        if (sensorData == null) {
            sensorData = new SensorData();
        }

        try {
            JSONObject Switch = new JSONObject(responseData);

            JSONObject state = Switch.getJSONObject("state");
            JSONObject desired = state.getJSONObject("desired");
            sensorData.setTemperature(desired.getString("Temperature"));
            sensorData.setHumidity(desired.getString("Humidity"));
            sensorData.setSwtch(desired.getString("Switch"));
        }catch(Exception e){
            Log.d("Tag",e.toString());
        }

       /* Document doc = getDomElement(responseData);
        Element elementStr = (Element) doc.getElementsByTagName("Str").item(0);
        NodeList nl;
        int i;
        String name;
        String value;
        if (elementStr.getAttribute("val").equalsIgnoreCase("TEMPERATURE_SENSOR")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setTemperature(((Element) nl.item(1)).getAttribute("val"));
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("HEATING_CONTROL")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setLuminosity(((Element) nl.item(1)).getAttribute("val"));
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("NOISE")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setNoise(((Element) nl.item(1)).getAttribute("val"));
            }
        } else if (elementStr.getAttribute("val").equalsIgnoreCase("HUMIDITY_SENSOR")) {
            nl = doc.getElementsByTagName("Int");
            for (i = 0; i < nl.getLength(); i++) {
                name = ((Element) nl.item(i)).getAttribute("name");
                value = ((Element) nl.item(0)).getAttribute("val");
                sensorData.setHumidity(((Element) nl.item(1)).getAttribute("val"));
            }
        }*/
        return sensorData;
    }

    public Document getDomElement(String xml) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e2) {
            Log.e("Error: ", e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e("Error: ", e3.getMessage());
            return null;
        }
    }

    public String getValue(Element item, String str) {
        return getElementValue(item.getElementsByTagName(str).item(0));
    }

    public final String getElementValue(Node elem) {
        if (elem != null && elem.hasChildNodes()) {
            for (Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNodeType() == (short) 3) {
                    return child.getNodeValue();
                }
            }
        }
        return BuildConfig.FLAVOR;
    }
}
