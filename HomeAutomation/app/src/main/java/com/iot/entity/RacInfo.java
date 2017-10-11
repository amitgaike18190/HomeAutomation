package com.iot.entity;

/**
 * Created by amit.gaike on 12/30/2016.
 */

public class RacInfo {
    private String racName;
    private int racId;
    private String racColumn;
    private String racContainer;

    public RacInfo(String racName,int racId,String racColumn,String racContainer){
        this.racName=racName;
        this.racId=racId;
        this.racColumn=racColumn;
        this.racContainer=racContainer;
    }

    public String getRacColumn() {
        return racColumn;
    }

    public void setRacColumn(String racColumn) {
        this.racColumn = racColumn;
    }

    public String getRacContainer() {
        return racContainer;
    }

    public void setRacContainer(String racContainer) {
        this.racContainer = racContainer;
    }


    public String getRacName() {
        return racName;
    }

    public void setRacName(String racName) {
        this.racName = racName;
    }

    public int getRacId() {
        return racId;
    }

    public void setRacId(int racId) {
        this.racId = racId;
    }
}
