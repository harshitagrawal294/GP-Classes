package com.harshit.dbms.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostName {
	
	public static String getHost() {
		
		String hostPort;
		try {
			String result = InetAddress.getLocalHost().getHostName();
		    if (result.contains("LAPTOP")) {
		        hostPort="http://localhost:8080/";
		    }else {
		    	hostPort="https://gpclasses.herokuapp.com/";
		    }
		} catch (UnknownHostException e) {
			hostPort="https://gpclasses.herokuapp.com/";
		}
		return hostPort;
	}

}
