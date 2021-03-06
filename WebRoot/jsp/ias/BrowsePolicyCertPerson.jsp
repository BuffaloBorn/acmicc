<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<html:form action="/iuauser/browsePolicyCertPerson" styleId="frmBrowsePolicyCertPerson">
	<forms:form formid="browsePolicyCertPerson" caption="form.iasdiary.browse.policy.cert.person.title" type="edit" width="920">
		<forms:html align="center">
			
				<logic:present scope="session" name="browsePersons">
					<ctrl:list id="browsePersons" name="browsePersons" title="list.iasdiary.browse.policy.cert.person.title" rows="50"  createButton="false" refreshButton="false" runat="server" locale="true">
						<ctrl:columntext title="list.iasdiary.browse.policy.cert.person.policy.id" property="POLICY_ID"	 width="10" />
						<ctrl:columndrilldown title="list.iasdiary.browse.policy.cert.person.person.id" property="PERSON_ID"  width="10" />
						<ctrl:columntext title="list.iasdiary.browse.policy.cert.person.person.search" property="PERSON_SEARCH_NAME1" width="120" />
					</ctrl:list>
				</logic:present>
			
			<forms:buttonsection default="btnBackEvent">			
				<c:if test="${sessionScope.policyPersonCodesComingForm == 'FT'}">
					<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.free.tx" title="button.title.back.event.free.tx" />	
				</c:if>
				
				<c:if test="${sessionScope.policyPersonCodesComingForm == 'SL'}">
					<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.std.letter" title="button.title.back.event.std.letter" />	
				</c:if>
				
				<c:if test="${sessionScope.policyPersonCodesComingForm == 'EP'}">
					<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.portmedic" title="button.title.back.event.portmedic" />	
				</c:if>
				
				<c:if test="${sessionScope.policyPersonCodesComingForm == 'ESM'}">
					<forms:button base="buttons.src.def2" name="btnBackEvent" text="button.title.back.event.std.memo" title="button.title.back.event.std.memo" />	
				</c:if>
				
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:html>
	</forms:form>
</html:form>

