<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/memoIdCodes">
	<forms:form formid="memoIdCodesBrowse" caption="form.iasdairy.memo.id.code.title" type="edit" width="920">
		<forms:html align="center">
			<ctrl:list id="memoIdCodesBrowseid" name="memoIdCodesBrowse" title="list.iasdairy.memo.id.code.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columndrilldown title="list.iasdairy.memo.id.code.memo.id" property="MEMO_ID" width="8" />
					<ctrl:columntext title="list.iasdairy.memo.id.code.description" property="DESCRIPTION" width="50" />
					<ctrl:columntext title="list.iasdairy.memo.id.code.attachment.ind" property="ATTACHMENT_IND" width="1" />
					<ctrl:columntext title="list.iasdairy.memo.id.code.application.form.id" property="APPLICATION_FORM_ID" width="20" />
					<ctrl:columntext title="list.iasdairy.memo.id.code.effect.date.request.ind" property="EFF_DT_REQ_IND" width="1" />
			</ctrl:list>
			<forms:buttonsection default="btnBack">
				<c:if test = "${requestScope.MORE_CODES == 'Y'}">
					<forms:button base="buttons.src.def2" name="btnMore"  text="button.title.more" title="button.title.more" />
				</c:if>
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:html>
	</forms:form>
</html:form>

