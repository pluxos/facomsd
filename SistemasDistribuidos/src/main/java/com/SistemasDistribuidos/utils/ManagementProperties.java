package com.SistemasDistribuidos.utils;

import java.util.Properties;

/**
 * 
 * @author luizw
 *
 */
public class ManagementProperties {

	    Properties properties;
	    
	    public int getPort() {
	        return Integer.parseInt(properties.getProperty("port", "8080"));
	    }
	    
	    public void setPort(int port) {
	        properties.setProperty("port", port + "");
	    }

	    public String getAddress() {
	        return properties.getProperty("address");
	    }

}
