<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>

<%com.cc.acmi.common.User user = (com.cc.acmi.common.User) session.getAttribute("USER");
  String role = user.getRole();
%>

<util:imagemap name="im_tabs">
	<util:imagemapping rule="resume" src="app/images/resume1.gif"
		width="16" height="16" />
	<util:imagemapping rule="list" src="app/images/list.gif"
		width="16" height="16" />
</util:imagemap>


<ctrl:tabset styleId="tabset1" name="secondarymaintabset"
	action="/main/secondarytabsetBrowse" tabs="4" labellength="20" width="920"
	imagemap="im_tabs" runat="server" height="250">

	<ctrl:tab tabid="tab5" action="/worklist"
		title="tabset1.tab5.title" content="/jsp/worklist/workListContent.jsp"
		tooltip="tabset1.tab1.tooltip" imageref="list" />
<% 
  if (role.indexOf("MSM") >= 0 || role.indexOf("UA") >= 0 || role.indexOf("NC") >= 0 || role.indexOf("MRT") >= 0 || role.indexOf("UAM") >= 0) {
%>
	<ctrl:tab tabid="tab2" action="/secondarymain/actpend"
		title="tabset1.tab2.title" content="SecondaryMain_Tab_Page2.jsp"
		tooltip="tabset1.tab2.tooltip"/>
<%
} 
  if (role.indexOf("MSM") >= 0 || role.indexOf("UAM") >= 0) {
%>		
	<ctrl:tab tabid="tab3" action="/secondarymain/reassign"
		title="tabset1.tab3.title" content="SecondaryMain_Tab_Page3.jsp"
		tooltip="tabset1.tab3.tooltip" />
<%-- removed from here "<% } %>" and placed it at the bottom to fix issue #61 cmontero 03/22/06--%>
	<ctrl:tab tabid="tab4" action="/secondarymain/docexep"
		title="tabset1.tab4.title" content="SecondaryMain_Tab_Page4.jsp"
		tooltip="tabset1.tab4.tooltip" />
<%
}
%>

</ctrl:tabset>
