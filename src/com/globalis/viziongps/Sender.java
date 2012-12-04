package com.globalis.viziongps;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import android.util.Log;

public class Sender implements Runnable {
	private GpsData gpsData;
	private GlobalConfiguration globalConfiguration;
	private boolean gpsValid = false;
	
	public Sender() {
		gpsData = GpsData.getInstance();
		globalConfiguration = GlobalConfiguration.getInstance();
	}
	
	public enum ReportType {
		AUTO,
		PANIC,
		MEDICAL,
		MECHANIC
	}
	
	@Override
	public void run() {
		try {
			send(ReportType.AUTO);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(ReportType reportType) {
		try {
			String latitude;
			String longitude;
			
			double lat = gpsData.getLatitude();
			if(lat != 0) {
				latitude = String.valueOf(lat).replace(".", "").substring(0, 8);	
				gpsValid = true;
			}
			else {
				latitude = "00000000";
				gpsValid = false;
			}
			
			double lon = gpsData.getLongitude();
			if(lon != 0) {
				longitude = new StringBuffer(String.valueOf(lon).replace(".", "").substring(0, 8)).insert(1, 0).toString();
				gpsValid = true;
			}
			else {
				longitude = "000000000";
				gpsValid = false;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));			
			String date = sdf.format(new Date());			
			
			String data = "";
			if(reportType == ReportType.AUTO) {
				if(gpsValid)
					data = ">RGP" + date + latitude + longitude + "000000300FF0000;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
				else 
					data = ">RGP" + date + latitude + longitude + "0000000FFFF0000;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
			}
			else if (reportType == ReportType.PANIC) {
				if(gpsValid)
					data = ">RGP" + date + latitude + longitude + "000000300FF0100;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
				else 
					data = ">RGP" + date + latitude + longitude + "0000000FFFF0100;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
			}
			else if (reportType == ReportType.MEDICAL) {
				if(gpsValid)
					data = ">RGP" + date + latitude + longitude + "000000300FF0200;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
				else 
					data = ">RGP" + date + latitude + longitude + "0000000FFFF0200;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
			}
			else if (reportType == ReportType.MECHANIC) {
				if(gpsValid)
					data = ">RGP" + date + latitude + longitude + "000000300FF0300;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
				else
					data = ">RGP" + date + latitude + longitude + "0000000FFFF0300;ID=" + globalConfiguration.getEquipment() + ";#0001;*FF<";
			}
						
			byte[] dataBytes = data.getBytes();			
			InetAddress serverAddress = InetAddress.getByName(globalConfiguration.getServer());
			DatagramSocket socket = new DatagramSocket();	
			socket.setSoTimeout(60000);
			DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, serverAddress, Integer.valueOf(globalConfiguration.getPort()));
			Log.d("Sender", "Sending: " + data);
			socket.send(packet);
			Log.d("Sender", "Done");
			socket.receive(packet);
			Log.d("Sender", "Received: " + "ACK");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}