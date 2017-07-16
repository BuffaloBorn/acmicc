<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">

	//window.onload=selectFocus;

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
	
	function selectFocus()
	{
		  var txtArea = document.getElementById("stdletterTextArea");
		  textArea.focus();
	
	}
	
	<c:if test="${not empty param.gIasChangesWereMade}">
		gIasChangesWereMade=true;
	</c:if>

</script>

<c:if test='${sessionScope.IASModify == "create"}'>
	<div align="center">
		<html:form action="/iuauser/eventStdMemoCreate" styleId="frmEventStdMemo">
			<forms:form formid="eventStdMemo" caption="form.iasdiary.event.std.memo.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.event.std.memo.event.id" property="event_id" />				
						<forms:plaintext label="form.iasdiary.event.std.memo.std.event" property="std_event" />					
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.description" property="description" />
						<forms:plaintext label="form.iasdiary.event.std.memo.attachment" property="attachment" />
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.event.std.memo.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text  property="recipient_id" maxlength="9" size="9" styleId="eventStdMemoRecipientStyleId"/>
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" />
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						 </forms:html>
						 <forms:html label="form.iasdiary.event.std.memo.memoid" join="true">	
		 					<ctrl:plaintext property="memoid" />
						 </forms:html>
					 </forms:row>
					 <forms:row>
						<forms:select id="statusid" label="form.iasdiary.event.std.memo.status" property="status" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.requested" property="requested" />
						<forms:text label="form.iasdiary.event.std.memo.response.date" property="respn_date" maxlength="10" size="10"  onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.event.std.mem.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.event.std.memo.free.form.ind" property="freeformind" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>		
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new" id="stdletterTextArea"  label="form.iasdiary.event.std.memo.LetterTextArea"	property="stdletterTextArea" readonly="true" cols="75" rows="14" maxlength="4500" valign="top" /></pre>
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
		<html:form action="/iuauser/eventStdMemo" styleId="frmEventStdMemo">
			<forms:form formid="eventStdMemo" caption="form.iasdiary.event.std.memo.title" type="display" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.event.std.memo.event.id" property="event_id" />				
						<forms:plaintext label="form.iasdiary.event.std.memo.std.event" property="std_event" />					
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.description" property="description" />
						<forms:plaintext label="form.iasdiary.event.std.memo.attachment" property="attachment" />
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.event.std.memo.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text property="recipient_id" maxlength="9" size="9"/>
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:text property="recipient_name" size="8" maxlength="8" />
									</td>
								</tr>
							</table>
						</forms:html>
						<forms:html label="form.iasdiary.event.std.memo.memoid" join="true">
							<ctrl:text property="memoid" size="8" maxlength="8" />
						</forms:html>
					</forms:row>
					<forms:row>
						<forms:select id="statusid" label="form.iasdiary.event.std.memo.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.requested" property="requested" />
						<forms:text label="form.iasdiary.event.std.memo.response.date" property="respn_date" maxlength="10" size="10"  onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" />
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.event.std.mem.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.event.std.memo.free.form.ind" property="freeformind" size="1">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new" id="stdletterTextArea"  label="form.iasdiary.event.std.memo.LetterTextArea"	property="stdletterTextArea" readonly="true" cols="75" rows="15" maxlength="4500" valign="top" /></pre>
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
		<html:form action="/iuauser/eventStdMemo" styleId="frmEventStdMemo">
			<forms:form formid="eventStdMemo" caption="form.iasdiary.event.std.memo.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.event.std.memo.event.id" property="event_id" />				
						<forms:plaintext label="form.iasdiary.event.std.memo.std.event" property="std_event" />					
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.description" property="description" />
						<forms:plaintext label="form.iasdiary.event.std.memo.attachment" property="attachment" />
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.event.std.memo.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text  property="recipient_id" maxlength="9" size="9" onchange="gIasChangesWereMade=true"/>
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" />
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						 </forms:html>
						 <forms:html label="form.iasdiary.event.std.memo.memoid" join="true">	
		 					<ctrl:plaintext property="memoid" />
						 </forms:html>
					 </forms:row>
					 <forms:row>
						<forms:select id="statusid" label="form.iasdiary.event.std.memo.status" property="status" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.requested" property="requested" />
						<forms:text label="form.iasdiary.event.std.memo.response.date" property="respn_date" maxlength="10" size="10"  onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.event.std.mem.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.event.std.memo.free.form.ind" property="freeformind" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>		
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new" id="stdletterTextArea"  label="form.iasdiary.event.std.memo.LetterTextArea"	property="stdletterTextArea" readonly="true" cols="75" rows="14" maxlength="4500" valign="top" /></pre>
					</forms:row>			
				</forms:section>
				<forms:buttonsection default="btnEdit">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="runPageValidation(this)"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)"/>
				</forms:buttonsection>	
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<div align="center">
		<html:form action="/iuauser/eventStdMemo" styleId="frmEventStdMemo">
			<forms:form formid="eventStdMemo" caption="form.iasdiary.event.std.memo.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.event.std.memo.event.id" property="event_id" />				
						<forms:plaintext label="form.iasdiary.event.std.memo.std.event" property="std_event" />					
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.std.memo.description" property="description" />
						<forms:plaintext label="form.iasdiary.event.std.memo.attachment" property="attachment" />
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.event.std.memo.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:plaintext  property="recipient_id"/>
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
						 <forms:html label="form.iasdiary.event.std.memo.memoid" join="true">	
		 					<ctrl:plaintext property="memoid" />
						 </forms:html>
					 </forms:row>
					 <forms:row>
						<forms:select id="statusid" label="form.iasdiary.event.std.memo.status" property="status" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.event.std.memo.requested" property="requested" />
						<forms:plaintext label="form.iasdiary.event.std.memo.response.date" property="respn_date"/>
					</forms:row>
					<forms:row>
					<c:set var="form" value="${eventStdMemoForm}"/>
					<c:choose>
						<c:when test='${form.second_request == "N"}'>
							<forms:select id="second_requestid" label="form.iasdiary.event.std.mem.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true">
								<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
							</forms:select>
						</c:when>
						<c:otherwise>
							<forms:select id="second_requestid" label="form.iasdiary.event.std.mem.second.request" property="second_request" size="1" disabled="true">
								<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
							</forms:select>
						</c:otherwise>						
					</c:choose>
						<forms:plaintext label="form.iasdiary.event.std.memo.form.id" property="application_formid" />
						<forms:plaintext label="form.iasdiary.event.std.memo.free.form.ind" property="freeformind" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new" id="stdletterTextArea"  label="form.iasdiary.event.std.memo.LetterTextArea"	property="stdletterTextArea" readonly="true" cols="75" rows="14" maxlength="4500" valign="top" onkeydown="gIasChangesWereMade=true"/></pre>
					</forms:row>			
				</forms:section>
				<forms:buttonsection default="btnEdit">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="runPageValidation(this)"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)"/>
				</forms:buttonsection>	
			</forms:form>
		</html:form>
	</div>
</c:if>



