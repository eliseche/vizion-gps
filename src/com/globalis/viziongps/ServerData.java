package com.globalis.viziongps;

public class ServerData {
	private static String server;
	private static String port;
	private static String equipment;
	
	public static String getServer() {
		return server;
	}
	
	public static void setServer(String server) {
		ServerData.server =  server;
	}
	
	public static String getEquipment() {
		return equipment;
	}
	
	public static void setEquipment(String equipment) {
		ServerData.equipment =  equipment;
	}
	
	public static String getPort() {
		return port;
	}
	
	public static void setPort(String port) {
		ServerData.port =  port;
	}
}