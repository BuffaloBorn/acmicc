<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ page import="com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseINOUT_PARM1Holder" %>
<%@ page import="com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseMSG_INFOHolder" %>

<%
	ACRIDBWResponseINOUT_PARM1Holder inoutparmsresponse = (ACRIDBWResponseINOUT_PARM1Holder ) session.getAttribute("inoutparmsRiderCodesResponse");
	ACRIDBWResponseMSG_INFOHolder msgInfoRiderCodesResponse =	(ACRIDBWResponseMSG_INFOHolder) session.getAttribute("msgInfoRiderCodesResponse");
	
	if (inoutparmsresponse != null)
		pageContext.setAttribute("scrollValue",  inoutparmsresponse.value.getFORWARDCOUNT1());		
	else
		pageContext.setAttribute("scrollValue",  "1");
	
	if (msgInfoRiderCodesResponse != null)
	{
		pageContext.setAttribute("returnCode",  msgInfoRiderCodesResponse.value.getRETURN_CODE());	
		session.removeAttribute("msgInfoRiderCodesResponse");
	}
	else
		pageContext.setAttribute("returnCode",  "none");
	
%>

<html:form action="/iuauser/browseRiderCodes">
	<forms:form formid="browseRiderCodes" caption="form.iasdiary.browse.rider.codes.title" type="edit" width="920">
		<forms:html label="form.iasdiary.browse.rider.codes.search">
			<ctrl:text property="SEARCH_CODE" width="10" />
			<ctrl:button name="btnUpdateSearchCode" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.browse.rider.codes.search" />
		</forms:html>
		<forms:html align="center">
			<logic:present scope="session" name="riderCodeList">
				<forms:info width="100%">
					Browse Rider Codes are displayed below.  Click the <b>Rider Code</b> link to return to Rider Maintenance with that Rider Code.  Click the <b>To Riders Maintenance</b> button to go back to Rider Maintenance. Click the <b>Next 25 Riders</b> button to get the next 25 codes to display.
				</forms:info>
				<ctrl:list id="riderCodeListid" name="riderCodeList" title="list.iasdiary.browse.rider.codes.title" rows="25"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columndrilldown title="list.iasdiary.browse.rider.codes.code" property="RIDER_CODE" width="10" />
					<ctrl:columntext title="list.iasdiary.browse.rider.codes.description" property="DESCRIPTION" width="10" />
					<ctrl:columntext title="list.iasdiary.browse.rider.codes.start.ind" property="STATE_IND" width="10" />
					<ctrl:columntext title="list.iasdiary.browse.rider.codes.state.code" property="STATE_CODE" width="10" />
				</ctrl:list>
				<forms:section title="form.iasdiary.policy.person.coverage.main.section.title">
					<forms:buttonsection default="btnBack">
						<c:if test = "${pageScope.scrollValue != '1'}">
							<forms:button base="buttons.src.def2" name="btnBackward"  text="button.title.backward.riders" title="button.title.backward.riders" />
						</c:if>
						<c:if test = "${pageScope.returnCode == 'none'}">	
							<forms:button base="buttons.src.def2" name="btnForward"  text="button.title.forward.riders" title="button.title.forward.riders" />
						</c:if>
						<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back.riders" title="button.title.back.riders" />
					</forms:buttonsection>
				</forms:section>
			</logic:present>
		</forms:html>
	</forms:form>
</html:form>
