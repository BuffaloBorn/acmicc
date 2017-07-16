<%--<%@ page import="com.epm.acmi.struts.form.SelectedActivity"%>--%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>

<script language="JavaScript">

	function showfilterbox(dl)
	{
		if (dl.options[dl.selectedIndex].value == "ActivityByState" || dl.options[dl.selectedIndex].value == "ActivityByUW"
		 || dl.options[dl.selectedIndex].value == "PolicyNumber"  || dl.options[dl.selectedIndex].value == "AgentNumber")
		{
			show("FilterValueId");
			show("filter");
			
		}else
		{
			hide("FilterValueId");
			hide("filter");
		}
		// clear previous value of filter
		el = document.getElementById("filter"); 
		el.value="";

	}
	
	

	
	function show(id)
	{
		el = document.getElementById(id); 
		  	el.style.display = "block"; 
	} 


	function hide(id)
	{
	    el = document.getElementById(id); 
	    el.style.display = "none"; 
	} 

	function refreshPage() {							
		//window.location.href="worklist.do";		
		//window.setTimeout("refreshPage()", refreshTime);
	}

	// mins * secs * millis	
	//var refreshTime = 5 * 60 * 1000;
</script>
<script for="onLoad">	 
	//window.setTimeout("refreshPage()", refreshTime);
</script>

<html:form action="/worklist">
	<forms:form formid="wrklst" caption="form.worklist.title" type="edit" width="920">
		<forms:html align="center">
		<Table>
			<tr>
				<td height="98%" valign="top" align="center" height="40">
					<forms:info width="100%">
						Activities assigned to your role are displayed below.  To be sure your work list is current, always refresh the work list by clicking the <b>
						Search/Refresh
						</b> button before selecting an activity.
			</forms:info>
				</td>
			</tr>

			<tr>
				<td height="98%" valign="top" align="center" height="40">
					<Table>
						<tr>
							<td class="legend">
								Activity Filters :
							</td>
							<td>
								<html:select name="SelectedActivity" onchange="showfilterbox(this)" property="selectedActivity">
									<base:option key="AllActivities">My All Activities</base:option>
									<base:option key="MyActiveActivities">My Active Activities</base:option>
									<base:option key="MyAcceptedActivities">My Accepted Activities</base:option>
									<!--  <base:option key="MyDeclinedActivities">My Declined Activities</base:option> -->
									<base:option key="ActivityByState">My Activities By State</base:option>
									<base:option key="ActivityByUW">My Activities By UnderWriter</base:option>
									<base:option key="PolicyNumber">By Policy Number</base:option>
									<!--  USR 8399-1 changes begins -->
									<base:option key="AgentNumber">My Activities by Agent Number</base:option>
									<!--  USR 8399-1 changes Ends -->
								</html:select>
							</td>
							<td class="legend">
								<Label id="FilterValueId" style="<c:if test="${SelectedActivity.filter != null}" >display:block</c:if><c:if test="${SelectedActivity.filter == null}" >display:none</c:if>">
									Value&nbsp;:
								</Label>
							</td>
							<td class="legend">
								<Input id="filter" type="text" name="filter" style="<c:if test="${SelectedActivity.filter != null}" >display:block</c:if><c:if test="${SelectedActivity.filter == null}" >display:none</c:if>" value="<c:out value="${SelectedActivity.filter}"/>"/>
							</td>
							<td class="legend">
								<html:submit value="Search / Refresh"></html:submit>
							</td>
						</tr>
					</Table>

					<br />

					<ctrl:list styleId="WLList" name="WLList" action="/worklist" title="task.WorkList" width="98%" rows="25">
						<!-- usr 7930 Changes--->
					    <ctrl:columntext title="task.eapp" property="taskEapp" width="45" sortable="true" />
					    <!-- End USR 7930 changes-->
						<ctrl:columntext title="task.name" property="taskName" width="275" sortable="true" />
						<ctrl:columndrilldown title="task.ka_flname" property="keyApplicantName" width="270" sortable="true" />
						<ctrl:columntext title="task.ka_state" property="state" width="45" sortable="true" />
						<ctrl:columntext title="task.listBill" property="listBill" width="75" sortable="true" />
						<ctrl:columntext title="task.policy" property="policyNumber" width="100" sortable="true" />
						<ctrl:columntext title="task.createddate" property="createdDateString" width="392" sortable="true" />
						<!-- usr 8399-1 Changes--->
					    <ctrl:columntext title="task.agent" property="taskAgent" width="55" sortable="true" />
					    <!-- End USR 8399-1 changes-->
						<ctrl:columntext title="task.underWriter" property="underWriter" width="35" sortable="true" />
						<ctrl:columntext title="task.status" property="status" width="60" sortable="true" />
						
					</ctrl:list>
				</td>
			</tr>
		</table>
		</forms:html>
	</forms:form>
</html:form>
