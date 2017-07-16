<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-menu.tld" prefix="menu"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<jsp:useBean id="current" class="java.util.Date" />

<script type="text/javascript" >
	
	function localResetCookieToClose()
	{
		var expDays = 1; // number of days the cookie should last

		var exp = new Date();
		exp.setTime(exp.getTime() + (expDays*24*60*60*1000));
		
		localSetCookie('iaspopup', 'close',exp, '/acmicc/');
	}
	
	function localSetCookie (name, value) {
	  var argv = localSetCookie.arguments;
	  var argc = localSetCookie.arguments.length;
	  var expires = (argc > 2) ? argv[2] : null;
	  var path = (argc > 3) ? argv[3] : null;
	  var domain = (argc > 4) ? argv[4] : null;
	  var secure = (argc > 5) ? argv[5] : false;
	  
	  document.cookie = name + "=" + escape (value) +
	    ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
	    ((path == null) ? "" : ("; path=" + path)) +
	    ((domain == null) ? "" : ("; domain=" + domain)) +
	    ((secure == true) ? "; secure" : "");
	}

</script>

<TABLE WIDTH="100%" height="40" NOwrap BACKGROUND="fw/def2/image/headerTitle.gif">
	<tr>
		<%-- Image --%>
		<td NOWRAP width="570"></td>

		<%-- Login Info --%>
		<td nowrap width="350">
			<table cellpadding="0" cellspacing="0" border="0" style="font-family:Arial;font-size:8pt;">
				<tr>
					<logic:present scope="session" name="USER">
						<td style="padding-left:11px;" class="small_legend">
							<b>
								User:
							</b>
						</td>
						<td style="padding-left:8px;" nowrap class="small_legend">
							<bean:write name="USER" property="firstName" />
							<bean:write name="USER" property="lastName" />
						</td>
						<td nowrap style="padding-left:11px;" class="small_legend">

							<b>
								Role(s):
							</b>
						</td>
						<td style="padding-left:8px;" nowrap class="small_legend">
							<font class="small_legend">
								<bean:write name="USER" property="role" />
							</font>
						</td>
					</logic:present>
				</tr>
				<tr>
					<logic:present scope="session" name="USER">
						<td style="padding-left:11px;" colspan="3" class="small_legend">
							<b>
								Date:
							</b>
							&nbsp;
							<fmt:formatDate value="${current}" />
							&nbsp;
						</td>
						<td align="right" class="small_legend">
							<a href="logout.do" onclick="localResetCookieToClose()">
								Logout
							</a>
							&nbsp;&nbsp;
						</td>
					</logic:present>
				</tr>
			</table>
		</td>
	</tr>
</table>
