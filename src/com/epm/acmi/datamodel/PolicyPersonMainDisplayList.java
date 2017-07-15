package com.epm.acmi.datamodel;

import java.util.ArrayList;
import java.util.Arrays;

import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.util.StringHelp;
import com.epm.acmi.bean.PolicyPersonMainBean;

public class PolicyPersonMainDisplayList extends ArrayList implements ListDataModel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	private final static String ROW_PREFIX = "row_";
	
	/**
	 * Collection with deleted objects/rows
	 */
	private ArrayList deleted = new ArrayList();
	
	/**
	 * Internal key for the editable row
	 */
	private  String key = null;
	
	/**
	 * Copy of the current editable row
	 */
	private Object copy = null;  
	
	// -------------------------------------------------
	//                    Methods
	// -------------------------------------------------
	
	/**
	 * Constructor
	 * @param	elements	List with Display-Objects
	 */
	public PolicyPersonMainDisplayList(PolicyPersonMainBean[] elements) {
		addAll(Arrays.asList(elements));
	}
	
	
	/**
	 * @see com.cc.framework.ui.model.ListDataModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return get(index);
	}

	
	/**
	 * @see com.cc.framework.ui.model.ListDataModel#getUniqueKey(int)
	 */
	public String getUniqueKey(int index) {
		return ROW_PREFIX + index;
	}
	
	/**
	 * Returns an internal index to get a row from
	 * the datampdel
	 * 
	 * @param uniqueKey The unique key
	 * @return	Index
	 */
	protected int getIndex(String uniqueKey) {
		String index = StringHelp.split(uniqueKey, "_")[1];
		return Integer.parseInt(index); 
	}

	
	
	/**
	 * Starts check rider status 
	 * @param key	Index of the row to edit
	 */	
	public void beginCheckRiderStatus(String key) {
		
		this.key = key;
		
		int index = getIndex(key);
		
		// save original data
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		if(data.isColumnMode())
			data.setRiderStatus(true);
		else
			data.setRiderStatus(false);	
		
		copy =  data.clone();
		
		//this.bean = bean;
	}
	
	
	/**
	 * Starts editing 
	 * @param key	Index of the row to edit
	 */	
	public void beginnEdit(String key) {
		
		this.key = key;
		
		int index = getIndex(key);
		
		// save original data
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		data.setEditable(true); 
		copy =  data.clone();
		
		
		//this.bean = bean;
	}
	
	/**
	 * Applay changes
	 */
	public void applyEdit() {

		int index = getIndex(this.key);
		
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		data.setEditable(false);

		copy = null;
	}
	
	/**
	 * Applay changes
	 */
	public PolicyPersonMainBean applyEditReturnRow() {

		int index = getIndex(this.key);
		
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		data.setEditable(false);

		copy = null;
		
		return data;
	}
	
	public PolicyPersonMainBean returnRowAt(String keyRow)
	{
		int index = getIndex(keyRow);
		
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		
		return data;
	}
	
	/**
	 * Cancel editing an restore original values
	 */
	public void cancelEdit() {

		int index = getIndex(this.key);
		
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		data.setEditable(false);

		if (data.isNew()) {
			remove(index);
		}
		
		if (data.isDirty()) {
			PolicyPersonMainBean oldData = (PolicyPersonMainBean) copy;
			
			if(oldData != null)
			{
				data.setTone(oldData.getTone());
				data.setTtwo(oldData.getTtwo());
				data.setTthree(oldData.getTthree());
				data.setTfour(oldData.getTfour());
				data.setTfive(oldData.getTfive());
				data.setPERSON_STATUS_IND(oldData.getPERSON_STATUS_IND());
				data.setSMOKER_IND(oldData.getSMOKER_IND());
				data.setPERSON_ID(oldData.getPERSON_ID());
				data.setPERSON_LEVEL_IND(oldData.getPERSON_LEVEL_IND());
				data.setPERSON_SEARCH_NAME(oldData.getPERSON_SEARCH_NAME());
				data.setPERSON_STATUS(oldData.getPERSON_STATUS());
				data.setPERSON_STATUS_START_DATE(oldData.getPERSON_STATUS_START_DATE());
				data.setRIDER_IND(oldData.getRIDER_IND());
			}
		}
		
		this.copy = null;
	}

	
	/**
	 * Deletes a single row
	 * @param key	Key of the row to delete
	 */
	public void delete(String key) {
		int index = getIndex(key);

		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		data.setDeleted();
		deleted.add(data);

		remove(index);
	}
	
	/**
	 * Returns the items which have been deleted from the list
	 * @return Collection which contains the items to delete from the database
	 */	
	public ArrayList getDeletedItems() {
		return this.deleted;
	}
	
	/**
	 * Returns true if the list is in check rider status
	 * @return <code>true</code> if the row has rider; <code>false</code> otherwise
	 */
	public boolean getCheckRiderStatus() {
		return (copy != null);
	}
	
	
	
	/**
	 * Returns true if the list is in editmode 
	 * @return <code>true</code> if the row is editable; <code>false</code> otherwise
	 */
	public boolean getEditable() {
		return (copy != null);
	}
	
	/**
	 * Returns true if the list is in editmode 
	 * @return <code>true</code> if the row is editable; <code>false</code> otherwise
	 */
	public String getRIDERSHOW() {
		int index = getIndex(this.key);
		PolicyPersonMainBean data = (PolicyPersonMainBean) getElementAt(index);
		
		return data.getRIDER_SHOW();
	}
	
	
	/**
	 * Adds a new row
	 * @param	The key for the new row  
	 */
	public String add(String key) {
		int newIndex = getIndex(key) + 1;
		
		// is an other row in edit modus?
		if (getEditable()) {
			cancelEdit();
		}
		
		PolicyPersonMainBean newBean = new PolicyPersonMainBean();
		newBean.setNew();
		
		this.add(newIndex, newBean);

		return ROW_PREFIX + newIndex;
	}
}
