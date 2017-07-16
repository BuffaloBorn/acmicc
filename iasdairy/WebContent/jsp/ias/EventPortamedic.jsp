<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
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
	<html:form action="<%= formAction%>" styleId="frmEventPortamedic">
		<forms:form formid="eventPortamedic" caption="form.iasdairy.event.portamedic.title" type="<%= formType%>" width="750" noframe="false">
			<forms:section title="form.iasdairy.event.portamedic.system.section.title">
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.event.id" property="event_id" size="9" maxlength="9" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.std.event" property="std_event" size="8" maxlength="8" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.status" property="status" size="1" maxlength="1" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:text label="form.iasdairy.event.portamedic.requested" property="request" size="8" maxlength="8" colspan="1" />				
					
						<forms:text  label="form.iasdairy.event.portamedic.respn" property="respn_mm" maxlength="2"  size="2" />
						<forms:text  property="respn_dd"  maxlength="2"  size="2"/>
						<forms:text  property="respn_yyyy"  maxlength="4"  size="4"/>
					
					<forms:text label="form.iasdairy.event.portamedic.response" property="response" size="1" maxlength="1" colspan="1" />
				</forms:row>
			</forms:section>
			<forms:section title="form.iasdairy.event.portamedic.general.section.title">
				<forms:row>	
					<c:choose>
	      				<c:when test = "${requestScope.action == 'display'}" >
	      					<forms:text label="form.iasdairy.event.portamedic.person.id" property="person_id" size="9" maxlength="9" colspan="1"/>
	   		 			</c:when>
	      				<c:otherwise>
							<forms:text label="form.iasdairy.event.portamedic.person.id" property="person_id" size="9" maxlength="9" colspan="1" help="personid"/>
	      				</c:otherwise>
	    			</c:choose>	
					<forms:text property="person_name" size="60" maxlength="60"/>
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.birth.date" property="birthdate" size="8" maxlength="8" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.birth.place" property="birthplace" size="6" maxlength="6" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.age" property="age" size="4" maxlength="4" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:text label="form.iasdairy.event.portamedic.sex" property="sex" size="1" maxlength="1" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.marital" property="marital" size="1" maxlength="1" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.drivers.license" property="drivers_license" size="20" maxlength="20" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.state" property="drivers_license_state" size="2" maxlength="2" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.ssn" property="ssn" size="9" maxlength="9" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.employer" property="employer" size="50" maxlength="50" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.occ" property="occ" size="20" maxlength="20" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.start" property="start" size="4" maxlength="4" colspan="1" />
				</forms:row>
			</forms:section>
			<forms:section title="form.iasdairy.event.portamedic.phyician.section.title">
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.first.name" property="first_name" size="20" maxlength="20" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.middle.name" property="middle_name" size="20" maxlength="20" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.last.name" property="last_name" size="20" maxlength="20" colspan="4" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.address" property="address_number" size="10" maxlength="20" colspan="2"/>
					<forms:text property="address_name" size="50" maxlength="50" colspan="2"/>
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.city" property="city" size="30" maxlength="10" colspan="1" />
					<forms:text label="form.iasdairy.event.portamedic.state" property="phyician_state" size="2" maxlength="2" colspan="1" />
					<forms:group orientation="horizontal">
						<forms:text property="phyician_zip_five" size="5" maxlength="5" colspan="5">
							<forms:label label="form.iasdairy.event.portamedic.zip"/>
						</forms:text>
						<forms:text property="phyician_zip_four" size="4" maxlength="4"/>
					</forms:group>
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.phone" property="phyician_phone_number" size="3" maxlength="3"/>
					<forms:text label="form.iasdairy.event.portamedic.ext" property="phyician_phone_extentsion" size="4" maxlength="4"/>
					<forms:group orientation="horizontal">
						<forms:text property="phyician_fax_area" size="3" maxlength="3" colspan="5">
							<forms:label label="form.iasdairy.event.portamedic.fax"/>
						</forms:text>
						<forms:text property="phyician_fax_exchange" size="3" maxlength="3"/>
						<forms:text property="phyician_fax_number" size="4" maxlength="4"/>
					</forms:group>
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.portamedic.request.type" property="request_type" size="8" maxlength="8" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:textarea label="form.iasdairy.event.portamedic.remarks" property="remarksTextArea" cols="75" rows="5" maxlength="158" valign="top"/>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</div>