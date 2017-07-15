package com.epm.acmi.datamodel;


import java.util.Arrays;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.model.ListDataModel;
import com.epm.acmi.bean.ReassignTaskBean;


/**
 * ReassignTaskList used to display reassign task list
 * @author usaho
 *
 */
public class ReassignTaskList implements ListDataModel {

	private ReassignTaskBean [] data = new ReassignTaskBean[0];


	// -------------------------------------------------
	//                    Methods
	// -------------------------------------------------

	/**
	 * Constructor for UserDisplayList.
	 */
	public ReassignTaskList() {
		super();
	}

	/**
	 * Constructor UserDisplayList
	 * @param	elements	List with Display-Objects
	 */
	public ReassignTaskList(ReassignTaskBean[] elements) {
		data = elements;
	}

	/**
	 * @see com.cc.framework.ui.model.ListDataModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return data[index];
	}

	/**
	 * @see com.cc.framework.ui.model.ListDataModel#size()
	 */
	public int size() {
		return data.length;
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
		long key = data[index].getTaskId();
		String keyString = Long.toString(key);
		return keyString;
	}

	public void sortByColumn(String column, SortOrder direction) {
		Arrays.sort(data, new ReassignTaskColumnComparator(column, direction));
	}
	
	
}
