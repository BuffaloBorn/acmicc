<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/cc-template.tld" prefix="template"%>
<template:insert base="/jsp" template="/jsp/template/Main.jsp">
	<template:put name="title" content="ACMI IU&A Process System" direct="true" />
	<template:put name="header" content="$/template/Header.jsp" />
	<template:put name="content" content="$/ias/EventStdMemo.jsp" />
	<template:put name="footer" content="$/template/Footer.jsp" />
</template:insert>
