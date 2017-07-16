<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>

<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>

<body>

<table width="100%">
    <tr>
		<td class="legend" align="right" height="8" width="100%">
		Back to <a href="main/secondarytabsetBrowse.do?ctrl=secondarymaintabset&action=TabClick&param=tab5">WorkList</a></td>
	</tr>
</table>

<ctrl:tabset styleId="tabset3" action="/secondarymain/refresh" name="adminTab"
	
	width="100%" runat="server"
	bgcolor="#DADFE0">

	<ctrl:tab tabid="tab120" title="adminTab.title" 
		content="/jsp/tools/adminDetailsTab.jsp" tooltip="adminTab.title.tooltip">
	</ctrl:tab>
</ctrl:tabset>

</body>
