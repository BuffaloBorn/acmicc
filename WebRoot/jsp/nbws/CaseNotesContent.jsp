<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld"     prefix="base" %>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld"    prefix="forms" %>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl" %>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld"  prefix="util" %>


<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>


<script language="JavaScript">

var submitcount=0;

function goclear() {
	gSaveClicked=true;
	gTabClick=true;
	document.CaseNotesForm.message.value = "";
	document.CaseNotesForm.subject.value = "";
	CaseNotesForm.submit();
}


function goSave() {
	gSaveClicked=true;
		if (submitcount==0)
		{
			gTabClick=true;
			submitcount++;
			CaseNotesForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }	

}





</script>


<html:form action="/iuauser/caseNotes">

	<forms:form type="edit" caption="Case.Notes" formid="CaseNotesForm" width="550" name="CaseNotesForm">

		
		<forms:section noframe="false">
			<forms:row>
				<forms:textarea	label="blank.textarea" property="previousNotes" cols="115" rows="25"  valign="top" readonly="true"/>
			</forms:row>

		<forms:textarea label="subject.textarea" property="subject" cols="115" rows="1" maxlength="50" valign="top" onkeydown="gChangesWereMade=true" />
		<forms:textarea label="mssage.textarea" property="message" cols="115" rows="2" maxlength="200" valign="top" onkeydown="gChangesWereMade=true" />

		<forms:buttonsection default="btnSave">
				<forms:button  name="btnSave"    text="button.title.update"    title="button.title.update" onclick="goSave()"/>
				<forms:button  name="btnClear"    text="button.title.clear"    title="button.title.clear" onclick="goclear()" />
		</forms:buttonsection>
	</forms:section>

	</forms:form>

</html:form>
