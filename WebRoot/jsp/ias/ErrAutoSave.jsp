<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
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
		<title>ACMI IUA Process System</title>
		<util:base />
		<%-- Framework Includes --%>
		<util:jsp directive="includes" />
		
		<script>
		
			var iasdiaryAutoSaveWin = null;
			
			function closeWin()
			{
				window.opener.close();
				self.close();
			}
			
			function diaryAutoSaveWin()
			{
				var width = 850;
				var height = 520;

				var winLeft = (screen.width-width)/2; 
				var winTop = (screen.height-(height+110))/2; 

				var url = document.getElementById("currentPageURL").value; 
				if(null == iasdiaryAutoSaveWin || iasdiaryAutoSaveWin.closed)
				{
					var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes';
					iasdiaryAutoSaveWin =window.open(url,'name',options);
				}
				else
				{
					iasdiaryAutoSaveWin.location.href = url;
				}
			}
		
		</script>
		
	</head>
	<body bottommargin="10" topmargin="10" leftmargin="10" rightmargin="10" marginheight="10" marginwidth="10" onload="init();" >
		<html:form action="/iuauser/StandardLetterErrAutoSave">
			<forms:form type="display" caption="form.iasdiary.dialog.information" formid="frmDialog" width="100%">
				<forms:html>
					<table cellspacing="7" cellpadding="0" border="0">
						<tr>
							<td valign="middle">
								&nbsp;<bean:message key="form.iasdiary.new.selected"/>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								&nbsp;<bean:message key="form.iasdiary.closed.session"/>
							</td>
						</tr>
						<tr>
							<td style="padding-top:5px;">
								<ctrl:button styleId="btnUpdate" name="btnUpdate" text="form.iasdiary.apply.update" title="form.iasdiary.apply.update" width="90"/>							
							</td>
							<td style="padding-top:5px;">
								<ctrl:button styleId="btnClose" name="btnClose" text="form.iasdiary.no.update" title="form.iasdiary.no.update" width="90" onclick="javascript:closeWin();"/>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
			<% 
			String currentPageURL = null;
		
			currentPageURL = (String) session.getAttribute("currentPageURL");
			
			if(currentPageURL == null)
			{
				currentPageURL = "/acmicc/iuauser/iasdiary.do?intPolicy=true";

			}%>
			<input id="currentPageURL" type="hidden" name="currentPageURL" value="<%=currentPageURL%>"/>
	</body>
</html:html>


