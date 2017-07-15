<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<script language="javascript">
	function ConfirmChoice()
	{
			var answer = confirm("Document will be marked for delete. Do you want to continue?");
			if (answer !=0)
			{
				return true;
				document.body.style.cursor = "wait";
			} else
			{
				return false;
			}
	}
	
</script>
<table height="400" width="100%">
	<tr>
		<td height="10" colspan="2" class="legend" align="right" width="100%">Back
		to <a href="worklist.do">WorkList</a></td>
	</tr>
	<tr>
		<logic:present scope="session" name="expdoclist">
			<td valign="top" height="35"><forms:info width="100%">
				Document(s) with exceptions are displayed below.  Click the <b>View</b> link to view the document.  Click the <b>Edit</b> link to edit the document metadata. Click the <b>Delete</b> link to mark the document for deletion.  Click the <base:image src="fw/def2/image/buttons/btnRefresh1.gif"
					alt="Refresh" /> image button on the grid header to refresh the information displayed.
		</forms:info></td>
		</logic:present>
		<logic:notPresent scope="session" name="expdoclist">
			<td valign="top" height="30"><forms:info>
				Currently there are no <B>Document(s)</b> in <b> Document Exceptions Queue</B>
			</forms:info></td>
		</logic:notPresent>
	</tr>
	<logic:present scope="session" name="expdoclist">
		<tr>
			<td valign="top"><ctrl:list id="doclist" name="expdoclist"
				title="doc.exception" rows="20" action="/main/docexep"
				refreshButton="true" runat="server" locale="true">
				<fmt:formatDate value="${scanDate}" />
				<ctrl:columnhtml title="doc.view" sortable="false" width="70"
					property="URL" />
				<ctrl:columntext title="doc.doctype" property="docType"
					sortable="true" width="160" />
				<ctrl:columntext title="doc.description" property="docDescription"
					sortable="true" width="170" />
				<ctrl:columntext title="doc.keyappfn" property="keyAppFN"
					sortable="true" width="170" />
				<ctrl:columntext title="doc.applastname" property="appLastName"
					sortable="true" width="120" />
				<ctrl:columntext title="doc.appfirstname" property="appFirstName"
					sortable="true" width="120" />
				<ctrl:columntext title="doc.receiptdate" property='scanDate' 
					sortable="true" width="90" />
				<ctrl:columnedit title="list.edit" />
				<ctrl:columndelete title="list.delete"
					onclick="return ConfirmChoice();" />
			</ctrl:list></td>
		</tr>

	</logic:present>
	<logic:notPresent scope="session" name="expdoclist">
		<tr>
			<td height="360">&nbsp;</td>
		</tr>
	</logic:notPresent>
</table>
