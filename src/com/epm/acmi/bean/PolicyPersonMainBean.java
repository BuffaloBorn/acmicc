package com.epm.acmi.bean;

import com.cc.acmi.presentation.dsp.PolicyPersonMainListDsp;
import com.cc.framework.common.DirtyMarker;

public class PolicyPersonMainBean extends PolicyPersonMainListDsp implements DirtyMarker, Cloneable{

	
	/**
	 * indentifies if this row is editable
	 */
	private boolean editable = false;
	
	/**
	 * indentifies if this row is dirty  
	 */
	protected boolean dirtyMarker = false;

	/**
	 * indentifies if this row is new  
	 */
	protected boolean newMarker = false;

	/**
	 * indentifies if this row is deleted  
	 */
	protected boolean deleteMarker = false;
	
	/**
	 * indentifies if this smoker status has changed   
	 */
	protected boolean SmokerStatusMarker = false;
	
	/**
	 * indentifies if this smoker status has changed   
	 */
	protected boolean PersonStatusIndMarker = false;
	
	/**
	 * indentifies if this smoker status has changed   
	 */
	protected boolean PolicyPersonTypeCurrentMarker = false;
	
	/**
	 * indentifies if this rider status has changed   
	 */
	protected boolean riderStatus = false;
	
	
	// ------------------------------------------------
	//                Methods
	// ------------------------------------------------

	/**
	 * Default constructor
	 */
	public PolicyPersonMainBean() {
		super();
	}

	
	public PolicyPersonMainBean( PolicyPersonMainListDsp data ) {
		super(data.getTone(), data.getTtwo(), data.getTthree(), data.getTfour(), data.getTfive(), data.getPERSON_STATUS_IND(), data.getSMOKER_IND(),
				data.getPERSON_ID(),data.getPERSON_LEVEL_IND(),data.getPERSON_SEARCH_NAME(), data.getPERSON_STATUS(),
				data.getPERSON_STATUS_START_DATE(), data.getRIDER_IND(), data.isColumnMode(), data.getRIDER_SHOW());
	}

	
	public boolean isDeleted() {
		return deleteMarker;
	}

	public boolean isDirty() {
		return dirtyMarker;
	}

	public boolean isNew() {
		return this.newMarker; 
	}

	public boolean isSmokerStatus()
	{
		return this.SmokerStatusMarker;
	}
	
	public boolean isPersonStatusInd()
	{
		return this.PersonStatusIndMarker;
	}
	
	public boolean isPolicyPersonTypeCurrentMarker() {
		return PolicyPersonTypeCurrentMarker;
	}

	public void resetDeleted() {
		this.deleteMarker = false;
		
	}

	public void resetDirty() {
		this.dirtyMarker = false;
		
	}

	public void resetNew() {
		this.newMarker = false;
		
	}
	
	public void resetSmokerStatusMarker()
	{
		this.SmokerStatusMarker = false;
	}
	
	public void resetPersonStatusIndMarker()
	{
		this.PersonStatusIndMarker = false;
	}

	public void resetPolicyPersonTypeCurrentMarker() {
		this.PolicyPersonTypeCurrentMarker = false;
	}
	
	public void setDeleted() 
	{
		this.deleteMarker = true;	
	}

	public void setDirty() 
	{
		this.dirtyMarker = true; 
	}

	public void setSmokerStatusMarker()
	{
		this.SmokerStatusMarker = true;
	}
	
	public void setPersonStatusIndMarker()
	{
		this.PersonStatusIndMarker = true;
	}
	
	public void setPolicyPersonTypeCurrentMarker() {
		this.PolicyPersonTypeCurrentMarker = true;
	}
	
	public void setNew() 
	{
		this.newMarker = true;
		
	}

	// ------------------------------------------------
	//           Editable
	// ------------------------------------------------

	/**
	 * Returns true or false if the row should be editable
	 * @return boolean
	 */
	public boolean getEditable() {
		return editable;
	}

	/**
	 * Secifies if the row is editable
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	// ------------------------------------------------
	//           Rider Status
	// ------------------------------------------------

	/**
	 * Returns true or false if the row should be editable
	 * @return boolean
	 */
	public boolean getRiderStatus() {
		return riderStatus;
	}

	/**
	 * Secifies if the row is editable
	 * @param editable
	 */
	public void setRiderStatus(boolean riderStatus) {
		this.riderStatus = riderStatus;
	}
	// ------------------------------------------------
	//           Getter/Setter
	// ------------------------------------------------
	

	/**
	 * @param tone
	 */
	public void setTone(String newTone) {
		
		if(newTone.equalsIgnoreCase(" "))
		{
			newTone = "";
		}
		
		if (!newTone.equals(this.tone)) {
			setDirty();
			setPolicyPersonTypeCurrentMarker();
		}
		this.tone = newTone;
	}
	
	public void setTtwo(String newTtwo) {
		
		if(newTtwo.equalsIgnoreCase(" "))
		{
			newTtwo = "";
		}
		
		if (!newTtwo.equals(this.ttwo)) {
			setDirty();
			setPolicyPersonTypeCurrentMarker();
		}
		this.ttwo = newTtwo;
	}
	
	public void setTthree(String newTthree) {
		
		if(newTthree.equalsIgnoreCase(" "))
		{
			newTthree = "";
		}
		
		if (!newTthree.equals(this.tthree)){
			setDirty();
			setPolicyPersonTypeCurrentMarker();
		}
		this.tthree = newTthree;
	}
	
	public void setTfour(String newTfour) {
		if(newTfour.equalsIgnoreCase(" "))
		{
			newTfour = "";
		}
		
		
		if (!newTfour.equals(this.tfour)){
			setDirty();
			setPolicyPersonTypeCurrentMarker();
		}
		this.tfour = newTfour;
	}
	
	public void setTfive(String newTfive) {
		if(newTfive.equalsIgnoreCase(" "))
		{
			newTfive = "";
		}
		
		if (!newTfive.equals(this.tfive)){
			setDirty();
			setPolicyPersonTypeCurrentMarker();
		}
		this.tfive = newTfive;
	}
	
	public void setPERSON_STATUS_IND(String newPerson_status_ind) {
		if (!newPerson_status_ind.equals(this.PERSON_STATUS_IND)){
			setDirty();
			setPersonStatusIndMarker();
		}
		this.PERSON_STATUS_IND = newPerson_status_ind;
	}
	
	public void setSMOKER_IND(String newSmoker_ind) {
		if (!newSmoker_ind.equals(this.SMOKER_IND)){
			setDirty();
			setSmokerStatusMarker();
		}
		this.SMOKER_IND = newSmoker_ind;
	}
	
	public void setPERSON_ID(String newPerson_id) {
		if (!newPerson_id.equals(this.PERSON_ID)){
			setDirty();
		}
		this.PERSON_ID = newPerson_id;
	}
	
	public void setPERSON_LEVEL_IND(String newPerson_level_ind) {
		if (!newPerson_level_ind.equals(this.PERSON_LEVEL_IND)){
			setDirty();
		}
		this.PERSON_LEVEL_IND = newPerson_level_ind;
	}
	
	public void setPERSON_SEARCH_NAME(String newPerson_search_name) {
		if (!newPerson_search_name.equals(this.PERSON_SEARCH_NAME)){
			setDirty();
		}
		this.PERSON_SEARCH_NAME = newPerson_search_name;
	}
	
	public void setPERSON_STATUS(String newPerson_status) {
		if (!newPerson_status.equals(this.PERSON_STATUS)){
			setDirty();
		}
		this.PERSON_STATUS = newPerson_status;
	}
	
	public void setPERSON_STATUS_START_DATE(String newPerson_status_start_date) {
		if (!newPerson_status_start_date.equals(this.PERSON_STATUS_START_DATE)){
			setDirty();
		}
		this.PERSON_STATUS_START_DATE = newPerson_status_start_date;
	}
	
	public void setRIDER_IND(String newRider_ind) {
		if (!newRider_ind.equals(this.RIDER_IND)){
			setDirty();
		}
		this.RIDER_IND = newRider_ind;
	}
	
	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new PolicyPersonMainBean(new PolicyPersonMainListDsp(getTone(), getTtwo(), getTthree(), getTfour(), getTfive(), getPERSON_STATUS_IND(), getSMOKER_IND(),
				getPERSON_ID(),getPERSON_LEVEL_IND(),getPERSON_SEARCH_NAME(), getPERSON_STATUS(),
				getPERSON_STATUS_START_DATE(), getRIDER_IND(),isColumnMode(), getRIDER_SHOW()));	
	}


	
}
