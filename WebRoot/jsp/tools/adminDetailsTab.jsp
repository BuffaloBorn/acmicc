<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>

<body>

<ctrl:tabset styleId="tabset5" name="adminDetailTab"
	
	width="100%" runat="server"
	bgcolor="#DADFE0">

	<ctrl:tab tabid="tab121" title="cacheRefresh.title" 
		content="/jsp/tools/refresh.jsp" tooltip="cacheRefresh.title.tooltip">
	</ctrl:tab>
	
	<ctrl:tab tabid="tab122" title="logs.title" 
		content="/jsp/tools/refresh.jsp" tooltip="logs.title.tooltip" enable="false">
	</ctrl:tab>
</ctrl:tabset>

</body>
