package com.glucoma.utils;

import java.io.InputStream;
import java.util.Properties;

import com.glucoma.creater.Environment;

public class ConfigReader {
	
	public static String avelinoLoginUrl;
	public static String avelinoUsername;
	public static String avelinoPassword;

	public static String avagenLoginUrl;
	public static String avagenUsername;
	public static String avagenPassword;

	public static void load(Environment env) {
		try {
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);

            switch (env) {
			case QA:
				avelinoLoginUrl = prop.getProperty("qa.avellinoaccess.login.url");
				avelinoUsername = prop.getProperty("qa.avellinoaccess.login.username");
				avelinoPassword = prop.getProperty("qa.avellinoaccess.login.password");
				
				avagenLoginUrl = prop.getProperty("qa.avagen.login.url");
				avagenUsername = prop.getProperty("qa.avagen.login.url");
				avagenPassword = prop.getProperty("qa.avagen.login.url");
				break;
			case UAT:
				avelinoLoginUrl = prop.getProperty("uat.avellinoaccess.login.url");
				avelinoUsername = prop.getProperty("uat.avellinoaccess.login.username");
				avelinoPassword = prop.getProperty("uat.avellinoaccess.login.password");
				
				avagenLoginUrl = prop.getProperty("uat.avagen.login.url");
				avagenUsername = prop.getProperty("uat.avagen.login.url");
				avagenPassword = prop.getProperty("uat.avagen.login.url");
				break;
			default:
				break;
			}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
