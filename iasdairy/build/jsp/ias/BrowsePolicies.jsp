<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>


<script language="Javascript">
	function goPolicyDairyScreen()
	{
		alert("goPolicyDairyScreen");
	}
	
	function showMorePolicyBrowse()
	{
		alert("showMorePolicyBrowse");
	}

</script>

<html:form action="/iuauser/browsePolicies">
	<forms:form formid="browsePolicies" caption="form.iasdairy.browse.policies.title" type="edit" width="920">
		<forms:html align="center">
			<logic:present scope="session" name="browsePolicies">
				<forms:info width="100%">
					Browse Help Policies are displayed below.  Click the <b>Policy Id</b> link to return Policy Dairy with that Policy Id.  Click the <b>Back</b> button to go back to Policy Dairy Maintenance tab. Click the <b>More</b> button to get the next 50 policies to display.
				</forms:info>
				<ctrl:list id="browsePoliciesid" name="browsePolicies" title="list.iasdairy.title" rows="50"  createButton="false" refreshButton="true" runat="server" locale="true">
					<ctrl:columntext title="list.iasdairy.browse.policies.key.insured" property="KEY_INSURED_SEARCH_NAME"	sortable="true" width="10" />
					<ctrl:columndrilldown title="list.iasdairy.browse.policies.policyid" property="POLICY_ID" sortable="true" width="10" />
					<ctrl:columntext title="list.iasdairy.browse.policies.key.insured.person.id" property="KEY_INSURED_PERSON_ID" sortable="true" width="10" />
					<ctrl:columntext title="list.iasdairy.browse.policies.product" property="KEY_PRODUCT_ID" sortable="true" width="120" />
				</ctrl:list>
				<forms:buttonsection default="btnBack">
					<c:if test = "${sessionScope.moreresponse.more_ind == 'Y'}">
						<forms:button base="buttons.src.def2" name="btnMore"  text="button.title.more" title="button.title.more" />
					</c:if>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>
			</logic:present>
		</forms:html>
	</forms:form>
</html:form>

