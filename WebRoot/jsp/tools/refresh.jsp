<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>

<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>

<script language="Javascript">

function setVariable() {
   var refreshAcmic = adminForm.refreshAcmic.value;
   var refreshEPM   = adminForm.refreshEPM.value;
   var refreshIAS   = adminForm.refreshIAS.value;
   
   if (refreshAcmic != null && refreshAcmic == "on") {
   		adminForm.refreshAcmic.value = "true";
   }
   else {
   	 	adminForm.refreshAcmic.value = "false";
   }
   
   if (refreshEPM != null && refreshEPM == "on") {
       	adminForm.refreshEPM.value = "true";
   }
   else {
    	adminForm.refreshEPM.value = "false";
   }
   
    if (refreshIAS != null && refreshIAS == "on") {
       	adminForm.refreshIAS.value = "true";
   }
   else {
    	adminForm.refreshIAS.value = "false";
   }
      
   if (refreshEPM == null && refreshAcmic == null) {
   		alert("Nothing is selected, no caches will be refreshed");
   	}
   	else {
   		adminForm.submit();
   	}
}
</script>

<body>

<html:form action="/secondarymain/refresh">

<forms:form name="adminForm" formid="adminForm" type="edit">
    <forms:row>
		<forms:checkbox property="refreshAcmic" label="acmic.refresh.title" join="true"/>
	</forms:row>
 	<forms:row>		
		<forms:checkbox property="refreshEPM" label="epm.refresh.title" join="true"/>
	</forms:row>
	<forms:row>		
		<forms:checkbox property="refreshIAS" label="ias.refresh.title" join="true"/>
	</forms:row>
	<forms:row>

    	<forms:buttonsection default="btnOk" join="true">
			<forms:button name="btnOk" onclick="setVariable();" text="button.ok" />
			<forms:button name="btnCancel" text="button.cancel" />
		</forms:buttonsection>
	</forms:row>			
</forms:form>
</html:form>
</body>
