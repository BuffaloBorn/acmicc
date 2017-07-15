<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>


<%	
	String modifyStatus = null;
	
	modifyStatus = (String) request.getAttribute("modify");
	
	if (modifyStatus == null)
		modifyStatus = "display";
	else
	{
		if (!modifyStatus.equalsIgnoreCase("display"))
			modifyStatus = "show";
	}
	
	pageContext.setAttribute("modifyStatus", modifyStatus);
%>


<c:if test='${sessionScope.IASModify == "create"}'>
	<div align="center">
		<html:form action="/iuauser/letterCreate" styleId="frmLetter">
			<forms:form formid="letter" caption="form.iasdiary.letter.title" type="edit" width="750" noframe="false">
				<forms:section title="Details.std.actions">
					<forms:row>	
						<forms:plaintext label="form.iasdiary.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.letter.event.id" property="event_id"/>
						<forms:plaintext label="form.iasdiary.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.letter.suspense.date" property="suspense_date" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.suspense.amt" property="suspense_amt" colspan="1" />
					</forms:row>
					<forms:html label="form.iasdiary.letter.recipient">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>
									<ctrl:text  property="recipient_id" maxlength="9" size="9"/>
									<!--<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" />-->
								</td>
							</tr>
							<tr>
								<td>
									<ctrl:plaintext property="recipient_name" />
								</td>
							</tr>
						</table>
					</forms:html>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.letter.status" property="status" colspan="1" />
						<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>			
						<forms:plaintext label="form.iasdiary.letter.requested" property="requested" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.attach" property="attach" colspan="1" />
					</forms:row>
					<forms:row>
						<forms:html label="" colspan="1"/>	
						<forms:radio property="by_agent_applicant" value="0" description="form.iasdiary.letter.by.agent"/>
						<forms:radio property="by_agent_applicant" value = "1" description="form.iasdiary.letter.by.applicant"/>
					</forms:row>
					<forms:row join="true">
						<forms:radio  label=""  property="letter_text"  value="0"  description="form.iasdiary.letter.withdrawn"/>
						<pre><forms:textarea  style="font-family: courier new" id ="letter_withdrawn" property="letter_withdrawn" cols="75" rows="1" maxlength="150" valign="top"/></pre>							
					</forms:row>
					<forms:row>
						<forms:radio  label="" property="letter_text"  value="1"  description="form.iasdiary.letter.incomplete"/>
						<pre><forms:textarea  style="font-family: courier new" id="letter_incomplete" property="letter_incomplete" cols="75" rows="3" maxlength="300" valign="top"/></pre>
					</forms:row>	
					<forms:row>
						<forms:checkbox label="" property="letter_declined_value"  description="form.iasdiary.letter.declined"/>
						<pre><forms:textarea  style="font-family: courier new" id="letter_declined" property="letter_declined" cols="75" rows="5" maxlength="450" valign="top"/></pre>
					</forms:row>		
				</forms:section>
				<forms:buttonsection default="btnSave">
					<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" />
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "display"}'>
	<div align="center">
		<html:form action="/iuauser/letter" styleId="frmLetter">
			<forms:form formid="letter" caption="form.iasdiary.letter.title" type="display" width="750" noframe="false">
				<forms:section title="Details.std.actions">
					<forms:row>	
						<forms:plaintext label="form.iasdiary.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.letter.event.id" property="event_id"/>
						<forms:plaintext label="form.iasdiary.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.letter.suspense.date" property="suspense_date" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.suspense.amt" property="suspense_amt" colspan="1" />
					</forms:row>
					<forms:html label="form.iasdiary.letter.recipient">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>
									<ctrl:plaintext property="recipient_id" />
								</td>
							</tr>
							<tr>
								<td>
									<ctrl:plaintext property="recipient_name" />
								</td>
							</tr>
						</table>
					</forms:html>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.letter.status" property="status" colspan="1" />		
						<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.letter.requested" property="requested" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.attach" property="attach" colspan="1" />
					</forms:row>
					<forms:row>
						<forms:html label="" colspan="1"/>
						<forms:radio property="by_agent_applicant" value="0" description="form.iasdiary.letter.by.agent"/>
						<forms:radio property="by_agent_applicant" value = "1" description="form.iasdiary.letter.by.applicant"/>
					</forms:row>
					<forms:row join="true">
						<forms:radio  label=""  property="letter_text"  value="0"  description="form.iasdiary.letter.withdrawn"/>
						<pre><forms:textarea   style="font-family: courier new" id ="letter_withdrawn" property="letter_withdrawn" cols="75" rows="3" maxlength="150" valign="top"/></pre>							
					</forms:row>
					<forms:row>
						<forms:radio  label="" property="letter_text"  value="1"  description="form.iasdiary.letter.incomplete"/>
						<pre><forms:textarea style="font-family: courier new" id="letter_incomplete" property="letter_incomplete" cols="75" rows="5" maxlength="300" valign="top"/></pre>
					</forms:row>	
					<forms:row>
						<forms:checkbox label="" property="letter_declined_value"  description="form.iasdiary.letter.declined"/>
						<pre><forms:textarea style="font-family: courier new" id="letter_declined" property="letter_declined" cols="75" rows="7" maxlength="450" valign="top"/></pre>
					</forms:row>		
				</forms:section>
				<forms:buttonsection default="btnBack">
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>	
			</forms:form>
		</html:form>
	</div>
</c:if>


<c:if test='${sessionScope.IASModify == "editWithStatus"}'>
	<div align="center">
		<html:form action="/iuauser/letter" styleId="frmLetter">
			<forms:form formid="letter" caption="form.iasdiary.letter.title" type="edit" width="750" noframe="false">
				<forms:section title="Details.std.actions">
					<forms:row>	
						<forms:plaintext label="form.iasdiary.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.letter.event.id" property="event_id"/>
						<forms:plaintext label="form.iasdiary.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.letter.suspense.date" property="suspense_date" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.suspense.amt" property="suspense_amt" colspan="1" />
					</forms:row>
					<forms:html label="form.iasdiary.letter.recipient">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>
									<ctrl:text  property="recipient_id" maxlength="9" size="9"/>
									<!--<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" />-->
								</td>
							</tr>
							<tr>
								<td>
									<ctrl:plaintext property="recipient_name" />
								</td>
							</tr>
						</table>
					</forms:html>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.letter.status" property="status" colspan="1" />	
						<c:set var="form" value="${letterForm}"/>
						<c:choose>
							<c:when test='${form.second_request == "N"}'>
								<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:when>
							<c:otherwise>
								<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1" disabled="true">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:otherwise>
						</c:choose>
						<forms:plaintext label="form.iasdiary.letter.requested" property="requested" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.attach" property="attach" colspan="1" />
					</forms:row>
					<forms:row>
						<forms:html label="" colspan="1"/>
						<forms:radio property="by_agent_applicant" value="0" description="form.iasdiary.letter.by.agent"/>
						<forms:radio property="by_agent_applicant" value = "1" description="form.iasdiary.letter.by.applicant"/>
					</forms:row>
					<forms:row join="true">
						<forms:radio  label=""  property="letter_text"  value="0"  description="form.iasdiary.letter.withdrawn"/>
						<pre><forms:textarea  style="font-family: courier new" id ="letter_withdrawn" property="letter_withdrawn" cols="75" rows="1" maxlength="150" valign="top"/></pre>							
					</forms:row>
					<forms:row>
						<forms:radio  label="" property="letter_text"  value="1"  description="form.iasdiary.letter.incomplete"/>
						<pre><forms:textarea style="font-family: courier new" id="letter_incomplete" property="letter_incomplete" cols="75" rows="3" maxlength="300" valign="top"/></pre>
					</forms:row>	
					<forms:row>
						<forms:checkbox label="" property="letter_declined_value"  description="form.iasdiary.letter.declined"/>
						<pre><forms:textarea style="font-family: courier new" id="letter_declined" property="letter_declined" cols="75" rows="5" maxlength="450" valign="top"/></pre>
					</forms:row>		
				</forms:section>
				<forms:buttonsection default="btnEdit">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" />
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<div align="center">
		<html:form action="/iuauser/letter" styleId="frmLetter">
			<forms:form formid="letter" caption="form.iasdiary.letter.title" type="edit" width="750" noframe="false">
				<forms:section title="Details.std.actions">
					<forms:row>	
						<forms:plaintext label="form.iasdiary.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.letter.event.id" property="event_id"/>
						<forms:plaintext label="form.iasdiary.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.letter.suspense.date" property="suspense_date" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.suspense.amt" property="suspense_amt" colspan="1" />
					</forms:row>
					<forms:html label="form.iasdiary.letter.recipient">
						<table border="0" cellspacing="0" cellpadding="5">
							<tr>
								<td>
									<ctrl:text  property="recipient_id" maxlength="9" size="9"/>
									<!--<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" />-->
								</td>
							</tr>
							<tr>
								<td>
									<ctrl:plaintext property="recipient_name" />
								</td>
							</tr>
						</table>
					</forms:html>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.letter.status" property="status" colspan="1" />			
						<c:set var="form" value="${letterForm}"/>
						<c:choose>
							<c:when test='${form.second_request == "N"}'>
								<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>	
							</c:when>
							<c:otherwise>
								<forms:select id="second_requestid" label="form.iasdiary.letter.second.request" property="second_request" size="1" disabled="true">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:otherwise>
						</c:choose>
						<forms:plaintext label="form.iasdiary.letter.requested" property="requested" colspan="1" />
						<forms:plaintext label="form.iasdiary.letter.attach" property="attach" colspan="1" />
					</forms:row>
					<forms:row>
						<forms:radio  label=""  property="letter_text"  value="0"  description="form.iasdiary.letter.withdrawn"/>
						<pre><forms:textarea  style="font-family: courier new" id ="letter_withdrawn" property="letter_withdrawn" cols="75" rows="1" maxlength="150" valign="top"/></pre>							
					</forms:row>
					<forms:row join="true">
						<forms:html label="" colspan="1"></forms:html>
						<forms:radio property="by_agent_applicant" value="0" description="form.iasdiary.letter.by.agent"/>
						<forms:radio property="by_agent_applicant" value = "1" description="form.iasdiary.letter.by.applicant"/>
					</forms:row>
					<forms:row>
						<forms:radio  style="font-family: courier new" label="" property="letter_text"  value="1"  description="form.iasdiary.letter.incomplete"/>
						<pre><forms:textarea style="font-family: courier new" id="letter_incomplete" property="letter_incomplete" cols="75" rows="3" maxlength="300" valign="top"/></pre>
					</forms:row>	
					<forms:row>
						<forms:checkbox label="" property="letter_declined_value"  description="form.iasdiary.letter.declined"/>
						<pre><forms:textarea  style="font-family: courier new" id="letter_declined" property="letter_declined" cols="75" rows="5" maxlength="450" valign="top"/></pre>
					</forms:row>		
				</forms:section>
				<forms:buttonsection default="btnEdit">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

