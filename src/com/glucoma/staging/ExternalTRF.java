package com.glucoma.staging;

import java.io.IOException;
import java.net.URL;

import com.glucoma.creater.Environment;
import com.glucoma.creater.TRFExternalCreater;

public class ExternalTRF {
	protected String loginUrl;
	protected String username;
	protected String password;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		TRFExternalCreater creater = new TRFExternalCreater(getParentPath() + "trf-external-user.xlsx", Environment.STAGING);
		creater.start();
	}

	private static String getParentPath() {
		URL location = new InternalTRF().getClass().getResource("ExternalTRF.class");
        String ref = location.getPath();
        return ref.substring(0, ref.lastIndexOf("/")+1);
	}
}
