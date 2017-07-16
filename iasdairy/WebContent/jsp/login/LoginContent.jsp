<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%pageContext.setAttribute(com.cc.framework.Globals.LOCALENAME_KEY, "true");%>

<script language="JavaScript">

function validateInput() {
  
	var userId = logonForm.userId.value;
	var password = logonForm.password.value;
	var message = "Please correct the following errors:\n";
	var goodVals = true;		
	if (userId == null || userId.length == 0)
	{
		message += " - The UserId field is required.\n";
		goodVals = false;
	}
	if (password == null || password.length == 0)
	{
		message += " - The Password field is required.\n";
		goodVals = false;
	}
	if (goodVals != true) {
		alert(message);		
	}
	else {
		logonForm.submit();
		document.body.style.cursor = "wait";
	}		
}
</script>
<script for="onLoad">

	try {
		setTimeout("logonForm.userId.focus()", 500);
	}
	catch(e) {		
	}	
	
</script>
<br><br>

<html:form action="/logon" >
	<forms:form type="edit" caption="user.logon.welcome" formid="frmEdit" width="350">
		<forms:text tabindex="0" label="user.form.id" property="userId" size="25" maxlength="15" required="true" />
		<forms:password label="user.form.password" property="password" size="25" maxlength="15" required="true" />
		<forms:buttonsection default="btnLogon">
			<forms:button name="btnLogon" text="button.title.login" default="true" onclick="validateInput()" />
		</forms:buttonsection>
	</forms:form>
</html:form>
