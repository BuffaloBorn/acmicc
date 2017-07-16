<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<script language='JavaScript' src='app/help/help.js'></script>

<html:form action="/iuauser/riderMain" styleId="frmRiderMain">
	<forms:form formid="riderMain" caption="form.iasdairy.rider.main.title" type="edit" width="750" noframe="false">
		<forms:section title="form.iasdairy.rider.main.section">
			<forms:row>
				<forms:text label="form.iasdairy.rider.main.cert.id" property="cert_id" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.rider.main.coverage" property="coverage" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.rider.main.person.id" property="person_id" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.rider.main.insured" property="insured" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.rider.main.key.ins" property="key_ins" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:radio  label="form.iasdairy.rider.main.restricted" property="restricted"  value="0"  description="form.iasdairy.rider.main.restricted.yes"/>
				<forms:radio  property="restricted"  value="1"  description="form.iasdairy.rider.main.restricted.no"/>
			</forms:row>
			<forms:row>
				<logic:present scope="session" name="RiderDetail">
					<ctrl:list id="riderDetail" name="riderDetail" title="list.iasdairy.rider.main.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
						<ctrl:columntext title="list.iasdairy.rider.main.number" property="number" width="2" />						
						<ctrl:columntext title="list.iasdairy.rider.main.rider" property="rider" width="10" />
						<ctrl:columntext title="list.iasdairy.rider.main.time.period" property="time_period" width="6" />
						<ctrl:columntext title="list.iasdairy.rider.main.start.date" property="start_date" width="5" />
						<ctrl:columntext title="list.iasdairy.rider.main.end.date" property="end_date" width="5" />
						<ctrl:columntext title="list.iasdairy.rider.main.notes" property="notes" width="20" />
					</ctrl:list>
				</logic:present>
			</forms:row>
		</forms:section>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>
</html:form>
