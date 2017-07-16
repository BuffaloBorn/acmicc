<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>

<script LANGUAGE="JavaScript" TYPE="text/javascript" SRC="app/js/popupiasdiary.js" CHARSET="ISO-8859-1"></script>

<util:imagemap name="im_tabs">
	<util:imagemapping rule="user" src="app/images/user/user.gif"
		width="16" height="16" />
</util:imagemap>
<%
  com.cc.acmi.common.User user = (com.cc.acmi.common.User) session.getAttribute("USER");
  String role = user.getRole();

%>
<ctrl:tabset styleId="tabset1" name="maintabset"
	action="main/tabsetBrowse" tabs="4" labellength="20" width="920"
	imagemap="im_tabs" runat="server" height="250">

	<ctrl:tab tabid="tab1" action="/main/iuauser"
		title="tabset1.tab1.title" content="Main_Tab_Page1.jsp"
		tooltip="tabset1.tab1.tooltip" imageref="user" onclick="gTabClick=true" />
<%
  if (role.indexOf("MSM") >= 0 || role.indexOf("UA") >= 0 || role.indexOf("MRT") >= 0 || role.indexOf("UAM") >= 0) {
%>

	<ctrl:tab tabid="tab2" action="/main/actpend"
		title="tabset1.tab2.title" content="Main_Tab_Page2.jsp"
		tooltip="tabset1.tab2.tooltip" onclick="gTabClick=true"/>
<%
 } 
  if (role.indexOf("MSM") >= 0 || role.indexOf("UAM") >= 0) {
%>	
	<ctrl:tab tabid="tab3" action="/main/reassign"
		title="tabset1.tab3.title" content="Main_Tab_Page3.jsp"
		tooltip="tabset1.tab3.tooltip" onclick="gTabClick=true"/>
<%-- removed from here "<% } %>" and placed it at the bottom to fix issue #61 cmontero 03/20/06--%>
	<ctrl:tab tabid="tab4" action="/main/docexep"
		title="tabset1.tab4.title" content="Main_Tab_Page4.jsp"
		tooltip="tabset1.tab4.tooltip" />
<% } %>
	</ctrl:tabset>

