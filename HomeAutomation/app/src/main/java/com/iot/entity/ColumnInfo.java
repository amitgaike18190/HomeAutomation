package com.iot.entity;

/**
 * Created by amit.gaike on 12/30/2016.
 */

public class ColumnInfo {
    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public int getColumnImageId() {
        return columnImageId;
    }

    public void setColumnImageId(int columnImageId) {
        this.columnImageId = columnImageId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private int columnId;
    private int  columnImageId;
    private String columnName;
}
