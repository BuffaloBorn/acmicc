:<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script language="JavaScript" src='app/help/help.js'></script>

<html:form action="/iuauser/personCompanyMain" styleId="frmPersonCompanyMain">
	<forms:form formid="personCompanyMain" caption="form.iasdairy.person.company.main.title" type="edit" width="750" noframe="false">
		<forms:section title="form.iasdairy.person.company.main.person.section.title">
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.personid" property="personid" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.person.first.name" property="first_name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.person.title" property="person_title" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.person.middle.name" property="middle_name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.person.honorifics" property="honorifics" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.person.last.name" property="last_name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.person.suffix" property="suffix" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.person.relationship" property="last_name" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.person.dependof" property="dependof" size="10" width="10" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdairy.person.company.main.personal.section.title">
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.personal.sex" property="sex" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.birth.date" property="birth_date" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.birth.place" property="birth_place" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.disability" property="disability" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.personal.marital.status" property="marital_status" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.height" property="height" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.weight" property="weight" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.personal.smoker" property="smoker" size="10" width="10" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdairy.person.company.main.occ.section.title">
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.occ.occupation" property="occupation" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.student" property="student" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.college" property="college" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.occ.employer" property="employer" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.length" property="length" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.occ.month.income" property="month_income" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.business.phone" property="business_phone" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.res.phone" property="res_phone" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.occ.social.sec" property="social_sec" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.drivers.license" property="drivers_license" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.occ.state" property="state" size="10" width="10" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:section title="form.iasdairy.person.company.main.company.section.title">
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.company.name" property="company_name" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.company.address" property="company_address" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:text label="form.iasdairy.person.company.main.company.mib.terr" property="mib_terr" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.company.dependents" property="dependents" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.company.no.in.array" property="no_in_array" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.person.company.main.company.app.declined" property="app_declined" size="10" width="10" colspan="1" />
			</forms:row>
		</forms:section>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>
</html:form>