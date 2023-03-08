package com.glucoma;

import java.io.IOException;

import com.glucoma.creater.Environment;
import com.glucoma.creater.TRFExternalCreater;

public class ExternalTRF {
	protected String loginUrl;
	protected String username;
	protected String password;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		TRFExternalCreater creater = new TRFExternalCreater("trf-external-user.xlsx", Environment.UAT);
		creater.start();
	}

}
