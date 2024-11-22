package com.onsemi.cim.tarlac.triggers.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;


public class AppUtils {

	private static final Properties props = new Properties();
	
	public static void loadProperties() throws Exception {
		try {
			props.load(new FileInputStream("config/config.properties"));
			props.load(new FileInputStream("config/lastruntime.properties"));
			props.load(new FileInputStream("config/log.properties"));
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			System.out.println("Error: Failed to load TriggerFileCreator property file.");
		}
	}
	
	public static String getProperty (String property) {
		return (String) props.get(property);
	}

	
	public static void updateProperties(String propertyid, String currentdate) throws IOException
	{
		//WARNING : This method is only applicable for Property File that has one ID
		try {
			//Private constructor to restrict new instances
			FileOutputStream out = new FileOutputStream("config/lastruntime.properties");
			FileInputStream  in = new FileInputStream("config/lastruntime.properties");
			Properties props = new Properties();
	        props.load(in);
	        in.close();
	        props.setProperty(propertyid, currentdate);
	        props.store(out, null);
	        out.close();
		} catch (IOException e) {
			System.out.println("Error: Unable to update Property File.");
		}		     
	}
	
}
