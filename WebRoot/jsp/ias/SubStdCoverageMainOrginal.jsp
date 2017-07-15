<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">

function mask(str,textbox,loc,delim){
var locs = loc.split(',');

for (var i = 0; i <= locs.length; i++){
	for (var k = 0; k <= str.length; k++){
	 if (k == locs[i]){
	  if (str.substring(k, k+1) != delim){
	   if (event.keyCode != 8){ //backspace
	    str = str.substring(0,k) + delim + str.substring(k,str.length);
       }
	  }
	 }
	}
 }
textbox.value = str
}

</script>

<html:form action="/iuauser/subStdCoverageMain" styleId="frmSubStdCoverageMain">
	<forms:form formid="subStdCoverageMain" caption="form.iasdiary.sub.std.coverage.main.title" type="edit" width="750" noframe="false">
		<forms:section title="form.iasdiary.sub.std.coverage.main.sys.info.section">
			<forms:row>
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.policyid" property="POLICY_ID" colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.status" property="POLICY_STATUS"  colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.status.effective.date" property="STATUS_EFF_DATE" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.person.id" property="PERSON_ID"  colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.status" property="PERSON_STATUS"  colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.status.effective.date" property="PERSON_STATUS_EFF_DATE"  colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.name" property="PERSON_NAME" colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.mode" property="MODE"  colspan="1" />
				<forms:text label="form.iasdiary.sub.std.coverage.main.display.date" property="DISPLAY_DATE" colspan="1" size="10" maxlength="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" />
			</forms:row>
			<forms:row>
				<forms:html colspan="2"></forms:html>	
				<forms:text label="form.iasdiary.sub.std.coverage.main.next.due.date" property="NEXT_DUE_DATE" size="10" width="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" />
			</forms:row>
		</forms:section>	
		<forms:section title="form.iasdiary.sub.std.coverage.main.std.condition.code.selection">
			<forms:html  label="form.iasdiary.sub.std.coverage.main.coverage.code">
					<ctrl:plaintext  property="COVERAGE_CODE"  />
			</forms:html>
			<forms:html label="form.iasdiary.sub.std.coverage.main.ss.code" join="true">
				<ctrl:select id="substdreasoncode"  property="SUB_STANDARD_RISK_CODE">
					<base:options property="substdreasoncodeOptions" keyProperty="key" labelProperty="value" />
				</ctrl:select>
				<ctrl:button name="btnResetSudStdCodes" src="fw/def/image/buttons/btnClose3.gif" tooltip="form.iasdiary.sub.std.coverage.reset" />	
			</forms:html> 
		</forms:section>
		<forms:section title="form.iasdiary.sub.std.coverage.main.condition.code.selection">
			<forms:row>
				<%--<forms:text label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODEONE"  size="8" maxlength="8" />--%>
				<forms:select id="conditioncode1" label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODEONE" size="1">
						<base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
				</forms:select>
				<forms:text label="list.iasdiary.sub.std.coverage.main.text" property="CONDITION_TEXTONE" size="50" maxlength="50" />
			</forms:row>
			<forms:row>
				<%--<forms:text label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODETWO"  size="8" maxlength="8" />--%>
				<forms:select id="conditioncode2" label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODETWO" size="1">
						<base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
				</forms:select>
				<forms:text label="list.iasdiary.sub.std.coverage.main.text" property="CONDITION_TEXTTWO" size="50" maxlength="50" />
			</forms:row>
			<forms:row>
				<%--<forms:text label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODETHREE"  size="8" maxlength="8" /> --%>
				<forms:select id="conditioncode3" label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODETHREE" size="1">
						<base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
				</forms:select>
				<forms:text label="list.iasdiary.sub.std.coverage.main.text" property="CONDITION_TEXTTHREE" size="50" maxlength="50" />
			</forms:row>
			<forms:row>
				<%--<forms:text label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODEFOUR"  size="8" maxlength="8" /> --%>
				<forms:select id="conditioncode4" label="list.iasdiary.sub.std.coverage.main.condition.code" property="CONDITION_CODEFOUR" size="1">
						<base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
				</forms:select>
				<forms:text label="list.iasdiary.sub.std.coverage.main.text" property="CONDITION_TEXTFOUR" size="50" maxlength="50" />
			</forms:row>
		</forms:section>	
		<forms:section title="form.iasdiary.sub.std.coverage.main.start.date.term.section">
			<forms:row>	
				<forms:text label="form.iasdiary.sub.std.coverage.main.start.date" property="SUB_EFF_DATE" size="10" width="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');"/>
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.term.date" property="SUB_EXT_DATE"  colspan="1" />
				<forms:plaintext label="form.iasdiary.sub.std.coverage.main.time.period" property="SS_CCODE_TIME_PERIOD" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdiary.policy.person.coverage.main.section.title">
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" />
				<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.coverage.main.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:section>
	</forms:form>
</html:form>

