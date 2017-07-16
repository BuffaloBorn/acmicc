<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<script language='JavaScript' src='app/help/help.js'></script>
<%
	String formType = null;
	formType = request.getParameter("action");
	if (formType == null)
	{
		formType = "edit";
	}
	
%>
<div align="center">
	<html:form action="/iuauser/letter" styleId="frmLetter">
		<forms:form formid="letter" caption="form.iasdairy.letter.title" type="<%= formType%>" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:text label="form.iasdairy.letter.suspense.date" property="suspense_date" size="8" maxlength="8" colspan="1" />
					<forms:text label="form.iasdairy.letter.suspense.amt" property="suspense_amt" size="8" maxlength="8" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.letter.event.id" property="event_id" size="9" maxlength="9" colspan="1" />
					<forms:text label="form.iasdairy.letter.std.event" property="std_event" size="8" maxlength="8" colspan="1" />
				</forms:row>
				<forms:row>
				
					<forms:text label="form.iasdairy.letter.recipient_id" property="recipient_id" maxlength="9" size="9" colspan="1"/>
					<forms:text property="recipient_name" maxlength="60" size="60" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.letter.status" property="status" size="1" maxlength="1" colspan="1" />
					<forms:text label="form.iasdairy.letter.second.request" property="second_request" size="1" maxlength="1" colspan="1" />
					<forms:text label="form.iasdairy.letter.requested" property="requested" size="8" maxlength="8" colspan="1" />
					<forms:text label="form.iasdairy.letter.attach" property="attach" size="1" maxlength="1" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:radio  label=""  property="letter_text"  value="0"  description="form.iasdairy.letter.withdrawn"/>
					<forms:textarea label="" property="letter_withdrawn" cols="75" rows="2" maxlength="150" valign="top">
						<forms:radio  property="by_agent_applicant" value="0" description="form.iasdairy.letter.by.agent"/>
						<forms:radio  property="by_agent_applicant" value = "1" description="form.iasdairy.letter.by.applicant"/>
					</forms:textarea>
				</forms:row>
				<forms:row>
					<forms:radio  label="" property="letter_text"  value="1"  description="form.iasdairy.letter.incomplete"/>
					<forms:textarea property="letter_incomplete" cols="75" rows="4" maxlength="300" valign="top"/>
				</forms:row>	
				<forms:row>
					<forms:radio label="" property="letter_text"  value="2"  description="form.iasdairy.letter.declined"/>
					<forms:textarea property="letter_declined" cols="75" rows="6" maxlength="450" valign="top"/>
				</forms:row>		
			</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</div>