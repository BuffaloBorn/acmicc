<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/cc-template.tld" prefix="template"%>
<div align="center"><template:insert base="/jsp" template="/jsp/template/Main.jsp">
	<template:put name="title" content="ACMI IU&A Process System" direct="true" />
	<template:put name="header" content="$/template/Header.jsp" />
	<template:put name="content" content="$/ias/UnderwritingNotesMain.jsp" />
	<template:put name="footer" content="$/template/Footer.jsp" />
</template:insert></div>

