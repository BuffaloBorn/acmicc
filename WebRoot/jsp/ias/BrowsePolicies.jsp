<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ page import="com.softwarag.extirex.webservice.browsepolicies.client.holders.MUPLYBWResponseINOUT_PARM1Holder" %>

<%

	MUPLYBWResponseINOUT_PARM1Holder inoutparmsresponse = (MUPLYBWResponseINOUT_PARM1Holder) session.getAttribute("inoutparmsresponse");
	
	if (inoutparmsresponse != null)
		pageContext.setAttribute("scrollValue",  inoutparmsresponse.value.getFORWARDCOUNT1());		
	else
		pageContext.setAttribute("scrollValue",  "1");
	
	
%>


<html:form action="/iuauser/browsePolicies">
	<forms:form formid="browsePolicies" caption="form.iasdiary.browse.policies.title" type="edit" width="920">
		<forms:html label="form.iasdiary.browse.policies.key.insured.search">
			<ctrl:text property="KEY_INSURED_SEARCH_NAME" width="10" />
			<ctrl:button name="btnUpdateSearchCode" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.memo.id.codes.search" />
		</forms:html>
		<forms:html align="center">
			<logic:present scope="session" name="browsePolicies">
				<forms:info width="100%">
					Browse Help Policies are displayed below.  Click the <b>Policy Id</b> link to return to Policy Diary Maintenance with that Policy Id.  Click the <b>To Policy Diary Maintenance</b> button to go back to Policy Diary Maintenance. Click the <b>Previous 50 Policies</b> button to get the previous 50 policies to display. Click the <b>Next 50 Policies</b> button to get the next 50 policies to display.
				</forms:info>
				<ctrl:list id="browsePoliciesid" name="browsePolicies" title="list.iasdiary.browse.policies.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columntext title="list.iasdiary.browse.policies.key.insured" property="KEY_INSURED_SEARCH_NAME" width="10" />
					<ctrl:columndrilldown title="list.iasdiary.browse.policies.policyid" property="POLICY_ID" width="10" />
					<ctrl:columntext title="list.iasdiary.browse.policies.key.insured.person.id" property="KEY_INSURED_PERSON_ID" width="10" />
					<ctrl:columntext title="list.iasdiary.browse.policies.product" property="KEY_PRODUCT_ID" width="120" />
				</ctrl:list>
				<forms:section title="form.iasdiary.browse.policies.main.section.title">
					<forms:buttonsection default="btnBack">
						<c:if test = "${pageScope.scrollValue != '1'}">
							<forms:button base="buttons.src.def2" name="btnBackward"  text="button.title.backward" title="button.title.backward" />
						</c:if>
						<forms:button base="buttons.src.def2" name="btnForward"  text="button.title.forward" title="button.title.forward" />
						<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
					</forms:buttonsection>
				</forms:section>
			</logic:present>
		</forms:html>
	</forms:form>
</html:form>

