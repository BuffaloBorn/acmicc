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

function selectClicked(pos) 
{
	document.subStdCoverageMainForm.currentPosition.value = pos;
	subStdCoverageMainForm.submit();
}


function checkBand(oListbox)
{
	if(oListbox.options[oListbox.selectedIndex].value == '33')
	{
		document.getElementById("hippaId").style.visibility =  "visible"; 	
	}
	else
	{
		document.getElementById("hippaId").style.visibility =  "hidden";
	}
}


function disableBtnEdit()
{
	document.getElementById("btnEdit").style.visibility =  "hidden"; 
	bar1.showBar();
	bar1.showMess();
}


function disableBtnResetSudStdCodes()
{
	document.getElementById("btnResetSudStdCodes").style.visibility =  "hidden"; 
	bar1.showBar();
	bar1.showMess();
}


</script>


<script language="javascript" src="app/js/xp_progress.js">

/***********************************************
* WinXP Progress Bar- By Brian Gosselin- http://www.scriptasylum.com/
* Script featured on Dynamic Drive- http://www.dynamicdrive.com
* Please keep this notice intact
***********************************************/
</script>

<script language='JavaScript' src='app/help/help.js'></script>

<c:if test='${sessionScope.IASModify == "display"}'>
	<html:form action="/iuauser/subStdCoverageMain" styleId="frmSubStdCoverageMain">   
		<forms:form formid="subStdCoverageMain" caption="form.iasdiary.sub.std.coverage.main.title" type="display" width="750" noframe="false">
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
					<forms:plaintext label="form.iasdiary.sub.std.coverage.main.display.date" property="DISPLAY_DATE"  colspan="1" />
				</forms:row>
				<forms:row>
					<forms:html colspan="2"></forms:html>	
					<forms:plaintext label="form.iasdiary.sub.std.coverage.main.next.due.date" property="NEXT_DUE_DATE"  colspan="1" />
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
				</forms:html> 
			</forms:section>
			<forms:section title="form.iasdiary.sub.std.coverage.main.condition.code.selection">
				<forms:html align="center">
					<ctrl:list styleId="conditionCodeList"  name="conditionCodeList" id="conditionCodeList"  rows="4"  formElement="true" noframe="true">	
						<ctrl:columnselect id="rowselect" title="list.iasdiary.sub.std.coverage.main.condition.code" property="CODE" editable="true" width="120"  onchange="javascript:selectClicked('@{bean.POSTION}')" style="background-color:red;" type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
				            <base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
						</ctrl:columnselect>
						<ctrl:columnhtml   title="list.iasdiary.sub.std.coverage.main.text"  editable="true"  id="rowbean"  type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
						 	<%
						 		if (rowbean.getDESCRIPTION_REQUIRED_IND().equalsIgnoreCase("Y")) {
						 	%>
	               				<ctrl:text  property="DESCRIPTION" size="50" maxlength="50"/>
						 	<%
						 		} else  {
						 	%>
						 		 <ctrl:plaintext  property="DESCRIPTION"/>
						 	 <%
						 	 	}
						 	 %>
						</ctrl:columnhtml> 
					</ctrl:list>
				</forms:html>
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
					<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.coverage.main.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:section>
		</forms:form>
	</html:form>
</c:if>


<c:if test='${sessionScope.IASModify == "edit"}'>
	<html:form action="/iuauser/subStdCoverageMain" styleId="frmSubStdCoverageMain">
		<html:hidden property="currentPosition" value=""/>
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
					<forms:plaintext label="form.iasdiary.sub.std.coverage.main.display.date" property="DISPLAY_DATE"  colspan="1" />
				</forms:row>
				<forms:row>
					<forms:html colspan="2">
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
					<forms:plaintext label="form.iasdiary.sub.std.coverage.main.next.due.date" property="NEXT_DUE_DATE"  colspan="1" />
				</forms:row>
			</forms:section>	
			<forms:section title="form.iasdiary.sub.std.coverage.main.std.condition.code.selection">
				<forms:html  label="form.iasdiary.sub.std.coverage.main.coverage.code">
						<ctrl:plaintext  property="COVERAGE_CODE"  />
				</forms:html>
				<forms:html label="form.iasdiary.sub.std.coverage.main.ss.code" join="true">
					<ctrl:select id="substdreasoncode"  property="SUB_STANDARD_RISK_CODE" onchange="javascript:checkBand(this);">
						<base:options property="substdreasoncodeOptions" keyProperty="key" labelProperty="value" />
					</ctrl:select>
					<br/>
					<span id="hippaId"  style="color:red; font-weight:bold;visibility:hidden;">WARNING: This is a HIPAA policy.  You MUST update the HIPAA field on Policy Maintenance directly through IAS</span>
					<ctrl:button name="btnResetSudStdCodes" text="form.iasdiary.sub.std.coverage.reset" tooltip="form.iasdiary.sub.std.coverage.reset" onmouseup="javascript:disableBtnResetSudStdCodes();" />	
				</forms:html> 
			</forms:section>
			<forms:section title="form.iasdiary.sub.std.coverage.main.condition.code.selection">
				<forms:html align="center">
					<ctrl:list styleId="conditionCodeList"  name="conditionCodeList" id="conditionCodeList"  rows="4"  formElement="true" noframe="true">
						<ctrl:columnselect	id="rowselect" title="list.iasdiary.sub.std.coverage.main.condition.code" property="CODE" editable="true" width="120" onchange="javascript:selectClicked('@{bean.POSTION}')" style="background-color:red;" type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
				            <base:options property="condictionCodesOptions" keyProperty="key" labelProperty="value" />
						</ctrl:columnselect>
						<ctrl:columnhtml   title="list.iasdiary.sub.std.coverage.main.text"  editable="true"  id="rowbean"  type="com.cc.acmi.presentation.dsp.ConditionCodeDsp">
						 	<% if (rowbean.getDESCRIPTION_REQUIRED_IND().equalsIgnoreCase("Y")) {%>
	               					
	               				<ctrl:text  property="DESCRIPTION" size="50" maxlength="50"/>
						 	<% } else  { %>
						 		 <ctrl:plaintext  property="DESCRIPTION"/>
						 	 <% } %>
						</ctrl:columnhtml> 
					</ctrl:list>
				</forms:html>
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
				<c:if test="${empty param.rating}">
					<forms:button base="buttons.src.def2" styleId="btnEdit" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="javascript:disableBtnEdit();"/>
				</c:if>
				
					<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.coverage.main.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:section>
		</forms:form>
	</html:form>
</c:if>