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
	</script>

	<c:if test='${param.showPopup == "false"}'>
		<script type="text/javascript">
			function lightboxPopupDivLoginTrue() {lightboxPopup('divLogin', true);}
			window.attachEvent('onload', lightboxPopupDivLoginTrue);
		</script>
	</c:if>
	
	<c:if test='${param.autoSavePopUp == "true"}'>
		<script type="text/javascript">
	  		function autoSavePopUpTrue() {autoSavePopUp();}
	  		window.attachEvent('onunload', autoSavePopUpTrue);
		</script>
	</c:if>
	
	<script LANGUAGE="JavaScript"><!--
			var autoSave = null;
			var gIASChangesWereMade = false;
			var gIASSaveClicked = false;

			function autoSavePopUp()
			{
				var width = 850;
				var height = 520;
			
				var winLeft = (screen.width-width)/2; 
				var winTop = (screen.height-(height+110))/2; 

				if (gIASChangesWereMade == true && gIASSaveClicked == false) 
				{
					
					if(null == iasdiaryAutoSaveWin || iasdiaryAutoSaveWin.closed)
					{
						document.forms('<%=(String) session.getAttribute("currentPageForm")%>').autoSave.value = 'true';
						document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
					
						var url = document.getElementById("currentPageURL").value; 
						
						var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes';
					 	iasdiaryAutoSaveWin =window.open(url,'name2',options);
					}
					else
					{		
						iasdiaryAutoSaveWin.location.href = url;
					}
					
					if (window.focus) 
					{
						iasdiaryAutoSaveWin.focus();
					}
				}				
			}
			
			function closeWin()
			{
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').autoSave.value = 'clear';
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
				self.close();
			}
			
			function updateBtnClick()
			{
				
				<% 
						String IASModify2 = (String) session.getAttribute("IASModify");
						String modifyStatus2 = (String) pageContext.getAttribute("modifyStatus");
						
						if (modifyStatus2 != null)
						{
							if(modifyStatus2.equalsIgnoreCase("create"))
							{
				%>
								document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnSaveHidden.value = 'clicked';
				<%			}
							
							if(modifyStatus2.substring(0,4).equalsIgnoreCase("edit"))
							{
				%>
								document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnEditHidden.value = 'clicked';
				<%			}	
							
						}
						
						if (IASModify2 != null)
						{
						
							if(IASModify2.equalsIgnoreCase("create"))
							{
				%>
				    			document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnSaveHidden.value = 'clicked';
				<%			}
							
							if(IASModify2.substring(0,4).equalsIgnoreCase("edit"))
							{
				%>
							   document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnEditHidden.value = 'clicked';
				<%			}	
						
						}
				%>
				
				
				
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
				
				
				self.close();
				return false;
			}
			
			function goUpdateBtnClick()
			{
				<% 
						String IASModify = (String) session.getAttribute("IASModify");
						String modifyStatus = (String) pageContext.getAttribute("modifyStatus");
						
						if (modifyStatus != null)
						{
							if(modifyStatus.equalsIgnoreCase("create"))
							{
				%>
								document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnSaveHidden.value = 'clicked';
				<%			}
							
							if(modifyStatus.substring(0,4).equalsIgnoreCase("edit"))
							{
				%>
								document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnEditHidden.value = 'clicked';
				<%			}	
							
						}
						
						if (IASModify != null)
						{
						
							if(IASModify.equalsIgnoreCase("create"))
							{
				%>
				    			document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnSaveHidden.value = 'clicked';
				<%			}
							
							if(IASModify.substring(0,4).equalsIgnoreCase("edit"))
							{
				%>
							   document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnEditHidden.value = 'clicked';
				<%			}	
						
						}
				%>
				
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
				return false;
			}
			
			function goBtnBack()
			{
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnBackHidden.value = 'clicked';
				document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
				
			}

		--></script>
		
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
		
	<!-- Needed for lightbox popups. -->
    <div id="divLightboxBlocker" class="cssLightboxBlocker"></div>
	<!-- ******************************************************************* -->
    <!-- ***** Login                                                   ***** -->
    <!-- ******************************************************************* -->
    <div id="divLogin" class="cssLightboxPopup" style="width:200px;height:100px;display:none;">
      <forms:form type="display" caption="form.iasdiary.dialog.information" formid="frmDialog" width="100%">
				<forms:html>
					<table cellspacing="7" cellpadding="0" border="0">
						<tr>
							<td valign="baseline" colspan="2">
								<bean:message key="form.iasdiary.navigate.off.page"/>
							</td>
						</tr>
						<tr>
							<td valign="baseline" colspan="2">
								<bean:message key="form.iasdiary.new.selected"/>
							</td>
						</tr>
						<tr>
							<td valign="middle" colspan="2">
								<bean:message key="form.iasdiary.closed.session"/>
							</td>
						</tr>
						<tr>
							<td style="padding-top:5px;">
								<ctrl:button name="btnUpdate" text="form.iasdiary.apply.update" title="form.iasdiary.apply.update" width="90" onclick="javascript:return updateBtnClick();"/>							
							</td>
							<td style="padding-top:5px;">
								<ctrl:button styleId="btnClose" name="btnClose" text="form.iasdiary.no.update" title="form.iasdiary.no.update" width="90" onclick="javascript:closeWin();"/>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
    </div>
    <div id="divLogin_Shadow0" class="cssLightboxShadow0" style="width:200px;height:100px;"></div>
    <div id="divLogin_Shadow1" class="cssLightboxShadow1" style="width:200px;height:100px;"></div>
    <div id="divLogin_Shadow2" class="cssLightboxShadow2" style="width:200px;height:100px;"></div>
    <div id="divLogin_Shadow3" class="cssLightboxShadow3" style="width:200px;height:100px;"></div>
    <div id="divLogin_Shadow4" class="cssLightboxShadow4" style="width:200px;height:100px;"></div>
    <div id="divLogin_Shadow5" class="cssLightboxShadow5" style="width:200px;height:100px;"></div>
    
	<!-- ******************************************************************* -->
    <!-- ***** Login                                                   ***** -->
    <!-- ******************************************************************* -->
    <div id="divLogin2" class="cssLightboxPopup" style="width:200px;height:100px;display:none;">
      <forms:form type="display" caption="form.iasdiary.dialog.information" formid="frmDialog" width="100%">
				<forms:html>
					<table cellspacing="7" cellpadding="0" border="0">
						<tr>
							<td valign="baseline" colspan="2">
								<bean:message key="form.iasdiary.navigate.off.page"/>
							</td>
						</tr>
						<tr>
							<td valign="baseline" colspan="2">
								<bean:message key="form.iasdiary.new.selected"/>
							</td>
						</tr>
						<tr>
							<td valign="middle" colspan="2">
								<bean:message key="form.iasdiary.closed.session"/>
							</td>
						</tr>
						<tr>
							<td style="padding-top:5px;">
								<ctrl:button name="btnUpdate" text="form.iasdiary.apply.update" title="form.iasdiary.apply.update" width="90" onclick="javascript:return goUpdateBtnClick();"/>							
							</td>
							<td style="padding-top:5px;">
								<ctrl:button styleId="btnClose" name="btnClose" text="form.iasdiary.no.update" title="form.iasdiary.no.update" width="90" onclick="javascript:goBtnBack();"/>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
    </div>
    <div id="divLogin2_Shadow0" class="cssLightboxShadow0" style="width:200px;height:100px;"></div>
    <div id="divLogin2_Shadow1" class="cssLightboxShadow1" style="width:200px;height:100px;"></div>
    <div id="divLogin2_Shadow2" class="cssLightboxShadow2" style="width:200px;height:100px;"></div>
    <div id="divLogin2_Shadow3" class="cssLightboxShadow3" style="width:200px;height:100px;"></div>
    <div id="divLogin2_Shadow4" class="cssLightboxShadow4" style="width:200px;height:100px;"></div>
    <div id="divLogin2_Shadow5" class="cssLightboxShadow5" style="width:200px;height:100px;"></div>
	
		
		<% 
			String currentPageURL = null;
		
			currentPageURL = (String) session.getAttribute("currentPageURL");
			
			if(currentPageURL == null)
			{
				currentPageURL = "/acmicc/iuauser/iasdiary.do?intPolicy=true";

			}%>
			<input type="hidden" name="currentPageURL" value="<%=currentPageURL%>"/>
	</body>
</html>
<util:jsp directive="endofpage" />
