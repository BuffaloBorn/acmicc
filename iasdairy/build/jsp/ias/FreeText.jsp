<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<%--<script language='JavaScript' src='app/help/help.js'></script>--%>
<%
	String formType = null;
	String formAction = null;
	String modifyStatus = null;
	
	formType = request.getParameter("action");
	modifyStatus =  request.getParameter("modify");
	
	if (formType == null)
	{
		formType = "edit";
	}
	
	if (modifyStatus == null)
	{
		modifyStatus = "notShow";
	}
	
	if (formType.equalsIgnoreCase("Help"))
	{
		formType = "edit";
	}
	
	if (formType.equalsIgnoreCase("edit"))
	{
		formAction = "/iuauser/freeTextCreate";
	}
	else
	{
		formAction = "/iuauser/freeText";	
	}
	
	 pageContext.setAttribute("formType", formType);
	 pageContext.setAttribute("modifyStatus", modifyStatus);
%>
<div align="center">
	<html:form action="<%= formAction%>" styleId="frmFreeText">
		<forms:form formid="freeText" caption="form.iasdairy.freeText.title" type="<%= formType%>" width="750" noframe="false">
			<forms:section>
				<forms:row>
					<forms:plaintext label="form.iasdairy.freeText.policy.no" property="policyno" />
					<%--<forms:text label="form.iasdairy.freeText.policy.no" property="policyno" size="15" maxlength="15" colspan="1" />--%>
					<forms:plaintext label="form.iasdairy.freeText.event.id" property="event_id" />
					<%--<forms:text label="form.iasdairy.freeText.event.id" property="event_id" size="9" maxlength="9" colspan="1"/>--%>
					<forms:plaintext label="form.iasdairy.freeText.std.event" property="std_event" />
				</forms:row>
				<forms:row>
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:text label="form.iasdairy.freeText.description" property="description" maxlength="60" size="60" colspan="1" />
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.freeText.description" property="description" />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:select id="memindid" label="form.iasdairy.freeText.memoind" property="memoind" size="1">
								<base:options property="memoindOptions" keyProperty="key" labelProperty="value" />
							</forms:select>
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.freeText.memoind" property="memoind" />
						</c:otherwise>
					</c:choose>
					<%--<forms:text label="form.iasdairy.freeText.attachement" property="attachment" size="1" maxlength="1" colspan="1"/>--%>
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:select id="attachmentid"	label="form.iasdairy.freeText.attachment" property="attachment" size="1">
								<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
							</forms:select>	
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.freeText.attachment" property="attachment" />
						</c:otherwise>
					</c:choose>
				</forms:row>
				<forms:row>
					<forms:group orientation="horizontal">
						<c:choose>
							<c:when test="${pageScope.formType == 'display'}">
								<forms:text label="form.iasdairy.freeText.Recipient" property="recipient_id" maxlength="9" size="9" colspan="1">
									<forms:label label="form.iasdairy.freeText.Recipient" />
								</forms:text>
							</c:when>
							<c:otherwise>
								<%--<% String policyno = (String)session.getAttribute(Constants.IASpolicyNumber);--%>
								<%--String help = HelpUtil.getFreeTextPersonBrowsePolicyCertPersonHelp(policyno);%>--%>
								<c:choose>
									<c:when test="${pageScope.modifyStatus == 'notShow'}">
										<forms:text label="form.iasdairy.freeText.Recipient" property="recipient_id" maxlength="9" size="9" colspan="1">
											<forms:label label="form.iasdairy.freeText.Recipient" />
										</forms:text>
										<forms:html>
											<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" />
										</forms:html>
									</c:when>
									<c:otherwise>
										<forms:plaintext label="form.iasdairy.freeText.Recipient" property="recipient_id" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${pageScope.modifyStatus == 'notShow'}">
								<forms:text property="recipient_name" maxlength="255" size="65"	colspan="1" join="true" />
							</c:when>
							<c:otherwise>
								<forms:plaintext property="recipient_name" />
							</c:otherwise>
						</c:choose>
					
						<c:choose>
							<c:when test="${pageScope.formType == 'display'}">
								<forms:text label="form.iasdairy.freeText.memoid" property="memoid" size="8" maxlength="8" colspan="1" />
							</c:when>
							<c:otherwise>
									<%--<%--%>
									<%--String policyno = (String) session.getAttribute(Constants.IASpolicyNumber);--%>
									<%--String help = HelpUtil.getFreeTextMemoIdHelp(policyno);--%>
									<%--%>--%>
									<c:choose>
										<c:when test="${pageScope.modifyStatus == 'notShow'}">
											<forms:text label="form.iasdairy.freeText.memoid" property="memoid" size="8" maxlength="8" colspan="1" />
											<forms:html>
												<ctrl:button name="btnMemoIdHelp" src="fw/def/image/help.gif" />
											</forms:html>
										</c:when>
										<c:otherwise>
											<forms:plaintext label="form.iasdairy.freeText.memoid" property="memoid" />
										</c:otherwise>
									</c:choose>
							</c:otherwise>
						</c:choose>
					 </forms:group>
				</forms:row>
				<forms:row>
					<%--<forms:text label="form.iasdairy.freeText.status" property="status"	size="1" maxlength="1" colspan="1" />--%>
					<forms:select id="statusid" label="form.iasdairy.freeText.status" property="status" size="1">
						<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
					</forms:select>
					<%--<forms:text label="form.iasdairy.freeText.requested" property="requested" size="8" maxlength="8" colspan="1" />--%>
					<forms:plaintext label="form.iasdairy.freeText.requested" property="requested" />
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:text label="form.iasdairy.event.portamedic.respn" property="respn_date" maxlength="10" size="10" />
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.event.portamedic.respn" property="respn_date" />
						</c:otherwise>
					</c:choose>
					
					<%--					<forms:group orientation="horizontal">--%>
					<%--						<forms:text property="respn_mm" maxlength="2" size="2">--%>
					<%--							<forms:label label="form.iasdairy.event.portamedic.respn" />--%>
					<%--						</forms:text>--%>
					<%--						<forms:text property="respn_dd" maxlength="2" size="2" />--%>
					<%--						<forms:text property="respn_yyyy" maxlength="4" size="4" />--%>
					<%--					</forms:group>--%>
				</forms:row>
				<forms:row>
					<%--<forms:text label="form.iasdairy.freeText.second.request" property="second_request" size="1" maxlength="1" colspan="1" />--%>
					<forms:select id="second_requestid" label="form.iasdairy.freeText.second.request" property="second_request" size="1">
						<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
					</forms:select>
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:text label="form.iasdairy.freeText.form.id" property="application_formid" size="20" maxlength="20" colspan="1" />
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.freeText.form.id" property="application_formid" />
						</c:otherwise>
					</c:choose>
					<%--<forms:text label="form.iasdairy.freeText.free.form.ind" property="freeformind" />--%>
					<%--<forms:text label="form.iasdairy.freeText.free.form.ind" property="freeformind" size="1" maxlength="1" colspan="1"/>--%>
					<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:select id="freeformid" label="form.iasdairy.freeText.free.form.ind" property="freeformind" size="1">
								<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
							</forms:select>
						</c:when>
						<c:otherwise>
							<forms:plaintext label="form.iasdairy.freeText.free.form.ind" property="freeformind" />
						</c:otherwise>
					</c:choose>	
				</forms:row>
				<forms:row>
				<c:choose>
						<c:when test="${pageScope.modifyStatus == 'notShow'}">
							<forms:textarea  label="form.iasdairy.freeText.freeTextArea"	property="freeTextArea" cols="75" rows="15" maxlength="4500" valign="top" />
						</c:when>
						<c:otherwise>
							<forms:textarea  disabled="true" label="form.iasdairy.freeText.freeTextArea"	property="freeTextArea" cols="75" rows="15" maxlength="4500" valign="top" />
						</c:otherwise>
				</c:choose>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">
				<c:if test="${pageScope.modifyStatus == 'show'}">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.edit" title="button.title.edit" />
					<c:set var="formType" scope="page" value="display"/>
				</c:if>
				<c:if test="${pageScope.formType =='edit'}">
					<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />
				</c:if>
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</div>