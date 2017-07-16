<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script language="javascript" src="app/js/xp_progress.js">

/***********************************************
* WinXP Progress Bar- By Brian Gosselin- http://www.scriptasylum.com/
* Script featured on Dynamic Drive- http://www.dynamicdrive.com
* Please keep this notice intact
***********************************************/
</script>

<script type="text/javascript">
function selectClicked(pos) 
{
	window.onbeforeunload = null;
	document.policyConditionCodesMainForm.currentPosition.value = pos;
	policyConditionCodesMainForm.submit();
}

function disableBtnEdit()
{
	gIasSaveClicked=true;
	document.getElementById("btnEdit").style.visibility =  "hidden"; 
	bar1.showBar();
	bar1.showMess();
}

	<c:if test="${not empty requestScope.gIasChangesWereMade}">
		gIasChangesWereMade=true;
	</c:if>

</script>

<html:form action="/iuauser/policyConditionCodesMain" styleId="frmSubStdCoverageMain">
	<html:hidden property="currentPosition" value=""/>
	<forms:form formid="policyConditionCodeMain" caption="form.iasdiary.policy.condition.codes.main.title" type="edit" width="750" noframe="false">
		<forms:section title="form.iasdiary.policy.condition.codes.main.sys.info.section">
			<forms:row>
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.policyid" property="POLICY_ID" colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.status" property="POLICY_STATUS"  colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.status.effective.date" property="STATUS_EFF_DATE" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.person.id" property="PERSON_ID"  colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condiction.main.person.status.ind" property="PERSON_STATUS_IND"  colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.status.effective.date" property="PERSON_STATUS_EFF_DATE"  colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.name" property="PERSON_NAME" colspan="1" />
				<forms:text label="form.iasdiary.policy.condition.codes.main.display.date" property="DISPLAY_DATE" size="10" width="10" colspan="1" onchange="gIasChangesWereMade=true"/>
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.next.due.date" property="NEXT_DUE_DATE"  colspan="1" />
			</forms:row>
			<forms:html  colspan="3">
				<div id="ratingprocess" style="visibility:hidden;">
							<div> 
								<script type="text/javascript">
									var bar1= createBar(300,15,'white',1,'black','blue',85,7,3,"", "Re-rating in progress");
									bar1.hideBar();
									bar1.hideMess();
								</script>
							</div>
						</div>
			</forms:html>
		</forms:section>
		<forms:section title="form.iasdiary.policy.condition.codes.main.condition.code.selection">
			<forms:html align="center">
				<ctrl:list styleId="conditionCodeList"  name="conditionCodeList" id="conditionCodeList"  rows="4"  formElement="true" noframe="true">
					<ctrl:columnselect	title="list.iasdiary.policy.condition.codes.main.condition.code" property="CODE" editable="true" width="120" onchange="javascript:selectClicked('@{bean.POSTION}')" style="background-color:red;" type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
			            <base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
					</ctrl:columnselect>
					<ctrl:columnhtml   title="list.iasdiary.policy.condition.codes.main.text"  editable="true"  id="rowbean"  type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
					 	<% if (rowbean.getDESCRIPTION_REQUIRED_IND().equalsIgnoreCase("Y")) { %>
               				<ctrl:text  property="DESCRIPTION" size="50" maxlength="50"/>
					 	<% } else  { %>
					 		 <ctrl:plaintext  property="DESCRIPTION"/>
					 	 <% } %>
					</ctrl:columnhtml> 
				</ctrl:list>
			</forms:html>
		</forms:section>
		<forms:section title="form.iasdiary.policy.condition.codes.main.start.date.term.section">
			<forms:row>	
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.start.date" property="CCODE_START_DATE" width="10" colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.term.date" property="CCODE_TERM_DATE"  colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condition.codes.main.time.period" property="CCODE_TIME_PERIOD" colspan="1" />
				<forms:plaintext label="form.iasdiary.policy.condiction.main.state.issued" property="STATE_ISSUED" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdiary.policy.condition.codes.main.section.title">
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="javascript:disableBtnEdit();" onclick="runPageValidation(this)"/>
				<c:choose> 
					<c:when test="${param.comingFrom == 'PD'}">
						<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
					</c:when>
					<c:otherwise>
						<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.policy.person.main.back" onclick="runPageValidation(this)"/>
					</c:otherwise>
				</c:choose>
			</forms:buttonsection>
		</forms:section>
	</forms:form>
</html:form>

