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
            
            String evnPrefix = env.toString().toLowerCase();
            //#AvellinoAccess Glaucoma:
            avelinoLoginUrl = prop.getProperty(evnPrefix + ".avellinoaccess.login.url");
			avelinoUsername = prop.getProperty(evnPrefix + ".avellinoaccess.login.username");
			avelinoPassword = prop.getProperty(evnPrefix + ".avellinoaccess.login.password");
			//#Avagen Glaucoma
			avagenLoginUrl = prop.getProperty(evnPrefix + ".avagen.login.url");
			avagenUsername = prop.getProperty(evnPrefix + ".avagen.login.username");
			avagenPassword = prop.getProperty(evnPrefix + ".avagen.login.password");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
