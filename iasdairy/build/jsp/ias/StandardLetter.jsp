<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<%
	String formType = null;
	formType = request.getParameter("action");
	if (formType == null)
	{
		formType = "edit";
	}
	
%>
<div align="center">
	<html:form action="/iuauser/stdLetter" styleId="frmStdLetter">
		<forms:form formid="stdLetter" caption="form.iasdairy.std.letter.title" type="<%= formType%>" width="750" noframe="false">
			<forms:section title="form.iasdairy.std.letter.std.events">
				<forms:row>
					<forms:text label="form.iasdairy.std.letter.event.id" property="event_id" size="9" maxlength="9" colspan="2" />
					<forms:text label="form.iasdairy.std.letter.std.event" property="std_event" size="8" maxlength="8" colspan="2"/>
					<forms:text label="form.iasdairy.std.letter.status" property="status" size="1" maxlength="8" colspan="2" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.std.letter.description" property="description" maxlength="60" size="60" colspan="4" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.std.letter.Recipient" property="recipient_id" maxlength="9" size="9" colspan="1"/>
					<forms:text property="recipient_name" maxlength="60" size="60" colspan="1" />
				</forms:row>
				<forms:row>
					
					<forms:text label="form.iasdairy.letter.second.request" property="second_request" size="1" maxlength="1" colspan="1" />
					<forms:text label="form.iasdairy.std.letter.requested" property="requested" size="8" width="8" colspan="1" />
					<forms:group orientation="horizontal">
						<forms:text  property="respn_mm" maxlength="2"  size="2">
							<forms:label label="form.iasdairy.event.portamedic.respn"/>
						</forms:text>
						<forms:text  property="respn_dd"  maxlength="2"  size="2"/>
						<forms:text  property="respn_yyyy"  maxlength="4"  size="4"/>
					</forms:group>				
					<forms:text label="form.iasdairy.std.letter.memoid" property="memoid" size="8" maxlength="8" colspan="1" />				
				</forms:row>
				<forms:row>
					<forms:textarea label="form.iasdairy.std.letter.LetterTextArea" property="letterTextArea" cols="75" rows="12" maxlength="4500" valign="top"/>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</div>