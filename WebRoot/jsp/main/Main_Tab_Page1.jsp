<%@ page import="com.epm.acmi.struts.Constants" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ page import="java.util.ArrayList"%>

<script>

var gChangesWereMade = false;
var gSaveClicked = false;
var gTabClick = false;
var gValidationFirst = false;

function runUnloadValidation() 
{
	
	if (gChangesWereMade == true && gSaveClicked == false) {
		 	event.returnValue = ("Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or click 'Cancel' to return to this page to save the changes made.");
		 	
		 	
		 		gTabClick =false;
		 		gValidationFirst=true;
		 	
	}
}

function runUnloadCloseIasDiary()
{
	if (iasdiary && gTabClick == false)
	{
		event.returnValue =("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok \nIf you do not want to go to worklist click on cancel")
	}
}

function runCloseIasDiaryUnload()
{
	if(gTabClick == false)
	{
		if (gValidationFirst == true)
		{
			alert("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok");
		}
		
		runIasdiary();
		closeIasdiary();
	}
}

</script>

<body onbeforeunload="runUnloadValidation(); runUnloadCloseIasDiary()" onunload="runCloseIasDiaryUnload()" >

<table width="100%">
	<tr>
		<td class="legend" align="right" height="8" width="100%">
		<% if (session.getAttribute(Constants.keyAppFN) != null) { %>
			Key Applicant : <b><%= session.getAttribute(Constants.keyAppFN)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<%}%>
		<% if (session.getAttribute(Constants.taskName) != null) { %>
			Activity Name : <b><%= session.getAttribute(Constants.taskName)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<%}%>
		<% if (session.getAttribute(Constants.policyNumber) != null) { %>
			Policy Number: <b><%= session.getAttribute(Constants.policyNumber)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<%}%>
		Back to <a href="main/secondarytabsetBrowse.do?ctrl=secondarymaintabset&action=TabClick&param=tab5">WorkList</a></td>
	</tr>
	<tr>
		<td height="98%"><ctrl:tabset styleId="tabset2" name="nestedtabset"
			action="main/iuauser" width="100%" runat="server" bgcolor="#DADFE0">


			<ctrl:tab tabid="tab11" title="tabset11.tab11.title"
				content="/jsp/main/Tab_Page11.jsp"
				tooltip="tabset11.tab11.title.tooltip" onclick="gTabClick=true" />

			<ctrl:tab tabid="tab12" title="tabset11.tab12.title"
				content="/jsp/nbws/AppDocs.jsp" 
				tooltip="tabset11.tab12.title.tooltip" onclick="gTabClick=true"/>

			<ctrl:tab tabid="tab13" title="tabset11.tab13.title"
				content="/jsp/cms/UploadDocumentContent.jsp"
				tooltip="tabset11.tab13.title.tooltip" onclick="gTabClick=true">
				
			</ctrl:tab>
			
			<%  
				if (session.getAttribute(Constants.policyNumber) != null)	
				{
					String policyno = ((String) session.getAttribute(Constants.policyNumber)).trim();
			
					if(policyno.length() != 0 && (!policyno.equalsIgnoreCase("0")) )
					{
			%> 
			 	<ctrl:tab tabid="tab6"	title="tabset1.tab6.title" tooltip="tabset1.tab6.tooltip" onclick="javascript:return runIasdiary();"/>
			
			<%	
					}
				} %> 
			
			 <!-- USR 8399-1 changes -->
			<%  ArrayList caseNotes = (ArrayList) pageContext.getSession().getAttribute("caseNotesDataStatus");
		      	if(caseNotes.get(0).equals("0")) {%> 
				<ctrl:tab tabid="tab14" title="tabset11.tab14.title" content="/jsp/nbws/CaseNotesContent.jsp" 
				tooltip="tabset11.tab14.title.tooltip" onclick="gTabClick=true" />
		    <% } else {  %>
       			<ctrl:tab tabid="tab14" title="tabset11.tab14.title.star" content="/jsp/nbws/CaseNotesContent.jsp" 
				tooltip="tabset11.tab14.title.tooltip" onclick="gTabClick=true" />
		    <!--  End of USR 8399-1 changes -->
			<% } %>
		</ctrl:tabset></td>
	</tr>
</table>

</body>
