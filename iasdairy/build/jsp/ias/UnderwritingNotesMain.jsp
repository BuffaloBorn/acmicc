<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/underwritingNotesMain">
	<forms:form formid="underwritingNotesMain" caption="form.iasdairy.policy.underwriting.notes.main.title" type="edit" width="750" noframe="false">
		<forms:section title="Details.std.actions">
			<forms:row>
				<forms:text label="form.iasdairy.policy.underwriting.notes.main.policyid" property="policy_id" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>	
				<forms:text label="form.iasdairy.policy.underwriting.notes.main.description" property="description" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:textarea label="form.iasdairy.policy.underwriting.notes.main.notes" property="notesArea" cols="64" rows="5" maxlength="158" valign="top"/>
			</forms:row>
		</forms:section>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>	

</html:form>