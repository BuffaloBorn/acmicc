<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/policyPersonCoverageMain" styleId="frmPolicyPersonCoverageMain">
	<forms:form formid="policyPersonCoverageMain" caption="form.iasdairy.amendment.main.title" type="edit" width="920">
		<forms:section title="">
			<forms:row>
				<forms:text label="form.iasdairy.policy.person.coverage.main.policyid" property="policyid" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.policy.status" property="policystatus" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.status.effective.date" property="status_effective_date1" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.policy.person.coverage.main.personid" property="personid" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.person.status" property="person_status" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.status.effective.date" property="status_effective_date2" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.policy.person.coverage.main.name" property="name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.mode" property="mode" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.policy.person.coverage.main.display.date" property="display_date" size="10" width="10" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdairy.policy.person.coverage.main.coverage.section.title">
			<logic:present scope="session" name="coverages">
				<ctrl:list id="coverages" name="coverages" title="list.iasdairy.policy.person.coverage.main.coverage.list" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.coverage.code" property="coverage_code"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.coverage.prem" property="coverage_prem"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.substd" property="substd"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.ss.perm" property="ss_perm"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.tot.perm" property="tot_perm"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.eff.perm" property="eff_perm"	 width="10" />
					<ctrl:columntext title="list.iasdairy.policy.person.coverage.main.term.date" property="term_date"	 width="10" />
				</ctrl:list>
			</logic:present>
		</forms:section>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>
</html:form>