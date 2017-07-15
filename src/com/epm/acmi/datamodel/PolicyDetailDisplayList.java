package com.epm.acmi.datamodel;

import java.util.Arrays;

import com.cc.acmi.presentation.dsp.PolicyDetailsDsp;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.model.ListDataModel;

/**
 * PolicyDiaryList is used to display Policy Diary List from web service
 * 
 * @author usacarl
 */
public class PolicyDetailDisplayList implements ListDataModel {

	/**
	 * Collection with UserDsp-Objects (=Rows) for our ListControl
	 */
	private PolicyDetailsDsp[] data = new PolicyDetailsDsp[0];


	// -------------------------------------------------
	// Methods
	// -------------------------------------------------

	/**
	 * Constructor for UserDisplayList.
	 */
	public PolicyDetailDisplayList() {
		super();
	}

	
	
	/**
	 * Constructor UserDisplayList
	 * @param	elements	List with Display-Objects
	 */
	public PolicyDetailDisplayList(PolicyDetailsDsp[] elements) {
		data = elements;
	}
	
	public Object getElementAt(int index) {
		return data[index];
	}

	/**
	 * The ListControl needs a unique Key if the
	 * Details of a Row (= User) should be displayed.
	 * In this case our Key only contains the UserId,
	 * which is also the PrimaryKey in the Database.
	 * If you have a composite Key you can return
	 * something like this Attribute1 + '.' + Attribute2 +....
	 * 
	 * @see com.cc.framework.ui.model.ListDataModel#getUniqueKey(int)
	 */
	public String getUniqueKey(int index) {
		return data[index].getEventid();
	}
	/**
	 * @see com.cc.framework.ui.model.ListDataModel#size()
	 */
	public int size() {
		return data.length;
	}


	public void sortByColumn(String column, SortOrder direction) {
		Arrays.sort(data, new PolicyDiaryListColumnComparator(column, direction));
	}

}
