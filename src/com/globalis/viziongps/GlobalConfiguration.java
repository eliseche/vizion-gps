package com.globalis.viziongps;

public class GlobalConfiguration {
	private static GlobalConfiguration instance = null;
	private String server;
	private String port;
	private String equipment;
	private int updateTime;
	
	private static void createInstance() {
		if (instance == null) {
			instance = new GlobalConfiguration();
		}			
	}
	
	public static GlobalConfiguration getInstance() {
		createInstance();
		return instance;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getEquipment() {
		return equipment;
	}
	
	public void setEquipment(String equipment) {		
		this.equipment = equipment;
	}
	
	public int getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
}