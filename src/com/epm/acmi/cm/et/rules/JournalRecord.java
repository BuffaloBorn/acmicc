/**
 * 
 */
package com.epm.acmi.cm.et.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cesar Cardenas (d446)
 * 
 */
public class JournalRecord {
	/**
	 * Public Constants
	 */

	
	/**
	 * A synchronized map for tracking journal records. 
	 * Needs to be synchronized since it is used by multiple threads within
	 * the servlet engine.
	 */
	public static Map journalMap = Collections.synchronizedMap(new HashMap());
	/**
	 * Failure States
	 */
	public static final int PROCESSED_SUCCESSFULLY = 0;

	public static final int DUPLICATE_GFID = 1;

	public static final int ACMI_UPDATE_FAILED = 2;

	/**
	 * Private Attributes
	 */

	/**
	 * This id will be the EPMEvents table recordId.
	 * Default is "" (No record id)
	 */
	private long id = 0;

	/**
	 * failureState represents the failure mode. 
	 * Default is successful processing.
	 */
	private int failureState = PROCESSED_SUCCESSFULLY;

	//
	
	/**
	 * moveEventsTableContents is self-explanatory
	 * Default goes in accordance with failureState default,
	 * which is to move the event to ProcessedEPMEvents
	 * on successful processing
	 */
	private boolean moveEventTableContents = true;

	/**
	 * Getters and Setters
	 */

	public int getFailureState() {
		return failureState;
	}

	public void setFailureState(int failureState) {
		if (failureState >= PROCESSED_SUCCESSFULLY && failureState <= ACMI_UPDATE_FAILED)
			this.failureState = failureState;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getMoveEventTableContents() {
		return moveEventTableContents;
	}

	public void setMoveEventTableContents(boolean moveEventTableContents) {
		this.moveEventTableContents = moveEventTableContents;
	}
}
