<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ page import="com.cc.acmi.presentation.taglib.TagUtils"%>
<html>
	<head>
		<meta name="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="Copyright" content="Copyright (c) 2003 - 2004 SCC Informationssysteme GmbH">
		<meta name="Title" content="Common Controls Sample Application">
		<meta name="Author" content="SCC Informationssysteme GmbH - www.scc-gmbh.com">
		<meta name="Language" content="ENGLISH">
		<meta name="Robots" content="NOINDEX">
		<%-- Title --%>
		<title>

		</title>
		<util:base />
		<%-- Framework Includes --%>
		<util:jsp directive="includes" />
	</head>

	<script language="Javascript">

function reAssignClick() {
	var role = userRoleForm.selectedRole.value;
	var user = userRoleForm.selectedUser.value;
	var selIndex = userRoleForm.selectedUser.selectedIndex;
	var userName;
	var retVal;
	
	if (role == "" && role.length ==0 &&
		user == "" && user.length ==0) 
	{
		return;
	}
	
	if (user != null && user.length != 0) {
		userName = userRoleForm.selectedUser.options[selIndex].text;
		retVal = confirm("Are you certain you want to reassign the task to user [" + userName + "]?");
	}
	else if (role != null && role.length != 0) {				
		retVal = confirm("Are you certain you want to reassign the task to role [" + role + "]?");			
	}
	
	if (retVal == true) {	
		userRoleForm.submit();
		window.opener.resubmitReassignWindow();		
		window.close();
	}
}

function cancelClick() {
	window.close()
}

</script>
	<body bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" onLoad="init();">


		<html:form action="/userRoleAssign">

			<forms:form type="edit" caption="userRoleAssign.title" name="userRoleForm" formid="userRoleForm">
				
				<forms:row colspan="2">
					<forms:html>
						To reassign a task to a role, select a role and click the 'Reassign' button.						
						<br>
						To reassign a task to a user, select a user and click the 'Reassign' button.					
					</forms:html>
				</forms:row>
				<forms:row>
					<forms:section title="userRoleAssign.Roles">
						<forms:select size="10" width="205" property="selectedRole">
							<base:options property="roleMap" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:section>
					<forms:section title="userRoleAssign.Users">
						<forms:select size="10" width="205" property="selectedUser" required="false">
							<base:options property="userMap" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:section>
				</forms:row>
				<forms:row colspan="2">
					<forms:buttonsection align="left" default="btnReassign">
						<forms:button name="btnReassign" onclick="reAssignClick();" text="button.title.reassign" />
						<forms:cancel name="btnCancel" onclick="cancelClick();" text="button.title.cancel" />
					</forms:buttonsection>
				</forms:row>
			</forms:form>


		</html:form>
	</body>
</html>
<util:jsp directive="endofpage" />
