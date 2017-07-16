<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-menu.tld" prefix="menu"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<jsp:useBean id="current" class="java.util.Date" />



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
					</logic:present>
					<logic:present scope="session" name="USER">
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
							<a href="logout.do">
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
