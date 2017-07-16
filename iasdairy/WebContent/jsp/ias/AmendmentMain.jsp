<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/amendmentMain" styleId="frmAmendmentMain">
	<forms:form formid="amendmentMain" caption="form.iasdairy.amendment.main.title" type="edit" width="920">
		<forms:section>
			<forms:row>
				<forms:text label="form.iasdairy.amendment.main.policyid" property="policyid" size="10" width="10" colspan="1" />
				<forms:text label="form.iasdairy.amendment.main.name" property="name" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
                <forms:radio  label="form.iasdairy.amendment.main.wrong.app"  property="wrong_app"  value="0"  description="opt.iasdairy.amendment.main.need.trust"/>
                <forms:radio  property="wrong_app"  value="1"  description="opt.iasdairy.amendment.main.proxy"/>
            </forms:row>
            <forms:row>
				<forms:text label="form.iasdairy.amendment.main.description" property="description" size="10" width="10" colspan="1" />
			</forms:row>
			<forms:row>
				<forms:textarea label="form.iasdairy.amendment.main.amendment.text" property="amendmentTextArea" cols="64" rows="6" maxlength="158" valign="top"/>
			</forms:row>
		</forms:section>
		<forms:buttonsection default="btnBack">
			<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
			<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
		</forms:buttonsection>
	</forms:form>
</html:form>

