package com.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;


public class PropertiesCache
{
	private final Properties configProp = new Properties();
	private static PropertiesCache  propertiesCache= null;

	private PropertiesCache() throws FileNotFoundException
	{
		//String homeDir = System.getProperty("user.home");
		InputStream in= new FileInputStream("E:\\New folder\\SBTSTRAVELSs\\src\\com\\resource\\data.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PropertiesCache getInstance() 
	{
		try {
			if (propertiesCache == null) {
				propertiesCache = new PropertiesCache();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return propertiesCache;
	}

	public String getProperty(String key){
		return configProp.getProperty(key);
	}

	public Set<String> getAllPropertyNames(){
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key){
		return configProp.containsKey(key);
	}
	

}
