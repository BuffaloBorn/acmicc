<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<script language="Javascript">

function setfocus()
{
	// empty method : SET FOCUS METHOD TO BE USED IN ANY PAGE. override this method in the respective page
}

</script>
<%--
<String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
--%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ page import="com.cc.acmi.presentation.taglib.TagUtils"%>
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
			<template:get name="title" />
		</title>
		<util:base />
		<%-- Framework Includes --%>
		<util:jsp directive="includes" />

	</head>
	<body bottommargin="0" topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" onLoad="init();setfocus();" bgcolor="#CFE1EB">
		<table width="980" height="100%" cellspacing="0" cellpadding="0" style="border-bottom: 0px;" align="center" bordercolor="#1E5C99" border="0.5px" bgcolor="#FFFFFF">
			<!-- Header -->
			<tr align="center">
				<td height="18">
					<template:get name="header" />
				</td>
			</tr>
			<%-- Content --%>
			<template:present name="contentheader">
				<%String captionString = TagUtils.getTemplate(pageContext, "contentheader");%>
				<tr>
					<td class="bodyelement" height="20">
						<ctrl:headline caption="<%=captionString%>" />
					</td>
				</tr>
			</template:present>
			<%if (TagUtils.hasMessages(pageContext, "error")) {

			%>
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
			<tr height="90%">
				<td valign="top" align="center" class="bodyelement">
					<template:present name="about">
						<template:get name="about" />
					</template:present>
					<template:get name="content" />
				</td>
			</tr>
			<tr>
				<td valign="top" align="center" class="bodyelement">
					<template:present name="footer">
						<template:get name="footer" />
					</template:present>
				</td>
			</tr>
		</table>
	</body>
</html>
<util:jsp directive="endofpage" />
