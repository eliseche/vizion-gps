package com.globalis.viziongps;

public class GpsData {
	private static GpsData instance = null;	
	private double latitude;
	private double longitude;
	private double altitude;
	
	private static void createInstance() {
		if (instance == null) {
			instance = new GpsData();
		}
	}
	
	public static GpsData getInstance() {
		createInstance();
		return instance;		
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public void setLatLon(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
}