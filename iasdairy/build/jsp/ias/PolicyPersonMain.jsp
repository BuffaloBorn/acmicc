<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/policyPersonMain" styleId="frmEventPortamedic">
	<forms:form formid="policyPersonMain" caption="form.iasdairy.policy.extended.comments.main.title" type="edit" width="750" noframe="false">
		<forms:html align="center">
			<table>
				<tr>
					<td>
						<forms:section title="form.iasdairy.policy.person.main.system.section.title">
							<forms:row>
								<forms:text label="form.iasdairy.policy.person.main.policyid" property="policyid" size="10" width="10" colspan="1" />
								<forms:text label="form.iasdairy.policy.person.main.personid" property="personid" size="10" width="10" colspan="1" />
								<forms:text label="form.iasdairy.policy.person.main.effective.date" property="effective_date" size="10" width="10" colspan="1" />
							</forms:row>
							<forms:row>
								<forms:text label="form.iasdairy.policy.person.main.direct.command" property="direct_command" size="10" width="10" colspan="1" />
							</forms:row>
						</forms:section>
					</td>
				</tr>	
				<tr>
					<td>
						<forms:section title="form.iasdairy.policy.person.main.persons.main.title">
							<forms:button name="btnSubstandardCoverage" text="button.iasdairy.policy.person.main.sub.standard.coverage" title="button.iasdairy.policy.extended.comments" style="padding-left:1px; vertical-align:middle;" />
							<forms:button name="btnRider" text="button.iasdairy.policy.person.main.rider" title="button.iasdairy.policy.person.main.rider" style="padding-left:1px; vertical-align:middle;" />
							<forms:button name="btnPolicyCondCodes" text="button.iasdairy.policy.person.policy.cond.codes" title="button.iasdairy.policy.person.policy.cond.codes" style="padding-left:1px; vertical-align:middle;" />
						</forms:section>
					</td>
				</tr>
				<tr>
					<td>
						<forms:section title="form.iasdairy.policy.person.main.persons.section.title">
							<logic:present scope="session" name="browsePolicyCertPersons">
								<ctrl:list id="browsePolicyCertPersons" name="browsePolicyCertPersons" title="list.iasdairy.policy.person.main.person.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
									<ctrl:columntext title="list.iasdairy.policy.person.main.t" property="t1"	 width="1" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.t" property="t2"	 width="1" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.t" property="t3"	 width="1" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.t" property="t4"	 width="1" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.t" property="t5"	 width="1" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.person" property="person"	 width="10" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.name" property="name"	width="10" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.status" property="status"	 width="10" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.start.date" property="start_date"	width="10" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.martial.status" property="martial_status"	width="10" />
									<ctrl:columntext title="list.iasdairy.policy.person.main.und.rd" property="und_rd"	width="10" />
								</ctrl:list>
							</logic:present>
						</forms:section>
						<forms:buttonsection default="btnBack">
							<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
							<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
						</forms:buttonsection>
					</td>
				</tr>
			</table>
		</forms:html>
	</forms:form>
</html:form>
