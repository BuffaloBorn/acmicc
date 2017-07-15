package com.epm.acmi.cm.et;

import java.util.Date;

import org.apache.log4j.Logger;

import com.epm.acmi.util.DateUtility;

/**
 * Class that schedules the Data Puller to run every X seconds
 * 
 * @author Dragos Mandruleanu
 */
public class EventTrackerTimer extends Thread {

	private static EventTrackerTimer ett;


	private static Logger log = Logger.getLogger("EventTrackerTimer.class");


	private DataPuller dp;


	private long sleepTime; // in seconds


	private EventTrackerTimer(long sleepTime) {
		this.dp = new DataPuller();
		// comes in as seconds, convert to millis
		this.sleepTime = 1000 * sleepTime;
	}


	public static synchronized EventTrackerTimer getEventTrackerTimer(long sleepTime) {
		if (ett == null) {
			ett = new EventTrackerTimer(sleepTime);
		}

		return ett;
	}


	public void run() {
		try {
			while (true) {
				log.info("Start Event Tracker " + DateUtility.convertDateToLocalTimeString(new Date(System.currentTimeMillis())));
				dp.getDataFromStellentDB();
				log.info("Stop Event Tracker " + DateUtility.convertDateToLocalTimeString(new Date(System.currentTimeMillis())));
				Thread.sleep(this.sleepTime);

			}
		} catch (Exception e) {
			// If an error occurs this thread need to restart again after logging the erro
			log.error("Exception", e);
			run();

		}

	}

}
