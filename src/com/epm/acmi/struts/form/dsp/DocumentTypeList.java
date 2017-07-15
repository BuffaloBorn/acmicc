package com.epm.acmi.struts.form.dsp;

import java.util.Arrays;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.model.ListDataModel;

/**
 * Document Type List used to build a document list and display it in Documents tab.
 * @author Jay Hombal
 *
 */
public class DocumentTypeList implements ListDataModel {

	/**
	 * Collection with DisplayDocument-Objects (=Rows) for ListControl
	 */
	private DocumentType[] data = new DocumentType[0];

	// -------------------------------------------------
	// Methods
	// -------------------------------------------------

	/**
	 * Constructor DisplayDocumentList
	 * 
	 * @param elements
	 *            List with Display-Objects
	 */
	public DocumentTypeList(DocumentType[] elements) {
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
	 * The ListControl needs a unique Key if the Details of a Row (= User)
	 * should be displayed. In this case our Key only contains the UserId, which
	 * is also the PrimaryKey in the Database. If you have a composite Key you
	 * can return something like this Attribute1 + '.' + Attribute2 +....
	 * 
	 * @see com.cc.framework.ui.model.ListDataModel#getUniqueKey(int)
	 */
	public String getUniqueKey(int index) {
		return data[index].getDocCode();
	}

	/**
	 * Sorts the DisplayDocumentList for the given Column
	 * 
	 * @param column
	 *            Property wich defines the Column
	 * @param direction
	 *            Sortdirection ASC, DESC
	 */
	public void sortByColumn(String column, SortOrder direction) {
		Arrays.sort(data, new DocumentSortComparator(column, direction));
	}

	/**
	 * Return docType for a given docType
	 * 
	 * @param docCode
	 * @return String
	 */
	public String getDocType(String docCode) {
		String docType = null;
		for (int i = 0; i < data.length; i++) {
			DocumentType dt = data[i];
			if (dt.getDocCode().equals(docCode)) {
				docType = dt.getDocType();
			}
		}
		return docType;

	}

}
