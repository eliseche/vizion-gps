package com.globalis.viziongps;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class Sender implements Runnable {
	private String remoteIp = "200.80.220.10";
	private int port = 9999;	
	
	@Override
	public void run() {
		try {			
			int lat = (int)GpsData.getLatitude();
			String latitude = String.valueOf(lat).substring(0, 8);
			int lon = (int)GpsData.getLongitude();
			String longitude = new StringBuffer(String.valueOf(lon).substring(0, 8)).insert(1, 0).toString();			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");			
			String date = sdf.format(new Date().toGMTString());
			
			String data = ">RGP" + date + latitude + longitude + "000000300FF0000;ID=9993;#0001;*FF<";			
			byte[] dataBytes = data.getBytes();			
			InetAddress serverAddress = InetAddress.getByName(remoteIp);
			DatagramSocket socket = new DatagramSocket();			
			DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, serverAddress, port);
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