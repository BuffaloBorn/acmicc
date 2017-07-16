<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ page import="com.cc.acmi.presentation.taglib.TagUtils"%>
<%@ page import="com.epm.acmi.struts.Constants" %>
<html>
	<head>
		<meta name="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<!-- past date will not cache the content-->
		<meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="Copyright" content="Copyright (c) 2005 - 2006 SCC American Community Mutual Insurance">
		<meta name="Title" content="Individual Underwriting Process System">
		<meta name="Author" content="SoftwareAG USA">
		<meta name="Language" content="ENGLISH">
		<meta name="Robots" content="NOINDEX">
		<%-- Title --%>
		<title>
			<tiles:getAsString name="title"/>
		</title>
		<util:base />
		<%-- Framework Includes --%>
		<util:jsp directive="includes" />
		
		<link rel="StyleSheet" href="app/css/lightbox.css" type="text/css">

 		<!-- Import JavaScripts. -->
	    <script src="app/js/prototype.js" type="text/javascript"></script>
	    <script src="app/js/scriptaculous.js?load=effects" type="text/javascript"></script>
	    <script src="app/js/lightbox.js" type="text/javascript"></script>
		<script src="app/js/iasautosave.js" type="text/javascript"></script>
		<script src="app/js/popupiasdiary.js" type="text/javascript" ></script>
		<script language='JavaScript' src='app/js/validationiasdiary.js'></script>
	</head>
	
	<script language="Javascript">
	<!--
	
	/***********************************************
	* Auto Maximize Window Script- © Dynamic Drive (www.dynamicdrive.com)
	* This notice must stay intact for use
	* Visit http://www.dynamicdrive.com/ for this script and 100's more.
	***********************************************/

	top.window.moveTo(0,0);
	
	if (document.all) 
	{
		top.window.resizeTo(screen.availWidth,screen.availHeight);
	}
	else if (document.layers||document.getElementById) 
	{
		if (top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth)
		{
			top.window.outerHeight = screen.availHeight;
			top.window.outerWidth = screen.availWidth;
		}
	}
	//-->
	
	function monitorCookie()
	{
		if (getCookie('iaspopup') == 'close')
			this.close(); 	
	}
	
	window.setInterval('monitorCookie()', 500);
	window.onbeforeunload = runUnloadIasValidation;
	</script>
	
	<body id="mainbody" bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" onLoad="init();"  bgcolor="#CFE1EB" >
		<table width="980" height="100%" cellspacing="0" cellpadding="0" style="border-bottom: 0px;" align="center" bordercolor="#1E5C99" border="0.5px" bgcolor="#FFFFFF">
			<!-- Header -->
			<tr align="center">
				<td height="18">
					<tiles:insert attribute="header" />
				</td>
			</tr>
				<tr>
					<td class="legend" align="right" height="8" width="100%">
							<% if (session.getAttribute(Constants.keyAppFN) != null) { %>
								IUPS Key Applicant : <b><%= session.getAttribute(Constants.keyAppFN)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
							<%}%>
							<% if (session.getAttribute(Constants.taskName) != null) { %>
							   IUPS Activity Name : <b><%= session.getAttribute(Constants.taskName)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
							<%}%>
							<% if (session.getAttribute(Constants.policyNumber) != null) { %>
								IUPS Policy Number: <a href="/acmicc/iuauser/iasdiary.do?intPolicy=true"><b><%= session.getAttribute(Constants.policyNumber)%></b></a>&nbsp;&nbsp;|&nbsp;&nbsp;
							<%}%>
					 </td>
				</tr>
				<tr>
					<td class="legend" align="right" height="8" width="100%">
							<% if (session.getAttribute(Constants.IASkeyInsured) != null) { %>
								IAS Key Insured : <b><%= session.getAttribute(Constants.IASkeyInsured)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
							<%}%>
							<% if (session.getAttribute(Constants.IASpolicyNumber) != null) { %>
								IAS Policy Number: <b><%= session.getAttribute(Constants.IASpolicyNumber)%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
							<%}%>
					 </td>
				</tr>
			<%-- Content --%>
			<tiles:insert attribute="contentheader" ignore="true">
				<%String captionString = TagUtils.getTemplate(pageContext, "contentheader");%>
				<tr>
					<td class="bodyelement" height="20">
						<ctrl:headline caption="<%=captionString%>" />
					</td>
				</tr>
			</tiles:insert>
			<%if (TagUtils.hasMessages(pageContext, "error")) {%>
			<tr>
				<td class="bodyelement" height="20">
					<forms:message caption="dlg.error" formid="err" severity="error" />
				</td>
			</tr>
			<%}%>
			<%if (TagUtils.hasMessages(pageContext, "information")) {%>
			<tr>
				<td class="bodyelement">
					<forms:message caption="dlg.message" formid="info" severity="information" />
				</td>
			</tr>
			<%}%>
			<tr height="20%">
				<td valign="top" align="center" class="bodyelement">
					<tiles:insert attribute="about" ignore="true"/>
					<tiles:insert attribute="selectEvent" ignore="true"/>
				</td>
			</tr>
			<tr height="70%">
				<td valign="top" align="center" class="bodyelement">
					
						<tiles:insert attribute="content" />
		
				</td>
			</tr>
			<tr>
				<td valign="top" align="center" class="bodyelement">
					<tiles:insert attribute="footer"/>
				</td>
			</tr>
		</table>
	</body>
</html>
<util:jsp directive="endofpage" />
