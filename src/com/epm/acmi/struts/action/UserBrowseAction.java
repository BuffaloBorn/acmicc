package com.epm.acmi.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.UserRoleForm;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.LDAPUser;

/**
 * 
 * @author Rajeev Chachra
 *
 */
public class UserBrowseAction extends FWAction {

	private static Logger log;
	private static HashMap userNameUserIDMap;
	
	static {
		userNameUserIDMap = new HashMap();
		log = Logger.getLogger(com.epm.acmi.struts.action.UserBrowseAction.class);
	}

	/**
	 * @see com.cc.framework.adapter.struts.FWAction#doExecute(ActionContext)
	 */
	
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws IOException, ServletException {

		UserRoleForm form;
		LDAPUser userObj;
		String selectedRole;
		String userName;
		String userRole;
		HashMap rolesDispHash = new HashMap();
		HashMap usersDispHash;
		ArrayList userRoleList;
		HashMap userList;
		TreeMap sortedMap;
		
		log.debug("Begin doExecute()");

		form = (UserRoleForm) ctx.form();

		try {						

			selectedRole = form.getSelectedRole();
			log.info("selected role = " + selectedRole);

			if (selectedRole != null) {				
				rolesDispHash.put(selectedRole, selectedRole);
				
				usersDispHash = new HashMap();
				userList = ACMICache.getUsers();
				log.info("Cache user list size = " + userList.size());

				Object[] userArray = userList.values().toArray();
				for (int i = 0; i < userArray.length; i++) {
					userObj = (LDAPUser) userArray[i];
				
					userRoleList = userObj.getRoles();
					for (int j = 0; j < userRoleList.size(); j++) {
						userRole = (String) userRoleList.get(j);
		

						if (userRole.equals(selectedRole)) {
							userName = userObj.getLastName() + " " + userObj.getFirstName();
							usersDispHash.put(userName, userName);
							
							UserBrowseAction.userNameUserIDMap.put(userName, userObj.getUserId());
							break;
						}
					} // end inner for
				}// end outer for

				sortedMap = new TreeMap(usersDispHash);
				
				form.setUserMap(sortedMap);
				form.setSelectedRole(selectedRole);
				form.setSelectedUser("");
			}

			form.setRoleMap(rolesDispHash);
			ctx.session().setAttribute("userRoleForm", form);

		} catch (Throwable ex) {
			ctx.addGlobalError("error.mainError", "");
			ex.printStackTrace();
		}

		log.debug("End doExecute()");

		// Display the Page with the UserList
		ctx.forwardToInput();
	}

	
	/**
	 * Reassign button click event handler
	 * @param ctx
	 * @throws Exception
	 */
	public void reassign_onClick(FormActionContext ctx) throws Exception {

		UserRoleForm form = (UserRoleForm) ctx.form();
		String selectedRole = form.getSelectedRole();
		String selectedUser = form.getSelectedUser();
		String userID;

		log.debug("Begin reassign_onClick()");
		log.debug("selected role = " + selectedRole);
		log.debug("selected user = " + selectedUser);

		if (selectedUser != null && selectedUser.length() != 0) {
			userID = (String) UserBrowseAction.userNameUserIDMap.get(selectedUser);
			log.info("Setting selected userID = " + userID);
			ctx.session().setAttribute(Constants.reassignkey, "user" + userID);
		} else if (selectedRole != null && selectedRole.length() != 0) {
			log.info("Setting selected role");
			ctx.session().setAttribute(Constants.reassignkey, "role" + selectedRole);
		}

		log.debug("End  reassign_onClick()");
	}

}