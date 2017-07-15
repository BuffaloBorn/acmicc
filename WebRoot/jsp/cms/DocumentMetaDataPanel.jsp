<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/cc-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/cc-menu.tld" prefix="menu" %>

<div align="center">
<template:insert base="/jsp" template="/jsp/template/Main.jsp">
	<template:put name="title"       content="ACMI Individual Underwriting and Administration" direct="true"/>
	<template:put name="header"      content="$/template/Header.jsp"/>
	<template:put name="content"     content="$/cms/DocumentMetaDataPanelContent.jsp"/>
</template:insert>
</div>