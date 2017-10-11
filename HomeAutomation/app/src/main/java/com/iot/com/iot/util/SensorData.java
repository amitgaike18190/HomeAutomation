package com.iot.com.iot.util;

public class SensorData {
    private String airflow;
    private String humidity;
    private String luminosity;
    private String noise;
    private String swtch;
    private String temperature;

    public SensorData() {
        this.luminosity = BuildConfig.FLAVOR;
        this.noise = BuildConfig.FLAVOR;
        this.humidity = BuildConfig.FLAVOR;
        this.temperature = BuildConfig.FLAVOR;
        this.airflow = BuildConfig.FLAVOR;
        this.swtch = BuildConfig.FLAVOR;
    }

    public String getLuminosity() {
        return this.luminosity;
    }

    public void setLuminosity(String luminosity) {
        this.luminosity = luminosity;
    }

    public String getNoise() {
        return this.noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAirflow() {
        return this.airflow;
    }

    public void setAirflow(String airflow) {
        this.airflow = airflow;
    }

    public String getSwtch() {return this.swtch; }

    public void setSwtch(String swtch) {this.swtch = swtch; }
}
