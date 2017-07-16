package com.cc.acmi.presentation.dsp;

import java.math.BigDecimal;

import com.cc.framework.common.DisplayObject;

public class PolicyEventsDsp implements DisplayObject {
	   private BigDecimal EVENT_ID;

	    private String STD_EVENT_ID;

	    private String STD_EVENT_STATUS;

	    private String EVENT_DESCRIPTION;

	    private String DATE_CREATED;

	    private String DATE_COMPLETED;

	    private String EVENT_PERSON;

	    private String USER_ID;
	    
	    private String SCRNAME;
	    
	    private boolean columnMode;
	    
	    private boolean editMode;
	    

		public PolicyEventsDsp() {
			super();
			// TODO Auto-generated constructor stub
		}

		public BigDecimal getEVENT_ID() {
			return EVENT_ID;
		}

		public void setEVENT_ID(BigDecimal event_id) {
			EVENT_ID = event_id;
		}

		public String getSTD_EVENT_ID() {
			return STD_EVENT_ID;
		}

		public void setSTD_EVENT_ID(String std_event_id) {
			STD_EVENT_ID = std_event_id;
		}

		public String getSTD_EVENT_STATUS() {
			return STD_EVENT_STATUS;
		}

		public void setSTD_EVENT_STATUS(String std_event_status) {
			STD_EVENT_STATUS = std_event_status;
		}

		public String getEVENT_DESCRIPTION() {
			return EVENT_DESCRIPTION;
		}

		public void setEVENT_DESCRIPTION(String event_description) {
			EVENT_DESCRIPTION = event_description;
		}

		public String getDATE_CREATED() {
			return DATE_CREATED;
		}

		public void setDATE_CREATED(String date_created) {
			DATE_CREATED = date_created;
		}

		public String getDATE_COMPLETED() {
			return DATE_COMPLETED;
		}

		public void setDATE_COMPLETED(String date_completed) {
			DATE_COMPLETED = date_completed;
		}

		public String getEVENT_PERSON() {
			return EVENT_PERSON;
		}

		public void setEVENT_PERSON(String event_person) {
			EVENT_PERSON = event_person;
		}

		public String getUSER_ID() {
			return USER_ID;
		}

		public void setUSER_ID(String user_id) {
			USER_ID = user_id;
		}

		public boolean isColumnMode() {
			return columnMode;
		}

		public void setColumnMode(boolean columnMode) {
			this.columnMode = columnMode;
		}

		public boolean isEditMode() {
			return editMode;
		}

		public void setEditMode(boolean editMode) {
			this.editMode = editMode;
		}

		public String getSCRNAME() {
			return SCRNAME;
		}

		public void setSCRNAME(String scrname) {
			SCRNAME = scrname;
		}

		

		


}
