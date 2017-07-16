<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html:form action="/iuauser/subStdCoverageMain" styleId="frmSubStdCoverageMain">
	<forms:form formid="subStdCoverageMai" caption="form.iasdairy.sub.std.coverage.main.title" type="edit" width="750" noframe="false">
		<forms:section title="form.iasdairy.sub.std.coverage.main.section">
			<forms:row>
				<forms:text label="form.iasdairy.sub.std.coverage.main.policyid" property="policyid" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.status" property="policy_status" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.status.effective.date" property="policy_status_effective_date" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.sub.std.coverage.main.policyid" property="personid" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.status" property="person_status" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.status.effective.date" property="person_status_effective_date" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.sub.std.coverage.main.name" property="name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.mode" property="mode" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.display.date" property="display_date" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.sub.std.coverage.main.coverage.code" property="coverage_code" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.ss.code" property="ss_code" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.next.due.date" property="next_due_date" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.sub.std.coverage.main.start.date" property="coverage_code" size="10" width="10" colspan="6" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.term.date" property="term_date" size="10" width="10" colspan="6" />
				<forms:text label="form.iasdairy.sub.std.coverage.main.time.period" property="time_period" size="10" width="10" colspan="6" />
			</forms:row>
		</forms:section>
		<logic:present scope="session" name="coveragecodes">
			<ctrl:list id="coveragecodes" name="coveragecodes" title="list.iasdairy.sub.std.coverage.main.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
				<ctrl:columntext title="list.iasdairy.sub.std.coverage.main.condition.code" property="condition_code" width="10" />
				<ctrl:columntext title="list.iasdairy.sub.std.coverage.main.text" property="coverage_text"	 width="20" />
			</ctrl:list>
		</logic:present>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>
</html:form>

