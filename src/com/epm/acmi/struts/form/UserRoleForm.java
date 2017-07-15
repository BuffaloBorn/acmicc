package com.epm.acmi.struts.form;

import java.util.HashMap;
import java.util.Map;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * User Role Form
 * @author jay Hombal
 *
 */
public class UserRoleForm extends FWActionForm
{

    private static final long serialVersionUID = 325435435L;

    private HashMap roleMap;
    
    private Map userMap;
    
    private String selectedRole;
    
    private String selectedUser;
    
    public String getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserRoleForm()
    {
    }

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public HashMap getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(HashMap roleMap) {
		this.roleMap = roleMap;
	}

	public Map getUserMap() {
		return userMap;
	}

	public void setUserMap(Map userMap) {
		this.userMap = userMap;
	}
}
