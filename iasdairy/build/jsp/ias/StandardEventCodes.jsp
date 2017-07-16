<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>

<script language="Javascript">
function openAmendmentWindow(eventCode)
{
	
	
	var stdEvtcode =  eventCode.options[eventCode.selectedIndex].value
	var policyNo = document.standardEventCodesForm.hiddenfield.value 
	var newStandardEvent = "/acmicc/iuauser/stdEventCode.do?eventcode=" + stdEvtcode + "&policyno=" + policyNo
	window.open(newStandardEvent,"_parent","");
	
	
}
</script>

<html:form action="/iuauser/stdEventCode" styleId="frmStandardEventCodesMain" >
	
	<forms:form formid="stdevtcodes" type="edit" width="920">
		<forms:html align="center">
			<forms:row>	
				<forms:select id="stdEventCodeId" label="form.iasdairy.std.event.codes"  property="stdEventCode" style="color: navy" width="350">
        			<base:options property="stdEventCodeOptions"  keyProperty="key" labelProperty="value" />
	        	</forms:select>
	        	<forms:buttonsection default="btnCreateEvent">
	        		<forms:button base="buttons.src.def2" name="btnCreateEvent" text="form.iasdairy.std.event.create.event" title="form.iasdairy.std.event.create.event" />
	        	</forms:buttonsection>
			</forms:row>	
		</forms:html>
	</forms:form>
	<%  String policyno = request.getParameter("policyno");%>
				<ctrl:hidden property="hiddenfield" value="<%=policyno%>"/>
</html:form>
