<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/memoIdCodes">
	<forms:form formid="memoIdCodesBrowse" caption="form.iasdiary.memo.id.code.title" type="edit" width="920">
		<forms:html label="form.iasdiary.browse.rider.codes.search">
			<ctrl:text property="SEARCH_CODE" width="10" />
			<ctrl:button name="btnUpdateSearchCode" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.memo.id.codes.search" />
		</forms:html>
		<forms:html align="center">
			<forms:info width="100%">
					Browse Help Memo Codes are displayed below.  Click the <b>Memo Code</b> link to return previous screen with that Memo Code.  Click the <b>To Policy Diary Maintenance</b> button to go back to Policy diary Maintenance tab. Click the <base:image src="fw/def2/image/buttons/btnNext1.gif"
					alt="Next" /> image button to retrieve the next 50 Memo Codes to display.
			</forms:info>
			<ctrl:list id="memoIdCodesBrowseid" name="memoIdCodesBrowse" title="list.iasdiary.memo.id.code.title" rows="50"  createButton="false" refreshButton="false" runat="server" locale="true">
					<ctrl:columndrilldown title="list.iasdiary.memo.id.code.memo.id" property="MEMO_ID" width="8" />
					<ctrl:columntext title="list.iasdiary.memo.id.code.description" property="DESCRIPTION" width="50" />
					<ctrl:columntext title="list.iasdiary.memo.id.code.attachment.ind" property="ATTACHMENT_IND" width="1" />
					<ctrl:columntext title="list.iasdiary.memo.id.code.application.form.id" property="APPLICATION_FORM_ID" width="20" />
					<ctrl:columntext title="list.iasdiary.memo.id.code.effect.date.request.ind" property="EFF_DT_REQ_IND" width="1" />
			</ctrl:list>
			<forms:section title="form.iasdiary.memo.id.codes.main.section.title">
				<forms:buttonsection default="btnBackEvent">
					<c:if test = "${requestScope.MORE_CODES == 'Y'}">
						<forms:button base="buttons.src.def2" name="btnMore"  text="button.title.more" title="button.title.more" />
					</c:if>
					
					<c:if test="${sessionScope.memoIdCodesComingForm == 'FT'}">
						<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.free.tx" title="button.title.back.event.free.tx" />	
					</c:if>
					
					<c:if test="${sessionScope.memoIdCodesComingForm == 'SL'}">
						<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.std.letter" title="button.title.back.event.std.letter" />	
					</c:if>
					
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:section>
		</forms:html>
		
	</forms:form>
</html:form>

