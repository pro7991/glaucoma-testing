package com.glucoma;

import java.io.IOException;

import com.glucoma.creater.TRFExternalCreater;

public class ExternalTRF {

	public static void main(String[] args) throws IOException, InterruptedException {
		TRFExternalCreater creater = new TRFExternalCreater("trf-external-user.xlsx");
		creater.start();
	}

}
