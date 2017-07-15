<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<script language='JavaScript' src='fw/global/jscript/formatter.js'></script>
<script language='JavaScript' src='fw/html/jscript/calendar_res.js'></script>
<script language='JavaScript' src='fw/html/jscript/calendar.js'></script>



<%	
	String modify = null;
	
	modify = (String) session.getAttribute("modify");
	
	request.setAttribute("modify", modify);
%>


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
		<html:form action="/iuauser/freeTextCreate" styleId="frmFreeText">
			<forms:form formid="freeText" caption="form.iasdiary.freeText.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.freeText.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.freeText.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.freeText.description" property="description" maxlength="60" size="60" colspan="1" styleId="descriptionStyleId" onchange="gIasChangesWereMade=true"/>				
						<forms:select id="memindid" label="form.iasdiary.freeText.memoind" property="memoind" size="1" onchange="gIasChangesWereMade=true" styleId="memoind">
							<base:options property="memoindOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:select id="attachmentid"	label="form.iasdiary.freeText.attachment" property="attachment" size="1" onchange="gIasChangesWereMade=true" styleId="freetextattachmentid">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.freeText.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text  property="recipient_id" maxlength="9" size="9" onchange="gIasChangesWereMade=true"/>
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" onmouseup="gIasSaveClicked=true"/>
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						</forms:html>
						<forms:html label="form.iasdiary.freeText.memoid" join="true">	
							<ctrl:text  property="memoid" size="8" maxlength="8" onchange="gIasChangesWereMade=true"/>										
							<ctrl:button name="btnMemoIdHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.memo.id.help" onmouseup="gIasSaveClicked=true"/>
						</forms:html>					
					</forms:row>
					<forms:row>
						<forms:select id="statusid" label="form.iasdiary.freeText.status" property="status" size="1" onchange="gIasChangesWereMade=true" styleId="freetextstatus">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.requested" property="requested" />	
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>								
					</forms:row>
					<forms:row>					
						<forms:select id="second_requestid" label="form.iasdiary.freeText.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true" styleId="freetextsecond_request">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.freeText.free.form.ind" property="freeformind" size="1" onchange="gIasChangesWereMade=true" styleId="freetextfreeformind">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>			
						<pre><forms:textarea style="font-family: courier new" label="form.iasdiary.freeText.freeTextArea" property="freeTextArea" cols="75" rows="14" maxlength="4500" valign="top" onkeydown="gIasChangesWereMade=true"/></pre>	
					</forms:row>
				</forms:section>
				<forms:buttonsection default="btnSave">
					<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" onclick="runPageValidation(this)"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)"/>	
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "display"}'>
	<div align="center">
		<html:form action="/iuauser/freeText" styleId="frmFreeText">
			<forms:form formid="freeText" caption="form.iasdiary.freeText.title" type="display" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.freeText.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.freeText.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.description" property="description" />
						<forms:select id="memindid" label="form.iasdiary.freeText.memoind" property="memoind" size="1">
							<base:options property="memoindOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:select id="attachmentid"	label="form.iasdiary.freeText.attachment" property="attachment" size="1">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>							
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.freeText.Recipient">
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
						<forms:html label="form.iasdiary.freeText.memoid">
							<ctrl:plaintext property="memoid" />
						</forms:html>		
					</forms:row>
					<forms:row>					
						<forms:select id="statusid" label="form.iasdiary.freeText.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.requested" property="requested" />
						<forms:plaintext label="form.iasdiary.freeText.response.date" property="respn_date" />					
					</forms:row>
					<forms:row>
						<forms:select id="second_requestid" label="form.iasdiary.freeText.second.request" property="second_request" size="1">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.freeText.free.form.ind" property="freeformind" size="1">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>			
						<pre><forms:textarea style="font-family: courier new" label="form.iasdiary.freeText.freeTextArea" property="freeTextArea" cols="75" rows="15" maxlength="4500" valign="top"/></pre>	
					</forms:row>
				</forms:section>
				<forms:buttonsection default="btnBack">
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)"/>	
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "editWithStatus"}'>
	<div align="center">
		<html:form action="/iuauser/freeText" styleId="frmFreeText">
			<forms:form formid="freeText" caption="form.iasdiary.freeText.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.freeText.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.freeText.std.event" property="std_event" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.freeText.description" property="description" maxlength="60" size="60" colspan="1" styleId="descriptionStyleId" onchange="gIasChangesWereMade=true"/>				
						<forms:select id="memindid" label="form.iasdiary.freeText.memoind" property="memoind" size="1" onchange="gIASChangesWereMade=true" styleId="freetextmemoid">
							<base:options property="memoindOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:select id="attachmentid"	label="form.iasdiary.freeText.attachment" property="attachment" size="1" onchange="gIasChangesWereMade=true" styleId="freetextattachment">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>	
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.freeText.Recipient">
							<table border="0" cellspacing="0" cellpadding="5">
								<tr>
									<td>
										<ctrl:text  property="recipient_id" maxlength="9" size="9" onchange="gIasChangesWereMade=true"/>
										<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.recipient.help" onmouseup="gIasSaveClicked=true"/>
									</td>
								</tr>
								<tr>
									<td>
										<ctrl:plaintext property="recipient_name" />
									</td>
								</tr>
							</table>
						</forms:html>
						<forms:html label="form.iasdiary.freeText.memoid" join="true">	
							<ctrl:text  property="memoid" size="8" maxlength="8" onchange="gIasChangesWereMade=true"/>										
							<ctrl:button name="btnMemoIdHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.memo.id.help" onmouseup="gIasSaveClicked=true"/>
						</forms:html>					
					</forms:row>
					<forms:row>
						<forms:select id="statusid" label="form.iasdiary.freeText.status" property="status" size="1" onchange="gIasChangesWereMade=true" styleId="freetextstatus">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.requested" property="requested" />	
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" maxlength="10" size="10" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>								
					</forms:row>
					<forms:row>					
						<forms:select id="second_requestid" label="form.iasdiary.freeText.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true" styleId="freetextsecond_request" disabled="true">
							<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.form.id" property="application_formid" />
						<forms:select id="freeformid" label="form.iasdiary.freeText.free.form.ind" property="freeformind" size="1" onchange="gIasChangesWereMade=true" styleId="freetextfreeformind">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>			
						<pre><forms:textarea style="font-family: courier new" label="form.iasdiary.freeText.freeTextArea" property="freeTextArea" cols="75" rows="14" maxlength="4500" valign="top" wrap="VIRTUAL" id="freeTextArea" onkeydown="gIasChangesWereMade=true"/></pre>
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
		<html:form action="/iuauser/freeText" styleId="frmFreeText">
			<forms:form formid="freeText" caption="form.iasdiary.freeText.title" type="edit" width="750" noframe="false">
				<forms:section>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.policy.no" property="policyno" />
						<forms:plaintext label="form.iasdiary.freeText.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.freeText.std.event" property="std_event" />					
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.freeText.description" property="description" />
						<forms:select id="memindid" label="form.iasdiary.freeText.memoind" property="memoind" size="1" onchange="gIasChangesWereMade=true" styleId="freetextmemoid" disabled="true">
							<base:options property="memoindOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:select id="attachmentid"	label="form.iasdiary.freeText.attachment" property="attachment" size="1" onchange="gIasChangesWereMade=true" styleId="freetextattachment" disabled="true">
							<base:options property="attachmentOptions" keyProperty="key" labelProperty="value" />
						</forms:select>						
					</forms:row>
					<forms:row>
						<forms:html label="form.iasdiary.freeText.Recipient">
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
						<forms:html label="form.iasdiary.freeText.memoid">
							<ctrl:plaintext property="memoid" />
						</forms:html>		
					</forms:row>
					<forms:row>												
						<forms:select id="statusid" label="form.iasdiary.freeText.status" property="status" size="1" onchange="gIasChangesWereMade=true" styleId="freetextstatus">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:plaintext label="form.iasdiary.freeText.requested" property="requested" />	
						<forms:plaintext label="form.iasdiary.event.portamedic.response.date" property="respn_date"/>												 
					</forms:row>
					<forms:row>					
						<c:set var="form" value="${freeTextForm}"/>
						<c:choose>
							<c:when test='${form.second_request == "N"}'>
								<forms:select id="second_requestid" label="form.iasdiary.freeText.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true" styleId="freetextsecond_request">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:when>
							<c:otherwise>
								<forms:select id="second_requestid" label="form.iasdiary.freeText.second.request" property="second_request" size="1" onchange="gIasChangesWereMade=true" styleId="freetextsecond_request" disabled="true">
									<base:options property="secondRequestOptions" keyProperty="key" labelProperty="value" />
								</forms:select>
							</c:otherwise>
						</c:choose>
						<forms:plaintext label="form.iasdiary.freeText.form.id" property="application_formid" />			
						<forms:select id="freeformid" label="form.iasdiary.freeText.free.form.ind" property="freeformind" size="1" onchange="gIasChangesWereMade=true" styleId="freetextfreeformind" disabled="true">
							<base:options property="freeformIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>	
					<forms:row>			
						<pre><forms:textarea style="font-family: courier new" label="form.iasdiary.freeText.freeTextArea" property="freeTextArea" cols="75" rows="14" maxlength="4500" valign="top" readonly="true"/></pre>	
					</forms:row>
				</forms:section>
				<forms:buttonsection default="btnEdit">
					<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="runPageValidation(this)"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)" />	
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>