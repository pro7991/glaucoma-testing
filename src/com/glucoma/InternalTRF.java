package com.glucoma;

import java.io.IOException;

import com.glucoma.creater.TRFInternalCreater;

public class InternalTRF {

	public static void main(String[] args) throws IOException, InterruptedException {
		TRFInternalCreater creater = new TRFInternalCreater("trf-internal-user.xlsx");
		creater.start();
//		TRFInternalUpdater updater = new TRFInternalUpdater("trf-internal-user.xlsx");
//		updater.start();
	}

}
