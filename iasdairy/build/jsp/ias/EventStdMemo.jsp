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
	<html:form action="/iuauser/eventStdMemo" styleId="frmEventStdMemo">
		<forms:form formid="eventStdMemo" caption="form.iasdairy.event.std.memo.title" type="<%= formType%>" width="750" noframe="false">
			<forms:section>
				<forms:row>
					<forms:text label="form.iasdairy.event.std.memo.event.id" property="event_id" size="10" width="10" colspan="1" />
					<forms:text label="form.iasdairy.event.std.memo.attach.requested" property="attach_requested" size="10" width="10" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text label="form.iasdairy.event.std.memo.description" property="description" maxlength="255" size="65" colspan="4" />
				</forms:row>
				<forms:row>
					<forms:plaintext label="form.iasdairy.event.std.memo.std.event" property="std_event" />
					<forms:text label="form.iasdairy.event.std.memo.status" property="status" size="10" width="10" colspan="1" />				
				</forms:row>
				<forms:row>				
					<forms:text label="form.iasdairy.event.std.memo.requested" property="requested" size="10" width="10" colspan="2" />
					<forms:group orientation="horizontal">
						<forms:text  property="respn_mm" maxlength="1"  size="1">
							<forms:label label="form.iasdairy.event.std.memo.response"/>
						</forms:text>
						<forms:text  property="respn_dd"  maxlength="2"  size="2"/>
						<forms:text  property="respn_yyyy"  maxlength="2"  size="3"/>
					</forms:group>
					<c:out value="${action}"/>
					<c:choose>
	      				<c:when test = "${action == 'display'}" >
	      						<forms:text label="form.iasdairy.freeText.memoid" property="memoid" size="10" width="10" colspan="1"/>
	      				</c:when>
	      				<c:otherwise>
							<forms:text label="form.iasdairy.freeText.memoid" property="memoid" size="10" width="10" colspan="1"/>
	      				</c:otherwise>
	    			</c:choose>			
				</forms:row>
				<forms:row>
					<c:choose>
	      				<c:when test = "${requestScope.action == 'display'}" >
	      					<forms:text label="form.iasdairy.freeText.Recipient" property="recipient_id" maxlength="255" size="65" colspan="1"/>
	   		 			</c:when>
	      				<c:otherwise>
									<forms:text label="form.iasdairy.freeText.Recipient" property="recipient_id" maxlength="255" size="65" colspan="1" />
	      				</c:otherwise>
	    			</c:choose>	
					<forms:text  property="recipient_name" maxlength="255" size="65" colspan="4" />
				</forms:row>
				<forms:row>
					<forms:textarea label="form.iasdairy.event.std.memo.LetterTextArea" property="freeTextArea" cols="75" rows="6" maxlength="158" valign="top"/>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">			
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</div>
