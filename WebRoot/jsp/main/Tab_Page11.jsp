<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ page import="java.util.ArrayList"%>

<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>
	
	<ctrl:tabset styleId="tabset3" name="nbwstabset"
	action="main/iuauser/nbws" width="100%" runat="server"
	bgcolor="#DADFE0">

	<ctrl:tab tabid="tab111" title="tabset111.tab111.title"
		content="/jsp/nbws/Details.jsp" tooltip="tabset111.tab111.title.tooltip" onclick="gTabClick=true">
		Test
	</ctrl:tab>
		
    <!-- USR 8399-1 changes -->
	<% 
      ArrayList medRec = (ArrayList) pageContext.getSession().getAttribute("medRecsDataStatus");
      if(medRec.get(0).equals("0")) {%> 
		<ctrl:tab tabid="tab112" title="tabset111.tab112.title"
			content="/jsp/nbws/MedRec.jsp" tooltip="tabset111.tab112.title.tooltip" onclick="gTabClick=true"/>
    <% } else {  %>
       <ctrl:tab tabid="tab112" title="tabset111.tab112.title.star"
			content="/jsp/nbws/MedRec.jsp" tooltip="tabset111.tab112.title.tooltip" onclick="gTabClick=true"/>
    <!--  End of USR 8399-1 changes -->
    
    <% } 
      ArrayList prevPolicies = (ArrayList) pageContext.getSession().getAttribute("prevPolicieDataStatus");
      if(prevPolicies.get(0).equals("0")) {%> 
		<ctrl:tab tabid="tab113" title="tabset111.tab113.title"
			content="/jsp/nbws/PrevPolicies.jsp" tooltip="tabset111.tab113.title.tooltip" onclick="gTabClick=true"/>
	<% } else {  %>
       <ctrl:tab tabid="tab113" title="tabset111.tab113.title.star"
			content="/jsp/nbws/PrevPolicies.jsp" tooltip="tabset111.tab113.title.tooltip" onclick="gTabClick=true" />

    <% }
      ArrayList memos = (ArrayList) pageContext.getSession().getAttribute("memosAmendDataStatus");
      if(memos.get(0).equals("0")) {%> 
		<ctrl:tab tabid="tab114" title="tabset111.tab114.title"
			content="/jsp/nbws/MemoAmmendments.jsp" tooltip="tabset111.tab114.title.tooltip" onclick="gTabClick=true"/>
	<% } else {  %>
		<ctrl:tab tabid="tab114" title="tabset111.tab114.title.star"
			content="/jsp/nbws/MemoAmmendments.jsp" tooltip="tabset111.tab114.title.tooltip" onclick="gTabClick=true"/>

    <% }ArrayList sharefInfo = (ArrayList) pageContext.getSession().getAttribute("sharedInfoDataStatus");
      if(sharefInfo.get(0).equals("0")) {%> 
		<ctrl:tab tabid="tab115" title="tabset111.tab115.title"
			content="/jsp/nbws/SharedInformation.jsp" tooltip="tabset111.tab115.title.tooltip" onclick="gTabClick=true" />
	<% } else {  %>
		<ctrl:tab tabid="tab115" title="tabset111.tab115.title.star"
			content="/jsp/nbws/SharedInformation.jsp" tooltip="tabset111.tab115.title.tooltip" onclick="gTabClick=true" />
    <% } Integer size = (Integer) pageContext.getSession().getAttribute("telephoneInterviewStatus");
      if(size.intValue() == 0) {%>
    	<ctrl:tab tabid="tab116" title="tabset111.tab116.title"
			content="/jsp/nbws/TeleInterview.jsp" tooltip="tabset111.tab116.title.tooltip" onclick="gTabClick=true"/>
	  <%} else { %>
	    <ctrl:tab tabid="tab116" title="tabset111.tab116.title.star"
			content="/jsp/nbws/TeleInterview.jsp" tooltip="tabset111.tab116.title.tooltip" onclick="gTabClick=true"/>
	  <%}%>
</ctrl:tabset>

