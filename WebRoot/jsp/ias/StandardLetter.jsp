<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
	function mask(str,textbox,loc,delim){
	var locs = loc.split(',');
	
	for (var i = 0; i <= locs.length; i++){
		for (var k = 0; k <= str.length; k++){
		 if (k == locs[i]){
		  if (str.substring(k, k+1) != delim){
		   if (event.keyCode != 8){ //backspace
		    str = str.substring(0,k) + delim + str.substring(k,str.length);
	       }
		  }
		 }
		}
	 }
	textbox.value = str
	}
</script>

<c:if test='${sessionScope.IASModify == "create"}'>
	<div align="center">
		<html:form action="/iuauser/stdLetterCreate" styleId="frmStdLetter">
			<forms:form formid="stdLetter" caption="form.iasdiary.std.letter.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.std.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.std.letter.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.std.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.std.letter.description" property="description" maxlength="60" size="60" colspan="1" />
						<forms:select id="attachmentid"	label="form.iasdiary.std.letter.attachment" property="attachment" size="1">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.std.letter.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text property="recipient_id" maxlength="9" size="9" />
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" />
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						</forms:html>
						<forms:html label="form.iasdiary.std.letter.memoid" join="true">	
							<ctrl:text  property="memoid" size="8" maxlength="8"/>										
							<ctrl:button name="btnMemoIdHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.memo.id.help"/>
						</forms:html>
					</forms:row>
					<forms:row>					
						<forms:select id="statusid" label="form.iasdiary.std.letter.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.std.letter.requested" property="requested" />
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>
						<forms:textarea  id="letterTextArea" label="form.iasdiary.std.letter.letterTextArea" style="font-family: courier new" property="letterTextArea" cols="75" rows="14" maxlength="4500" valign="top" />		
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
		<html:form action="/iuauser/stdLetter" styleId="frmStdLetter">
			<forms:form formid="stdLetter" caption="form.iasdiary.std.letter.title" type="display" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.std.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.std.letter.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.std.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.std.letter.description" property="description" maxlength="60" size="60" colspan="1" />
						<forms:select id="attachmentid"	label="form.iasdiary.std.letter.attachment" property="attachment" size="1">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.std.letter.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text property="recipient_id" maxlength="9" size="9" />
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:text property="recipient_name" size="8" maxlength="8" />
									</td>
								</tr>
							</table>
						</forms:html>						
						<forms:html label="form.iasdiary.std.letter.memoid">
							<ctrl:text  property="memoid" size="8" maxlength="8" />
						</forms:html>
					</forms:row>
					<forms:row>					
						<forms:select id="statusid" label="form.iasdiary.std.letter.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.std.letter.requested" property="requested" />
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" />
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>
						<forms:textarea  label="form.iasdiary.std.letter.letterTextArea" style="font-family: courier new" property="letterTextArea" cols="75" rows="15" maxlength="4500" valign="top" />		
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
		<html:form action="/iuauser/stdLetter" styleId="frmStdLetter">
			<forms:form formid="stdLetter" caption="form.iasdiary.std.letter.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.std.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.std.letter.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.std.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.std.letter.description" property="description" maxlength="60" size="60" colspan="1" />
						<forms:select id="attachmentid"	label="form.iasdiary.std.letter.attachment" property="attachment" size="1">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.std.letter.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text property="recipient_id" maxlength="9" size="9" />
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" />
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						</forms:html>
						<forms:html label="form.iasdiary.std.letter.memoid" join="true">	
							<ctrl:text  property="memoid" size="8" maxlength="8"/>										
							<ctrl:button name="btnMemoIdHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.memo.id.help"/>
						</forms:html>
					</forms:row>
					<forms:row>					
						<forms:select id="statusid" label="form.iasdiary.std.letter.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.std.letter.requested" property="requested" />
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>
						<forms:textarea  id="letterTextArea" label="form.iasdiary.std.letter.letterTextArea"  style="font-family: courier new" property="letterTextArea" cols="75" rows="14" maxlength="4500" valign="top" />		
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


<c:if test='${sessionScope.IASModify == "edit"}'>
	<div align="center">
		<html:form action="/iuauser/stdLetter" styleId="frmStdLetter">
			<forms:form formid="stdLetter" caption="form.iasdiary.std.letter.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.std.letter.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.std.letter.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.std.letter.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.std.letter.description" property="description" maxlength="60" size="60" colspan="1" />
						<forms:select id="attachmentid"	label="form.iasdiary.std.letter.attachment" property="attachment" size="1" disabled="true">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.std.letter.Recipient">
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
						<forms:html label="form.iasdiary.std.letter.memoid">
							<ctrl:plaintext property="memoid" />
						</forms:html>
					</forms:row>
					<forms:row>					
						<forms:select id="statusid" label="form.iasdiary.std.letter.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<c:set var="form" value="${stdLetterForm}"/>
						<c:choose>
							<c:when test='${form.second_request == "N"}'>
								<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:when>
							<c:otherwise>
								<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1" disabled="true">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:otherwise>
						</c:choose>
						<!--<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />-->
						<forms:plaintext label="form.iasdiary.event.portamedic.response.date" property="respn_date" />
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.std.letter.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>
						<forms:textarea  id="letterTextArea" label="form.iasdiary.std.letter.letterTextArea" style="font-family: courier new" property="letterTextArea" cols="75" rows="14" maxlength="4500" valign="top" readonly="true"/>		
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