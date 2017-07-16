<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<%@ page import="com.cc.framework.ui.control.SimpleListControl"%>
<%@ page import="com.cc.framework.ui.model.ListDataModel"%>
<%@ page import="com.cc.framework.util.ListHelp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.epm.acmi.util.EPMHelper"%>

<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>


<script>
var submitcount=0;
window.onload = addherfListenersToSort;
window.onbeforeunload=runUnloadCloseIasDiary;
window.onunload=runCloseIasDiaryUnload;

function openRoleAssignWindow() 
{
	var theRole;

	if (selectedRole == 0) {
		alert ("Please select a Work Item to Reassign ");
	}
	else {
		theRole = ReassignTaskForm.roleDropdown.value;
		gTabClick = true;
		window.open("/acmicc/userRoleAssign.do?selectedRole=" +  theRole ,null,
					"resizable=yes,left=200,top=200,height=275,width=440,status=no,toolbar=no,menubar=no,location=no");			
	}
}

var selectedRole = 0;

function selectOnClick(theWidget) {				
	
	if (theWidget.checked == true) {
		selectedRole++;
	}
	else {
		selectedRole--;
	}		
}


function resubmitReassignWindow() 
{

		if (submitcount==0)
		{
			submitcount++;
			gTabClick = true;
			ReassignTaskForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
	


}

function getRole() {
	
	ReassignTaskForm.action.value='getrole';
		if (submitcount==0)
		{
			submitcount++;
			gTabClick = true;
			ReassignTaskForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
	
}

</script>

<body  onbeforeunload=" runUnloadCloseIasDiary()" onunload="runCloseIasDiaryUnload()">
<table width="100%" align="center">

	<tr>
		<td class="legend" align="right" height="8">
			Back to
			<a href="main/secondarytabsetBrowse.do?ctrl=secondarymaintabset&action=TabClick&param=tab5" onclick="setWorklistFlag()">
				WorkList
			</a>
		</td>
	</tr>

	<tr>

		<td width="100%" align="center">
			<html:form action="/main/reassign" >

				<forms:form type="edit" caption="Reassign.Tasks" formid="ReassignTaskForm" width="550" name="ReassignTaskForm">

					<input type=hidden name=action>
					
					<forms:select onchange="getRole()" label="Reassign.Tasks.IUAUser" property="item" style="color: navy" width="95%">
						<base:options property="map" keyProperty="key" labelProperty="value" filter="false" empty="Reassign.list.title" />
					</forms:select>
					
					<c:if test="${ReassignTaskForm.roleValues != null}" >
						<forms:select label="Reassign.title.role" property="roleDropdown" style="color: navy" width="95%" >
							<base:options property="roleValues" keyProperty="key" labelProperty="value" filter="false"  />
						</forms:select>
	
						<forms:buttonsection default="btnSearch">
							<forms:button name="btnSearch" text="button.title.Search" title="button.title.Search" onmouseup="gTabClick=true;"/>
						</forms:buttonsection>
					</c:if>
				</forms:form>

		</td>
	</tr>
</table>
<br />
<div align="center">
	<logic:present scope="session" name="ReassignTaskList">

		<ctrl:list action="/main/reassign2" scope="session" 
		name="ReassignTaskList" 
		title="Reassign.list.title" 
		rows="15" 
		style="bgcolor:'#EFEFEF'" 
		formElement="false" 
		refreshButton="false"
		select="single"	>

			<ctrl:columntext title="Reassign.Tasks.ActivityName" property="taskName" width="200" sortable="true" />
			<ctrl:columntext title="Reassign.Tasks.ApplicantName" property="keyApplicantName" width="200" sortable="true" />
			<ctrl:columntext title="Reassign.Tasks.State" property="state" width="65" sortable="true" />
				<ctrl:columntext title="Reassign.Tasks.PolicyNumber" property="policyNumber" width="95" sortable="true" />
			<ctrl:columntext title="Reassign.Tasks.CreatedDate" property="createdDateString" width="190" sortable="true" />
			<ctrl:columntext title="Reassign.Tasks.UnderWiter" property="underWriter" width="60" sortable="true" />
			<ctrl:columntext title="Reassign.title.role" property="role" width="60" sortable="true" />
			<ctrl:columntext title="Reassign.Tasks.Status" property="status" width="60" sortable="true" />
			<ctrl:columncheckbox editable="true" onclick="selectOnClick(this)" title="Reassign.Tasks.Select" property="checkState" width="50" />
		</ctrl:list>
	</logic:present>
</div>
<br />
	<ctrl:button name="btnReassign" text="button.title.reassign" title="button.title.reassign" onclick="openRoleAssignWindow()" />
</html:form>
</body>




