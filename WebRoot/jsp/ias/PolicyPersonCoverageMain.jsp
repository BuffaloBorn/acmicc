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

<c:if test='${sessionScope.IASModify == "display"}'>
	<div align="center">
		<html:form action="/iuauser/policyPersonCoverageMain" styleId="frmPolicyPersonCoverageMain">
			<forms:form formid="policyPersonCoverageMain" caption="form.iasdiary.policy.person.coverage.main.title" type="edit" width="920">
				<forms:html align="center">
					<table>
					<tr>
						<td height="98%" valign="top" align="center">
								<forms:section title="">
									<forms:row>
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.policyid" property="policyid"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.policy.status" property="policystatus"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.status.effective.date" property="policy_effective_date"  width="10" colspan="1" />
									</forms:row>
									<forms:row>	
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.personid" property="personid"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.person.status" property="person_status"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.status.effective.date" property="person_effective_date" width="10" colspan="1" />
									</forms:row>
									<forms:row>	
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.name" property="name"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.mode" property="mode"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.display.date" property="display_date"  width="10" colspan="1" />
									</forms:row>
								 </forms:section>
							</td>
						</tr>	
						<tr>
							<td valign="top">
								<logic:present scope="session" name="coverages">
									<ctrl:list id="coverages" name="coverages" title="list.iasdiary.policy.person.coverage.main.coverage.list" rows="50"  createButton="false" runat="server" locale="true">
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.coverage.code" property="COVERAGE_CODE"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.coverage.prem" property="ANNUAL_PREM_AMT"	 width="10" align="right"/>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.ss.perm" property="ANNUAL_PERM_EXTRA_PREM_AMT"	 width="10" align="right"/>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.tot.perm" property="TOTAL_PREMIUM"	 width="10" align="right"/>
										<ctrl:columngroup title="list.iasdiary.policy.person.coverage.main.substd" align="center">
											<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.substd.code" property="SUB_STANDARD_RISK_CODE" align="center"/>
											<ctrl:columnbutton  title="list.iasdiary.policy.person.main.edit.sub.std.coverage" text="list.iasdiary.policy.person.main.edit.sub.std.coverage.click" command="editstdcoverage" align="center"/>
										</ctrl:columngroup>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.eff.perm" property="START_DATE"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.term.date" property="END_DATE"	 width="10" />
									</ctrl:list>
								</logic:present>
								<forms:section title="form.iasdiary.policy.person.coverage.main.section.title">
									<forms:buttonsection default="btnBack">
										<%--<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.edit" title="button.title.save" />--%>
										<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.title.back" />
									</forms:buttonsection>
								</forms:section>
						    </td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<div align="center">
		<html:form action="/iuauser/policyPersonCoverageMain" styleId="frmPolicyPersonCoverageMain">
			<forms:form formid="policyPersonCoverageMain" caption="form.iasdiary.policy.person.coverage.main.title" type="edit" width="920">
				<forms:html align="center">
					<table>
					<tr>
						<td height="98%" valign="top" align="center">
								<forms:section title="">
									<forms:row>
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.policyid" property="policyid"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.policy.status" property="policystatus"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.status.effective.date" property="policy_effective_date"  width="10" colspan="1" />
									</forms:row>
									<forms:row>	
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.personid" property="personid"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.person.status" property="person_status"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.status.effective.date" property="person_effective_date" width="10" colspan="1" />
									</forms:row>
									<forms:row>	
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.name" property="name"  width="10" colspan="1" />
										<forms:plaintext label="form.iasdiary.policy.person.coverage.main.mode" property="mode"  width="10" colspan="1" />
										<forms:html label="form.iasdiary.policy.person.coverage.main.display.date">
											<ctrl:text  property="display_date"  size="10" maxlength="10"  onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true;"/>
											<ctrl:button name="btnUpdateDisplayDate" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.policy.person.coverage.main.update.display.date" onmouseup="gIasSaveClicked=true"/>									
										</forms:html>
									</forms:row>
								 </forms:section>
							</td>
						</tr>	
						<tr>
							<td valign="top">
								<logic:present scope="session" name="coverages">
									<ctrl:list id="coverages" name="coverages" title="list.iasdiary.policy.person.coverage.main.coverage.list" rows="50"  createButton="false" runat="server" locale="true">
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.coverage.code" property="COVERAGE_CODE"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.coverage.prem" property="ANNUAL_PREM_AMT"	 width="10" align="right"/>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.ss.perm" property="ANNUAL_PERM_EXTRA_PREM_AMT"	 width="10" align="right"/>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.tot.perm" property="TOTAL_PREMIUM"	 width="10" align="right"/>
										<ctrl:columngroup title="list.iasdiary.policy.person.coverage.main.substd" align="center">
											<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.substd.code" property="SUB_STANDARD_RISK_CODE" align="center"/>
											<ctrl:columnbutton  title="list.iasdiary.policy.person.main.edit.sub.std.coverage" text="list.iasdiary.policy.person.main.edit.sub.std.coverage.click" command="editstdcoverage" align="center"/>
										</ctrl:columngroup>
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.eff.perm" property="START_DATE"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.coverage.main.term.date" property="END_DATE"	 width="10" />
									</ctrl:list>
								</logic:present>
								<forms:section title="form.iasdiary.policy.person.coverage.main.section.title">
									<forms:buttonsection default="btnBack">
										<%--<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.edit" title="button.title.save" />--%>
										<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.title.back" onclick="runPageValidation(this)"/>
									</forms:buttonsection>
								</forms:section>
						    </td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>