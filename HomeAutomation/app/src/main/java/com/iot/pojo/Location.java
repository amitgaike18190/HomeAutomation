package com.iot.pojo;



public class Location {

	private String locationName;
	private String itemName;
	private String itemPlacedDate;
	
	
	public String getItemPlacedDate() {
		return itemPlacedDate;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setItemPlacedDate(String itemPlacedDate) {
		this.itemPlacedDate = itemPlacedDate;
	}
	
	
}
