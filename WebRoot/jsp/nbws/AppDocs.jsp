<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%session.setAttribute("pageContext", pageContext); %>


<script type="text/javascript">	
window.onload = addherfListenersToSort;
</script>

<script language="javascript">

	
	function ConfirmChoice()
	{
			gTabClick=true;
			var answer = confirm("The selected document will removed from this application. Do you want to continue?");
			if (answer !=0)
			{
				return true;
			} else
			{
				return false;
			}
		
	}
	
</script>


<div align="center">
<table height="400" width="900">
	<tr>
		<logic:present scope="session" name="doclist">
			<td valign="top" height="35"><forms:info width="100%">
					Document(s) associated to this application are displayed below.  Click the <b>View</b> link to view the document.  Click the <b>Edit</b> link to edit the information related to the document.  Click the <b>Remove</b> link to remove the document from this application and place it in Document Exceptions.  Click the <base:image src="fw/def2/image/buttons/btnRefresh1.gif"
					alt="Refresh" /> image button on the grid header to refresh the information displayed.
		</forms:info></td>
		</logic:present>
		<logic:notPresent scope="session" name="doclist">
			<td valign="top" height="30"><forms:info width="100%">
				Currently there no <B>Document(s)</b> assiociated with this <b> <font
					style="color:blue">Application.</font></B>
			</forms:info></td>
		</logic:notPresent>

	</tr>
	<logic:present scope="session" name="doclist">
		<tr>
			<td valign="top"><ctrl:list id="doclist" name="doclist"
				title="doc.application" rows="25" action="/main/iuauser"
				refreshButton="true" runat="server" locale="true" width="100%">
				<fmt:formatDate value="${scanDate}" />
				<ctrl:columnhtml title="doc.view" sortable="false" property="URL" />
				<ctrl:columntext title="doc.doctype" property="docType"
					sortable="true" />
				<ctrl:columntext title="doc.description" property="docDescription"
					sortable="true" />
				<ctrl:columntext title="doc.appfn" property="appFN"
					sortable="true"/>	
				<ctrl:columntext title="doc.receiptdate" property='scanDate'
					sortable="true" />
				<ctrl:columntext title="doc.reviewed" property='reviewed'
					sortable="false" />	
				<ctrl:columntext title="doc.copyforpolicy" property="CFP" width="40" />
				<ctrl:columntext title="doc.annotate" property="annotate" width="40" />
				<ctrl:columnedit title="list.edit" onclick="gTabClick=true"/>
				<ctrl:columndelete title="list.remove"
					onclick="return ConfirmChoice();" />
			</ctrl:list></td>
		</tr>
	</logic:present>

</table>

</div>
