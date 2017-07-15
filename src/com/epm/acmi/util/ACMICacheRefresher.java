package com.epm.acmi.util;


/**
 * 
 * Class that refreshes the ACMI Cache every X seconds
 * 
 * @author Dragos Mandruleanu
 *
 */
public class ACMICacheRefresher extends Thread {	
							
 	private long sleepTime = 25000; // in seconds
	
	
	public ACMICacheRefresher(long sleepTime) {
		
		// comes in as seconds, convert to millis
		this.sleepTime = 1000 * sleepTime;
	}
	
	
	
	public void run() {
             
        try {     
	    	while (true) {	    
	    		ACMICache.refreshCache();		
	    		Thread.sleep(this.sleepTime);
	    	}
    	}
   		catch (Exception e) {
   			e.printStackTrace();	
   		}
    	         
    }	
	
}

