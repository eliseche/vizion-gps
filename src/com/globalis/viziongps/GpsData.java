package com.globalis.viziongps;

public class GpsData {
	private static double latitude;
	private static double longitude;
	private static double altitude;
	
	public static double getLatitude() {
		return latitude;
	}
	
	public static double getLongitude() {
		return longitude;
	}
	
	public static double getAltitude() {
		return altitude;
	}
	
	public static void setLatLon(double latitude, double longitude) {
		GpsData.latitude = latitude;
		GpsData.longitude = longitude;
	}
	
	public static void setAltitude(double altitude) {
		GpsData.altitude = altitude;
	}
}
