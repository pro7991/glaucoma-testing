package com.glucoma.staging;

import java.io.IOException;
import java.net.URL;

import com.glucoma.creater.Environment;
import com.glucoma.creater.TRFInternalCreater;

public class InternalTRF {

	public static void main(String[] args) throws IOException, InterruptedException {
		TRFInternalCreater creater = new TRFInternalCreater(getParentPath() + "trf-internal-user.xlsx", Environment.STAGING);
		creater.start();
//		TRFInternalUpdater updater = new TRFInternalUpdater("trf-internal-user.xlsx");
//		updater.start();
	}

	private static String getParentPath() {
		URL location = new InternalTRF().getClass().getResource("InternalTRF.class");
        String ref = location.getPath();
        return ref.substring(0, ref.lastIndexOf("/")+1);
	}

}
