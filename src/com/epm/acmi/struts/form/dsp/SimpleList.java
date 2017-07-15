package com.epm.acmi.struts.form.dsp;

import java.util.Arrays;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.model.ListDataModel;

public class SimpleList implements ListDataModel {
    
    /**
     * Collection with DisplayDocument-Objects (=Rows) for ListControl
     * Author: Dragos
     */
    private SimpleType[] data = new SimpleType[0];

    
    // -------------------------------------------------
    //                    Methods
    // -------------------------------------------------

    /**
     * Constructor DisplayDocumentList
     * @param   elements    List with Display-Objects
     */
    public SimpleList (SimpleType[] elements) {
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
        return data[index].getKey();
    }

    /**
     * Sorts the DisplayDocumentList for the given Column     
     * @param column        Property wich defines the Column
     * @param direction     Sortdirection ASC, DESC
     */ 
    public void sortByColumn(String column, SortOrder direction) {
        Arrays.sort(data, new DocumentSortComparator(column, direction));
    }


}
