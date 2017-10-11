package com.iot.pojo;

import java.util.List;

public class DashBoard {

	private List<Location> locations;
	private Status status;
	
	
	
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
