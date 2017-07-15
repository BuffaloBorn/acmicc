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
function goUpdate() {
	gSaveClicked=true;
	if (submitcount==0)
		{
			submitcount++;
			SharedInfoForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }

	document.body.style.cursor = "wait";
}



</script>

<html:form action="/nbs/sharedInfo">

	<forms:form type="edit" caption="Shared.Info" formid="SharedInfoForm" width="550" name="SharedInfoForm">

		
		<forms:section noframe="false">
			<!-- 1, 2 -->
			<forms:row>
				<forms:checkbox  label="blank.checkbox"  property="checkboxRPR"  description="shared.checkboxRPR" onclick="gChangesWereMade=true"/>				
				<forms:checkbox  label="blank.checkbox"  property="checkboxADEC"  description="shared.checkboxADEC" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea	label="blank.textarea" property="rpr" cols="70" maxlength="250" rows="3"  valign="top"  onkeydown="gChangesWereMade=true" />
				<forms:textarea	label="blank.textarea" property="adec" cols="70" maxlength="250"  rows="3"  valign="top"  onkeydown="gChangesWereMade=true" />
			</forms:row>

			<!-- 3, 4 -->
			<forms:row>
				<forms:checkbox  label="blank.checkbox"  property="checkboxRCA"  description="shared.checkboxRCA" onclick="gChangesWereMade=true"/>
				<forms:checkbox  label="blank.checkbox"  property="checkboxDNP"  description="shared.checkboxDNP" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea	label="blank.textarea" property="rca" cols="70" rows="3" maxlength="250"  valign="top"  onkeydown="gChangesWereMade=true" />
				<forms:textarea	label="blank.textarea" property="dnp" cols="70" rows="3" maxlength="250"  valign="top"  onkeydown="gChangesWereMade=true" />
			</forms:row>

			<!-- 5 -->
			<forms:row>
				<forms:checkbox  label="blank.checkbox"  property="checkboxSIOT"  description="shared.checkboxSIOT" onclick="gChangesWereMade=true"
				/>
			</forms:row>
			<forms:row>
				<forms:textarea	label="blank.textarea" property="siot" cols="70" rows="3" maxlength="250" valign="top"  onkeydown="gChangesWereMade=true"/>
			</forms:row>

		<forms:buttonsection default="btnUpdate">
				<forms:button  name="btnUpdate"    text="button.title.update"    title="button.title.update" onclick="goUpdate()" />
		</forms:buttonsection>
	</forms:section>

	</forms:form>

</html:form>
