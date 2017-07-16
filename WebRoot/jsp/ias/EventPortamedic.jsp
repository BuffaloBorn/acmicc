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
		
		function maskZipCode(str,textbox,loc,delim){
		var locs = loc.split(',');
		
		for (var i = 0; i <= locs.length; i++)
		{
			for (var k = 0; k <= str.length; k++)
			{
			 if (k == locs[i])
			 {
			  if (str.substring(k-1, k) != delim)
			  {
			   if (event.keyCode != 8)
			   { //backspace
			    str =   str.substring(0,k-1)+ delim  + str.substring(k-1,str.length);
		       }
			  }
			 }
			}
		 }
		textbox.value = str
		}
		
	 <c:if test="${not empty param.gIasChangesWereMade}">
		gIasChangesWereMade=true;
	 </c:if>
		
</script>


<c:if test='${sessionScope.IASModify == "create"}'>
	<div align="center">
		<html:form action="/iuauser/eventPortamedicCreate" styleId="frmEventPortamedic">
			<forms:form formid="eventPortamedic" caption="form.iasdiary.event.portamedic.title" type="edit" width="750" noframe="false">
				<forms:section title="form.iasdiary.event.portamedic.system.section.title">
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.policy.no" property="policyno" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.event.portamedic.std.event" property="std_event"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.status" property="status"/>
					</forms:row>
					<forms:row>	
						<forms:plaintext label="form.iasdiary.event.portamedic.requested" property="request"/>
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.response.ind" property="response"/>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.general.section.title">
					<forms:html label="form.iasdiary.event.portamedic.person.id">
						<ctrl:text  property="person_id" size="9" maxlength="9" onchange="gIasChangesWereMade=true"/>
						<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" onmouseup="gIasSaveClicked=true"/>
						<ctrl:plaintext  property="person_name"/>
					</forms:html>					
					<forms:row>					
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.date" property="birthdate"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.place" property="birthplace"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.age" property="age"/>
					</forms:row>
					<forms:row>						
						<forms:plaintext label="form.iasdiary.event.portamedic.sex" property="sex"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.marital" property="marital"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.drivers.license" property="drivers_license" />
						<forms:plaintext label="form.iasdiary.event.portamedic.state" property="drivers_license_state"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.ssn" property="ssn"/>
					</forms:row>
					<forms:row>					
						<forms:plaintext label="form.iasdiary.event.portamedic.employer" property="employer"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.occ" property="occ"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.start" property="start"/>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.phyician.section.title">
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.first.name" property="first_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.middle.name" property="middle_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.last.name" property="last_name" size="50" maxlength="50" colspan="4" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:html label="form.iasdiary.event.portamedic.address">
						<ctrl:text  property="address_number" size="20" maxlength="20" onchange="gIasChangesWereMade=true"/>
						<ctrl:text property="address_name" size="50" maxlength="50" onchange="gIasChangesWereMade=true"/>
					</forms:html>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.city" property="city" size="30" maxlength="30" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:select id="stateid" label="form.iasdiary.event.portamedic.state" property="phyician_state" onchange="gIasChangesWereMade=true">
								<base:options property="statesOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:text label="form.iasdiary.event.portamedic.zip" property="phyician_zipCode" size="10" maxlength="10" colspan="1" onkeyup="javascript:return maskZipCode(this.value,this,'6','-');" onblur="javascript:return maskZipCode(this.value,this,'6','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.phone" property="phyician_phone_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.ext" property="phyician_phone_extentsion" size="4" maxlength="4" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.fax" property="phyician_fax_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.request.type" property="request_type"  colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new"  label="form.iasdiary.event.portamedic.remarks" property="remarksTextArea" cols="60" rows="4" maxlength="300" valign="top" wrap="hard" onkeydown="gIasChangesWereMade=true"/></pre>
					</forms:row>
			   	</forms:section>
				<forms:buttonsection default="btnSave">
					<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="if (runPageValidationTwo(this)) CCUtility.submitEnclosingForm(this); else {document.getElementById('btnBackHidden').value=''; return false;}"/>
					<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="if (runPageValidationTwo(this)) CCUtility.submitEnclosingForm(this); else {document.getElementById('btnBackHidden').value=''; return false;}"/>
				</forms:buttonsection>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "display"}'>
	<div align="center">
		<html:form action="/iuauser/eventPortamedic" styleId="frmEventPortamedic">
			<forms:form formid="eventPortamedic" caption="form.iasdiary.event.portamedic.title" type="display" width="750" noframe="false">
				<forms:section title="form.iasdiary.event.portamedic.system.section.title">
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.policy.no" property="policyno" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.event.portamedic.std.event" property="std_event"/>
						<forms:select id="statusid" label="form.iasdiary.event.portamedic.status" property="status" size="1">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>	
						<forms:plaintext label="form.iasdiary.event.portamedic.requested" property="request"/>
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');"/>
						<forms:select id="responseindid" label="form.iasdiary.event.portamedic.response.ind" property="response" size="1">
								<base:options property="responseIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.general.section.title">
					<forms:html label="form.iasdiary.event.portamedic.person.id">
						<ctrl:text  property="person_id" size="9" maxlength="9" />
						<ctrl:plaintext  property="person_name"/>
					</forms:html>
					<forms:row>					
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.date" property="birthdate"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.place" property="birthplace"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.age" property="age"/>
					</forms:row>
					<forms:row>						
						<forms:plaintext label="form.iasdiary.event.portamedic.sex" property="sex"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.marital" property="marital"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.drivers.license" property="drivers_license" />
						<forms:plaintext label="form.iasdiary.event.portamedic.state" property="drivers_license_state"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.ssn" property="ssn"/>
					</forms:row>
					<forms:row>					
						<forms:plaintext label="form.iasdiary.event.portamedic.employer" property="employer"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.occ" property="occ"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.start" property="start"/>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.phyician.section.title">
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.first.name" property="first_name" size="20" maxlength="20" colspan="1" />
						<forms:text label="form.iasdiary.event.portamedic.middle.name" property="middle_name" size="20" maxlength="20" colspan="1" />
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.last.name" property="last_name" size="50" maxlength="50" colspan="4" />
					</forms:row>
					<forms:html label="form.iasdiary.event.portamedic.address">
						<ctrl:text  property="address_number" size="20" maxlength="20"/>
						<ctrl:text property="address_name" size="50" maxlength="50" />
					</forms:html>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.city" property="city" size="30" maxlength="30" colspan="1" />
						<forms:plaintext label="form.iasdiary.event.portamedic.state" property="phyician_state"/>
						<forms:text label="form.iasdiary.event.portamedic.zip" property="phyician_zipCode" size="10" maxlength="10" colspan="1" onkeyup="javascript:return maskZipCode(this.value,this,'6','-');" onblur="javascript:return maskZipCode(this.value,this,'6','-');"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.phone" property="phyician_phone_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');"/>
						<forms:text label="form.iasdiary.event.portamedic.ext" property="phyician_phone_extentsion" size="4" maxlength="4"/>
						<forms:text label="form.iasdiary.event.portamedic.fax" property="phyician_fax_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.request.type" property="request_type"  colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea style="font-family: courier new" readonly="true"  label="form.iasdiary.event.portamedic.remarks" property="remarksTextArea" cols="60" rows="5" maxlength="300" valign="top" wrap="hard"/></pre>
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
		<html:form action="/iuauser/eventPortamedic" styleId="frmEventPortamedic">
			<forms:form formid="eventPortamedic" caption="form.iasdiary.event.portamedic.title" type="edit" width="750" noframe="false">
				<forms:section title="form.iasdiary.event.portamedic.system.section.title">
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.policy.no" property="policyno" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.event.portamedic.std.event" property="std_event"/>
						<forms:select id="statusid" label="form.iasdiary.event.portamedic.status" property="status" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>	
						<forms:text label="form.iasdiary.event.portamedic.requested" property="request" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onchange="gIasChangesWereMade=true"/>				
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onchange="gIasChangesWereMade=true"/>
						<forms:select id="responseindid" label="form.iasdiary.event.portamedic.response.ind" property="response" size="1" disabled="true">
								<base:options property="responseIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.general.section.title">
					<forms:html label="form.iasdiary.event.portamedic.person.id">
						<ctrl:text  property="person_id" size="9" maxlength="9" onchange="gIasChangesWereMade=true"/>
						<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" onmouseup="gIasSaveClicked=true"/>
						<ctrl:plaintext  property="person_name"/>
					</forms:html>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.date" property="birthdate"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.place" property="birthplace"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.age" property="age"/>
					</forms:row>
					<forms:row>	
						<forms:plaintext label="form.iasdiary.event.portamedic.sex" property="sex"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.marital" property="marital"/>
					</forms:row>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.event.portamedic.drivers.license" property="drivers_license" />
						<forms:plaintext label="form.iasdiary.event.portamedic.state" property="drivers_license_state"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.ssn" property="ssn"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.employer" property="employer"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.occ" property="occ"/>				
						<forms:plaintext label="form.iasdiary.event.portamedic.start" property="start"/>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.phyician.section.title">
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.first.name" property="first_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.middle.name" property="middle_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.last.name" property="last_name" size="50" maxlength="50" colspan="4" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:html label="form.iasdiary.event.portamedic.address">
						<ctrl:text  property="address_number" size="20" maxlength="20" onchange="gIasChangesWereMade=true"/>
						<ctrl:text property="address_name" size="50" maxlength="50" onchange="gIasChangesWereMade=true"/>
					</forms:html>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.city" property="city" size="30" maxlength="30" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:select id="stateid" label="form.iasdiary.event.portamedic.state" property="phyician_state" onchange="gIasChangesWereMade=true">
									<base:options property="statesOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:text label="form.iasdiary.event.portamedic.zip" property="phyician_zipCode" size="10" maxlength="10" colspan="1" onkeyup="javascript:return maskZipCode(this.value,this,'6','-');" onblur="javascript:return maskZipCode(this.value,this,'6','-');" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.phone" property="phyician_phone_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.ext" property="phyician_phone_extentsion" size="4" maxlength="4" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.fax" property="phyician_fax_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.request.type" property="request_type"  size="10" maxlength="10"  colspan="1" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>				 
						<pre><forms:textarea style="font-family: courier new" id="remarksTextArea"  label="form.iasdiary.event.portamedic.remarks" property="remarksTextArea" cols="60" rows="4" maxlength="300" valign="top" wrap="hard" onkeydown="gIasChangesWereMade=true"/></pre>
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
		<html:form action="/iuauser/eventPortamedic" styleId="frmEventPortamedic">
			<forms:form formid="eventPortamedic" caption="form.iasdiary.event.portamedic.title" type="edit" width="750" noframe="false">
				<forms:section title="form.iasdiary.event.portamedic.system.section.title">
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.policy.no" property="policyno" />
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.event.id" property="event_id" />
						<forms:plaintext label="form.iasdiary.event.portamedic.std.event" property="std_event"/>
						<forms:select id="statusid" label="form.iasdiary.event.portamedic.status" property="status" size="1" onchange="gIasChangesWereMade=true">
							<base:options property="statusOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
					<forms:row>	
						<forms:text label="form.iasdiary.event.portamedic.requested" property="request" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onchange="gIasChangesWereMade=true"/>				
						<forms:text label="form.iasdiary.event.portamedic.response.date" property="respn_date" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:select id="responseindid" label="form.iasdiary.event.portamedic.response.ind" property="response" size="1" disabled="true">
								<base:options property="responseIndOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.general.section.title">
					<forms:html label="form.iasdiary.event.portamedic.person.id">
						<ctrl:text  property="person_id" size="9" maxlength="9" onchange="gIasChangesWereMade=true"/>
						<ctrl:button name="btnRecipientHelp" src="fw/def/image/help.gif" onmouseup="gIasSaveClicked=true"/>
						<ctrl:plaintext  property="person_name"/>
					</forms:html>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.date" property="birthdate"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.birth.place" property="birthplace"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.age" property="age"/>
					</forms:row>
					<forms:row>	
						<forms:plaintext label="form.iasdiary.event.portamedic.sex" property="sex"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.marital" property="marital"/>
					</forms:row>
					<forms:row>			
						<forms:plaintext label="form.iasdiary.event.portamedic.drivers.license" property="drivers_license" />
						<forms:plaintext label="form.iasdiary.event.portamedic.state" property="drivers_license_state"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.ssn" property="ssn"/>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.event.portamedic.employer" property="employer"/>
						<forms:plaintext label="form.iasdiary.event.portamedic.occ" property="occ"/>				
						<forms:plaintext label="form.iasdiary.event.portamedic.start" property="start"/>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.event.portamedic.phyician.section.title">
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.first.name" property="first_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.middle.name" property="middle_name" size="20" maxlength="20" colspan="1" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.last.name" property="last_name" size="50" maxlength="50" colspan="4" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:html label="form.iasdiary.event.portamedic.address">
						<ctrl:text  property="address_number" size="20" maxlength="20" onchange="gIasChangesWereMade=true"/>
						<ctrl:text property="address_name" size="50" maxlength="50" onchange="gIasChangesWereMade=true"/>
					</forms:html>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.city" property="city" size="30" maxlength="30" colspan="1" onchange="gIasChangesWereMade=true"/>
						<forms:select id="stateid" label="form.iasdiary.event.portamedic.state" property="phyician_state" onchange="gIasChangesWereMade=true">
									<base:options property="statesOptions" keyProperty="key" labelProperty="value" />
						</forms:select>
						<forms:text label="form.iasdiary.event.portamedic.zip" property="phyician_zipCode" size="10" maxlength="10" colspan="1" onkeyup="javascript:return maskZipCode(this.value,this,'6','-');" onblur="javascript:return maskZipCode(this.value,this,'6','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.phone" property="phyician_phone_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.ext" property="phyician_phone_extentsion" size="4" maxlength="4" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
						<forms:text label="form.iasdiary.event.portamedic.fax" property="phyician_fax_number" size="12" maxlength="12" onkeyup="javascript:return mask(this.value,this,'3,7','-');" onblur="javascript:return mask(this.value,this,'3,7','-');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>
						<forms:text label="form.iasdiary.event.portamedic.request.type" property="request_type"  size="10" maxlength="10"  colspan="1" onchange="gIasChangesWereMade=true"/>
					</forms:row>
					<forms:row>				 
						<pre><forms:textarea  style="font-family: courier new" id="remarksTextArea"  label="form.iasdiary.event.portamedic.remarks" property="remarksTextArea" cols="60" rows="4" maxlength="300" valign="top" wrap="hard" onkeydown="gIasChangesWereMade=true"/></pre>
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


