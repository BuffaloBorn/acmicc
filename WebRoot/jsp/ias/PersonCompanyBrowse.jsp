<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/personCompanyBrowse">
	<forms:form formid="personCompanyBrowse" caption="form.iasdiary.person.company.browse.title" type="edit" width="920">
		<forms:html align="center">
			<ctrl:list id="personCompanyBrowseid" name="personCompanyBrowse" title="list.iasdiary.person.company.browse.list" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columndrilldown title="list.iasdiary.person.company.browse.person.company.id" property="person_company_id" width="10" />
					<ctrl:columntext title="list.iasdiary.person.company.browse.seach.name" property="seach_name" width="10" />
					<ctrl:columntext title="list.iasdiary.person.company.browse.ty" property="ty" width="120" />
			</ctrl:list>
			<forms:buttonsection default="btnBack">
				<c:if test = "${requestScope.PC_MORE_REC == 'Y'}">
					<forms:button base="buttons.src.def2" name="btnMore"  text="button.title.more" title="button.title.more" />
				</c:if>
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:html>
	</forms:form>
</html:form>

