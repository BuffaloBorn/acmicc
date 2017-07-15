package com.cc.acmi.service.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epm.acmi.cm.et.EventTrackerTimer;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.ACMICacheRefresher;
import com.epm.acmi.util.LocalProperties;

/**
 * Start Up Servlet initializes the cache and starts event tracker and cache refresh thread
 * 
 * @author Jay Hombal
 */
public class StartUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	private static Logger log = Logger.getLogger(StartUpServlet.class);


	public StartUpServlet() {
	}


	/**
	 * init method build ACMI cache and starts cache refresh thread and event tracker thread
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		long timeInterval;
		long cacheInterval;
		String timeIntString;
		String cacheIntString;

		ACMICacheRefresher cacheRefresher;

		// Wait until BPM is up
		try {
			log.info("Waiting 30 secs for BPM to start....");
			Thread.sleep(30000);
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
			e.printStackTrace();
		}

		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}
		log.info("ACMIIUA Initializing ....");
		ACMICache acmiCache = new ACMICache();
		try {
			String AAID = null;
			
			try {
				AAID = StellentClient.login();
				
				if (AAID != null) {

					String docTypeSearchName = LocalProperties.getProperty("doc_type_search");
					String docTypeSearchFieldName = LocalProperties.getProperty("doc_typ_search_field");
					String docTypeSearchFieldValue = LocalProperties.getProperty("doc_typ_search_field_value");

					log.info("Loading Stellent Search Prompt Types...");
					acmiCache.loadStellentSearchPromptCache(AAID);
					log.info("Loading Stellent Search Prompt Types...");

					log.info("Loading Stellent Doc Types...");
					acmiCache.loadStellentDocumentTypes(AAID, docTypeSearchName, docTypeSearchFieldName, docTypeSearchFieldValue);
					log.info("Loading Stellent Doc Types done...");
				}

				log.info("Logout of Stellent session...");
			} 
			catch (Exception e)
			{
				log.debug("Exception " + e.getClass().getName() + " thrown while trying to login to Stellent");
			}
			finally
			{
				if (AAID != null)
					StellentClient.logout(AAID);				
			}

			log.info("Loading code tables from database...");
			ACMICache.loaDBdCodeTables();
			log.info("Loading code tables from database done...");

			log.info("Building Active Directory Cache...");
			acmiCache.loadADUsersAndGroup();
			log.info("Active Directory Cache built....");

			log.info("ACMIIUA Initializing done....");
		} catch (Exception ex) {
			log.error("Stellent Initialization Failed", ex);
		}

		log.info("Starting the Event tracker thread");
		timeIntString = LocalProperties.getProperty("timeInterval");

		if (timeIntString == null || timeIntString.equals("")) {
			timeInterval = 300;
		} else {
			timeInterval = Long.parseLong(timeIntString);
		}

		String runET = LocalProperties.getProperty("RunEventTracker");
		String runCR = LocalProperties.getProperty("RunCacheRefresh");

		log.info("Event Tracker Switch " + runET);

		if (runET.trim().equalsIgnoreCase("Yes")) {

			log.info("Using time interval of " + timeInterval + " secs");
			EventTrackerTimer ett = EventTrackerTimer.getEventTrackerTimer(timeInterval);
			ett.start();
			log.info("Event tracker thread has been started");

		} else {
			log.info("EVENT TRACKER WILL NOT RUN as RunEventTracker Switch is set to No");
		}

		log.info("Cache Refresh Switch " + runCR);

		if (runCR.trim().equalsIgnoreCase("Yes")) {
			cacheIntString = LocalProperties.getProperty("cacheRefreshInterval");
			if (cacheIntString == null || cacheIntString.equals("")) {
				cacheInterval = 3600;
			} else {
				cacheInterval = Long.parseLong(cacheIntString);
			}
			log.info("Starting the Acmi Cache Refresher thread");
			log.info("Using cache refresh interval of " + cacheInterval + " secs");
			cacheRefresher = new ACMICacheRefresher(cacheInterval);
			cacheRefresher.start();
			log.info("Cache Refresher thread has been started");
		} else {
			log.info("ACMI CACHE REFRESH WILL NOT RUN as RunCacheRefresh Switch is set to No");
		}
		
		log.info("Building Standard Event Codes Cache...");
		ACMICache.loaDStdEventCodes();
		log.info("Standard Event Codes Cache built....");
		
		log.info("Building Standard Event Codes and Descriptions Cache...");
		ACMICache.loaDStdEventCodesDescription();
		log.info("Standard Event Codes and Descriptions Cache built....");
		
		log.info("Building Condition Codes Cache...");
		ACMICache.loaDConditionCodes();
		log.info("Condition Codes Cache built....");
		
		log.info("Building Sub Standard Reason Cache...");
		ACMICache.loaDSubStandardReason();
		log.info("Standard Sub Standard Reason and Descriptions Cache built....");
		
		log.info("Building Under Writer Status Codes Cache...");
		ACMICache.loaDUnderWriterStatusCodes();
		log.info("Under Writer Status Codes Cache built....");
		
		log.info("Building Memo Id Codes Cache...");
		ACMICache.loaDMemoIdCodes();
		log.info("Under Memo Id Codes Cache built....");
		
		log.info("Building Person Types Cache...");
		ACMICache.loaDPersonTypesCodes();
		log.info("Under Person Types Codes Cache built....");
		
		log.info("Building Person Status Cache...");
		ACMICache.loaDPersonStatusCodes();
		log.info("Under Person Status Codes Cache built....");

	}


	public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) {
	}

	static {
		log = Logger.getLogger(com.cc.acmi.service.servlet.StartUpServlet.class);
	}
}
