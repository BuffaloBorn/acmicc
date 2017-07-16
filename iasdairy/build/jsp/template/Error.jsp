<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<ul>
	<html:messages message="true" id="msg">
		<li class="message"><bean:write name="msg"/></li>
	</html:messages>
</ul>

<html:errors/>
