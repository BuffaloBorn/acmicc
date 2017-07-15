<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>

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
			MemosAmendForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
	
	
	document.body.style.cursor = "wait";
}



</script>

<html:form action="/nbs/memosAmend">

	<forms:form type="edit" caption="Isues.Memos" formid="MemosAmendForm" width="550" name="MemosAmendForm">


		<forms:section noframe="false">
			<!-- 1, 2 -->
			<forms:row>
				<forms:checkbox label="blank.checkbox" property="checkboxMDI" description="memos.checkboxMDI" onclick="gChangesWereMade=true" />
				<forms:checkbox label="blank.checkbox" property="checkboxMMI" description="memos.checkboxMMI" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea label="blank.textarea" property="mdi" cols="70" maxlength="250"  rows="3" valign="top" onkeydown="gChangesWereMade=true" />
				<forms:textarea label="blank.textarea" property="mmi" cols="70" maxlength="250"  rows="3" valign="top" onkeydown="gChangesWereMade=true" />
			</forms:row>
			
			<!-- 3, 4 -->
			<forms:row>
				<forms:checkbox label="blank.checkbox" property="checkboxMOI" description="memos.checkboxMOI" onclick="gChangesWereMade=true"/>
				<forms:checkbox label="blank.checkbox" property="checkboxSIA" description="memos.checkboxSIA" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea label="blank.textarea" property="moi" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
				<forms:textarea label="blank.textarea" property="sia" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
			</forms:row>
			
			<!-- 5, 6 -->
			<forms:row>
				<forms:checkbox label="blank.checkbox" property="checkboxSIAG" description="memos.checkboxSIAG" onclick="gChangesWereMade=true"/>
				<forms:checkbox label="blank.checkbox" property="checkboxSIAD" description="memos.checkboxSIAD" onclick="gChangesWereMade=true"/>
			</forms:row>		
			<forms:row>
				<forms:textarea label="blank.textarea" property="siag" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
				<forms:textarea label="blank.textarea" property="siad" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
			</forms:row>

			<!-- 7, 8 -->			
			<forms:row>
				<forms:checkbox label="blank.checkbox" property="checkboxGAL" description="memos.checkboxGAL" onclick="gChangesWereMade=true"/>
				<forms:checkbox label="blank.checkbox" property="checkboxAOA" description="memos.checkboxAOA" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea label="blank.textarea" property="gal" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
				<forms:textarea label="blank.textarea" property="aoa" cols="70"  maxlength="250" rows="3" valign="top" onkeydown="gChangesWereMade=true" />
			</forms:row>
			
			<!-- 9, 10 -->			
			<forms:row>
				<forms:checkbox label="blank.checkbox" property="checkboxANCH" description="memos.checkboxANCH" onclick="gChangesWereMade=true"/>
				<forms:checkbox label="blank.checkbox" property="checkboxMAOT" description="memos.checkboxMAOT" onclick="gChangesWereMade=true"/>
			</forms:row>
			<forms:row>
				<forms:textarea label="blank.textarea" property="anch" cols="70" maxlength="250"  rows="3" valign="top" onkeydown="gChangesWereMade=true" />
				<forms:textarea label="blank.textarea" property="maot" cols="70" maxlength="250"  rows="3" valign="top" onkeydown="gChangesWereMade=true" />
			</forms:row>

		</forms:section>

		<forms:buttonsection default="btnUpdate" align="left" join="true">
			<forms:button name="btnUpdate" text="button.title.update" title="button.title.update" onclick="goUpdate()" />
		</forms:buttonsection>


	</forms:form>

</html:form>
