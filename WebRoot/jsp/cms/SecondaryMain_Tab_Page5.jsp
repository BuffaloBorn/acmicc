<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<script language='JavaScript' src='app/help/help.js'></script>

<c:if test='${sessionScope.IASModify == "create"}'>
	<div align="center">
		<html:form action="/iuauser/iasdiary">
			<forms:form formid="iasdiary" caption="form.iasdiary.title" type="edit" width="920">
				<forms:html align="center">
					<forms:info width="100%">
						<table border="0" cellspacing="0" cellpadding="5" align="center">
							<tr>
								<td>
									<c:choose>
										<c:when test = "${requestScope.COMMENTS_IND == 'Y'}">
											<ul>
												<li>Comments: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Comments: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.RIDER_IND == 'Y'}">
											<ul>
												<li>Rider: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Rider: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}">
											<ul>
												<li>Amendment: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Amendment: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.NOTES_IND == 'Y'}">
											<ul>
												<li>Underwriting Notes: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Underwriting Notes: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.DELIVERY_REQ == 'Y'}">
											<ul>
												<li>Delivery Requirement: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Delivery Requirement: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
							</tr>
						</table>
					</forms:info>
							
					<table>	
						<tr>
							<td height="98%" valign="top" align="center">
								<forms:section noframe="false">
									<forms:row>
										<forms:html label="form.iasdiary.policy.id">
											<ctrl:text  property="POLICY_ID" size="10" width="10" />
											<ctrl:button name="btnUpdatePolicyNo" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.policy.no.update" />
											<ctrl:button name="btnBrowsePoliciesHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.browse.policy.help" />
										</forms:html>
										<forms:plaintext label="form.iasdiary.key.product" property="KEY_PRODUCT_ID" width="5" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:text label="form.iasdiary.key.insured" property="KEY_INSURED" maxlength="255" size="65" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:html label="form.iasdiary.underwriter.status">
											<ctrl:select  property="udwselectedItem" size="1" >
			            						<base:options property="underwriterOptions"  keyProperty="key" labelProperty="value"/>
								        	</ctrl:select>
								        	<ctrl:button name="btnUpdateUnderwriterStatus" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.underwriter.status.update" />
								        	<c:if test="${not empty param.amendmentwarning}">
								        		<br/>
									        	<span style="color:red; font-weight: bold;">WARNING:  HAVE YOU ADDED YOUR AMENDMENT?</span>
			        						</c:if>
										</forms:html>
										<forms:html label="form.iasdiary.decision.date" join="true">
											<ctrl:plaintext  property="DECISION_DATE" width="10" />
											<ctrl:plaintext  property="USER" width="10"  />	
										</forms:html>
									</forms:row>
								</forms:section>
								<forms:section title="form.iasdiary.maintenance.section.title">
									<forms:buttonsection align="left">
										<%--<forms:button name="btnPolicyPersonCoverageMain" text="button.iasdiary.policy.person.coverage.main.without" title="button.iasdiary.policy.person.coverage.main.without" />																								--%>
										<c:choose>									
		      								<c:when test = "${requestScope.COMMENTS_IND == 'Y'}" >
		      									<forms:button name="btnPolicyExtendCommentsEdit" text="button.iasdiary.comments.with" title="button.iasdiary.comments.with" />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnPolicyExtendCommentsCreate" text="button.iasdiary.comments.without" title="button.iasdiary.comments.without" />
		      								</c:otherwise>
		    							</c:choose>
										<forms:button name="btnPolicyPersonEdit" text="button.iasdiary.policy.person" title="button.iasdiary.policy.person" />		
		   								<c:choose>
		      								<c:when test = "${requestScope.NOTES_IND == 'Y'}" >
		        								<forms:button name="btnUnderwritingNotesEdit"  text="button.iasdiary.notes.id.with" title="button.iasdiary.notes.id.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnUnderwritingNotesCreate"  text="button.iasdiary.notes.id.without" title="button.iasdiary.notes.id.without"  />
		      								</c:otherwise>
		    							</c:choose>
		  								<c:choose>
		      								<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}" >
		        								<forms:button name="btnAmendmentEdit" text="button.iasdiary.amendment.with" title="button.iasdiary.amendment.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnAmendmentCreate" text="button.iasdiary.amendment.without" title="button.iasdiary.amendment.without"  />
		      								</c:otherwise>
		    							</c:choose>
		<%--    							<c:choose>--%>
		<%--      								<c:when test = "${requestScope.RIDER_IND == 'Y'}" >--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.with" title="button.iasdiary.rider.with" />--%>
		<%--      								</c:when>--%>
		<%--      								<c:otherwise>--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.without" title="button.iasdiary.rider.without" />--%>
		<%--      								</c:otherwise>--%>
		<%--    							</c:choose>		--%>
									</forms:buttonsection>
							  </forms:section>
							</td>
						</tr>
						<tr>
							<td valign="top">
							<logic:present scope="session" name="events">
								<ctrl:list id="events" name="events" title="list.iasdiary.title" rows="25"  createButton="true" refreshButton="false" runat="server" locale="true">
									<ctrl:columndrilldown title="list.iasdiary.eventid" property="EVENT_ID"	width="10" enableProperty="columnMode"/>
									<ctrl:columntext title="list.iasdiary.eventst" property="STD_EVENT_ID"  width="10" />
									<ctrl:columntext title="list.iasdiary.std.event.status" property="STD_EVENT_STATUS"  width="10" />
									<ctrl:columntext title="list.iasdiary.event.desc" property="EVENT_DESCRIPTION"	 width="120" />
									<ctrl:columntext title="list.iasdiary.date.created" property="DATE_CREATED"  width="80" />
									<ctrl:columntext title="list.iasdiary.date.completed" property="DATE_COMPLETED"  width="80" />
									<ctrl:columntext title="list.iasdiary.event.person" property="EVENT_PERSON"  width="80" />
									<ctrl:columntext title="list.iasdiary.userid" property="USER_ID"  width="15" />	
									<ctrl:columnedit title="list.edit" property="editMode"/>
								</ctrl:list>
							</logic:present>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "editWithStatus"}'>
	<div align="center">
		<html:form action="/iuauser/iasdiary">
			<forms:form formid="iasdiary" caption="form.iasdiary.title" type="edit" width="920">
				<forms:html align="center">
					<forms:info width="100%">
						<table border="0" cellspacing="0" cellpadding="5" align="center">
							<tr>
								<td>
									<c:choose>
										<c:when test = "${requestScope.COMMENTS_IND == 'Y'}">
											<ul>
												<li>Comments: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Comments: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.RIDER_IND == 'Y'}">
											<ul>
												<li>Rider: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Rider: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}">
											<ul>
												<li>Amendment: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Amendment: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.NOTES_IND == 'Y'}">
											<ul>
												<li>Underwriting Notes: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Underwriting Notes: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.DELIVERY_REQ == 'Y'}">
											<ul>
												<li>Delivery Requirement: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Delivery Requirement: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
							</tr>
						</table>
					</forms:info>
							
					<table>	
						<tr>
							<td height="98%" valign="top" align="center">
								<forms:section noframe="false">
									<forms:row>
										<forms:html label="form.iasdiary.policy.id">
											<ctrl:text  property="POLICY_ID" size="10" width="10" />
											<ctrl:button name="btnUpdatePolicyNo" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.policy.no.update" />
											<ctrl:button name="btnBrowsePoliciesHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.browse.policy.help" />
										</forms:html>
										<forms:plaintext label="form.iasdiary.key.product" property="KEY_PRODUCT_ID" width="5" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:text label="form.iasdiary.key.insured" property="KEY_INSURED" maxlength="255" size="65" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:html label="form.iasdiary.underwriter.status">
											<ctrl:select  property="udwselectedItem" size="1" >
			            						<base:options property="underwriterOptions"  keyProperty="key" labelProperty="value"/>
								        	</ctrl:select>
								        	<ctrl:button name="btnUpdateUnderwriterStatus" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.underwriter.status.update" />
								        	<c:if test="${not empty param.amendmentwarning}">
								        		<br/>
									        	<span style="color:red; font-weight: bold;">WARNING:  HAVE YOU ADDED YOUR AMENDMENT?</span>
			        						</c:if>
										</forms:html>
										<forms:html label="form.iasdiary.decision.date" join="true">
											<ctrl:plaintext  property="DECISION_DATE" width="10" />
											<ctrl:plaintext  property="USER" width="10"  />	
										</forms:html>
									</forms:row>
								</forms:section>
								<forms:section title="form.iasdiary.maintenance.section.title">
									<forms:buttonsection align="left">
										<%--<forms:button name="btnPolicyPersonCoverageMain" text="button.iasdiary.policy.person.coverage.main.without" title="button.iasdiary.policy.person.coverage.main.without" />																								--%>
										<c:choose>									
		      								<c:when test = "${requestScope.COMMENTS_IND == 'Y'}" >
		      									<forms:button name="btnPolicyExtendCommentsEdit" text="button.iasdiary.comments.with" title="button.iasdiary.comments.with" />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnPolicyExtendCommentsCreate" text="button.iasdiary.comments.without" title="button.iasdiary.comments.without" />
		      								</c:otherwise>
		    							</c:choose>
										<forms:button name="btnPolicyPersonEdit" text="button.iasdiary.policy.person" title="button.iasdiary.policy.person" />		
		   								<c:choose>
		      								<c:when test = "${requestScope.NOTES_IND == 'Y'}" >
		        								<forms:button name="btnUnderwritingNotesEdit"  text="button.iasdiary.notes.id.with" title="button.iasdiary.notes.id.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnUnderwritingNotesCreate"  text="button.iasdiary.notes.id.without" title="button.iasdiary.notes.id.without"  />
		      								</c:otherwise>
		    							</c:choose>
		  								<c:choose>
		      								<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}" >
		        								<forms:button name="btnAmendmentEdit" text="button.iasdiary.amendment.with" title="button.iasdiary.amendment.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnAmendmentCreate" text="button.iasdiary.amendment.without" title="button.iasdiary.amendment.without"  />
		      								</c:otherwise>
		    							</c:choose>
		<%--    							<c:choose>--%>
		<%--      								<c:when test = "${requestScope.RIDER_IND == 'Y'}" >--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.with" title="button.iasdiary.rider.with" />--%>
		<%--      								</c:when>--%>
		<%--      								<c:otherwise>--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.without" title="button.iasdiary.rider.without" />--%>
		<%--      								</c:otherwise>--%>
		<%--    							</c:choose>		--%>
									</forms:buttonsection>
							  </forms:section>
							</td>
						</tr>
						<tr>
							<td valign="top">
							<logic:present scope="session" name="events">
								<ctrl:list id="events" name="events" title="list.iasdiary.title" rows="25"  createButton="true" refreshButton="false" runat="server" locale="true">
									<ctrl:columndrilldown title="list.iasdiary.eventid" property="EVENT_ID"	width="10" enableProperty="columnMode"/>
									<ctrl:columntext title="list.iasdiary.eventst" property="STD_EVENT_ID"  width="10" />
									<ctrl:columntext title="list.iasdiary.std.event.status" property="STD_EVENT_STATUS"  width="10" />
									<ctrl:columntext title="list.iasdiary.event.desc" property="EVENT_DESCRIPTION"	 width="120" />
									<ctrl:columntext title="list.iasdiary.date.created" property="DATE_CREATED"  width="80" />
									<ctrl:columntext title="list.iasdiary.date.completed" property="DATE_COMPLETED"  width="80" />
									<ctrl:columntext title="list.iasdiary.event.person" property="EVENT_PERSON"  width="80" />
									<ctrl:columntext title="list.iasdiary.userid" property="USER_ID"  width="15" />	
									<ctrl:columnedit title="list.edit" property="editMode" />
								</ctrl:list>
							</logic:present>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<div align="center">
		<html:form action="/iuauser/iasdiary">
			<forms:form formid="iasdiary" caption="form.iasdiary.title" type="edit" width="920">
				<forms:html align="center">
					<forms:info width="100%">
						<table border="0" cellspacing="0" cellpadding="5" align="center">
							<tr>
								<td>
									<c:choose>
										<c:when test = "${requestScope.COMMENTS_IND == 'Y'}">
											<ul>
												<li>Comments: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Comments: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.RIDER_IND == 'Y'}">
											<ul>
												<li>Rider: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Rider: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}">
											<ul>
												<li>Amendment: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Amendment: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.NOTES_IND == 'Y'}">
											<ul>
												<li>Underwriting Notes: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Underwriting Notes: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.DELIVERY_REQ == 'Y'}">
											<ul>
												<li>Delivery Requirement: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Delivery Requirement: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
							</tr>
						</table>
					</forms:info>
							
					<table>	
						<tr>
							<td height="98%" valign="top" align="center">
								<forms:section noframe="false">
									<forms:row>
										<forms:html label="form.iasdiary.policy.id">
											<ctrl:text  property="POLICY_ID" size="10" width="10" />
											<ctrl:button name="btnUpdatePolicyNo" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.policy.no.update" />
											<ctrl:button name="btnBrowsePoliciesHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.browse.policy.help" />
										</forms:html>
										<forms:plaintext label="form.iasdiary.key.product" property="KEY_PRODUCT_ID" width="5" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:text label="form.iasdiary.key.insured" property="KEY_INSURED" maxlength="255" size="65" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:html label="form.iasdiary.underwriter.status">
											<ctrl:select  property="udwselectedItem" size="1" >
			            						<base:options property="underwriterOptions"  keyProperty="key" labelProperty="value"/>
								        	</ctrl:select>
								        	<ctrl:button name="btnUpdateUnderwriterStatus" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.underwriter.status.update" />
								        	<c:if test="${not empty param.amendmentwarning}">
								        		<br/>
									        	<span style="color:red; font-weight: bold;">WARNING:  HAVE YOU ADDED YOUR AMENDMENT?</span>
			        						</c:if>
										</forms:html>
										<forms:html label="form.iasdiary.decision.date" join="true">
											<ctrl:plaintext  property="DECISION_DATE" width="10" />
											<ctrl:plaintext  property="USER" width="10"  />	
										</forms:html>
									</forms:row>
								</forms:section>
								<forms:section title="form.iasdiary.maintenance.section.title">
									<forms:buttonsection align="left">
										<%--<forms:button name="btnPolicyPersonCoverageMain" text="button.iasdiary.policy.person.coverage.main.without" title="button.iasdiary.policy.person.coverage.main.without" />																								--%>
										<c:choose>									
		      								<c:when test = "${requestScope.COMMENTS_IND == 'Y'}" >
		      									<forms:button name="btnPolicyExtendCommentsEdit" text="button.iasdiary.comments.with" title="button.iasdiary.comments.with" />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnPolicyExtendCommentsCreate" text="button.iasdiary.comments.without" title="button.iasdiary.comments.without" />
		      								</c:otherwise>
		    							</c:choose>
										<forms:button name="btnPolicyPersonEdit" text="button.iasdiary.policy.person" title="button.iasdiary.policy.person" />		
		   								<c:choose>
		      								<c:when test = "${requestScope.NOTES_IND == 'Y'}" >
		        								<forms:button name="btnUnderwritingNotesEdit"  text="button.iasdiary.notes.id.with" title="button.iasdiary.notes.id.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnUnderwritingNotesCreate"  text="button.iasdiary.notes.id.without" title="button.iasdiary.notes.id.without"  />
		      								</c:otherwise>
		    							</c:choose>
		  								<c:choose>
		      								<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}" >
		        								<forms:button name="btnAmendmentEdit" text="button.iasdiary.amendment.with" title="button.iasdiary.amendment.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnAmendmentCreate" text="button.iasdiary.amendment.without" title="button.iasdiary.amendment.without"  />
		      								</c:otherwise>
		    							</c:choose>
		<%--    							<c:choose>--%>
		<%--      								<c:when test = "${requestScope.RIDER_IND == 'Y'}" >--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.with" title="button.iasdiary.rider.with" />--%>
		<%--      								</c:when>--%>
		<%--      								<c:otherwise>--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.without" title="button.iasdiary.rider.without" />--%>
		<%--      								</c:otherwise>--%>
		<%--    							</c:choose>		--%>
									</forms:buttonsection>
							  </forms:section>
							</td>
						</tr>
						<tr>
							<td valign="top">
							<logic:present scope="session" name="events">
								<ctrl:list id="events" name="events" title="list.iasdiary.title" rows="25"  createButton="true" refreshButton="false" runat="server" locale="true">
									<ctrl:columndrilldown title="list.iasdiary.eventid" property="EVENT_ID"	width="10" enableProperty="columnMode"/>
									<ctrl:columntext title="list.iasdiary.eventst" property="STD_EVENT_ID"  width="10" />
									<ctrl:columntext title="list.iasdiary.std.event.status" property="STD_EVENT_STATUS"  width="10" />
									<ctrl:columntext title="list.iasdiary.event.desc" property="EVENT_DESCRIPTION"	 width="120" />
									<ctrl:columntext title="list.iasdiary.date.created" property="DATE_CREATED"  width="80" />
									<ctrl:columntext title="list.iasdiary.date.completed" property="DATE_COMPLETED"  width="80" />
									<ctrl:columntext title="list.iasdiary.event.person" property="EVENT_PERSON"  width="80" />
									<ctrl:columntext title="list.iasdiary.userid" property="USER_ID"  width="15" />	
									<ctrl:columnedit title="list.edit" property="editMode"/>
								</ctrl:list>
							</logic:present>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>

<c:if test='${sessionScope.IASModify == "display"}'>
	<div align="center">
		<html:form action="/iuauser/iasdiary">
			<forms:form formid="iasdiary" caption="form.iasdiary.title" type="edit" width="920">
				<forms:html align="center">
					<forms:info width="100%">
						<table border="0" cellspacing="0" cellpadding="5" align="center">
							<tr>
								<td>
									<c:choose>
										<c:when test = "${requestScope.COMMENTS_IND == 'Y'}">
											<ul>
												<li>Comments: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Comments: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.RIDER_IND == 'Y'}">
											<ul>
												<li>Rider: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Rider: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}">
											<ul>
												<li>Amendment: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Amendment: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.NOTES_IND == 'Y'}">
											<ul>
												<li>Underwriting Notes: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Underwriting Notes: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
								<td>
									<c:choose>
										<c:when test = "${requestScope.DELIVERY_REQ == 'Y'}">
											<ul>
												<li>Delivery Requirement: Yes</li>
											</ul> 
										</c:when>
										<c:otherwise>
											<ul>
												<li>Delivery Requirement: No</li>
											</ul> 
										</c:otherwise>
									</c:choose>	
								</td>
							</tr>
						</table>
					</forms:info>
							
					<table>	
						<tr>
							<td height="98%" valign="top" align="center">
								<forms:section noframe="false">
									<forms:row>
										<forms:html label="form.iasdiary.policy.id">
											<ctrl:text  property="POLICY_ID" size="10" width="10" />
											<ctrl:button name="btnUpdatePolicyNo" src="fw/def/image/buttons/btnUnchkAll3.gif" tooltip="form.iasdiary.policy.no.update" />
											<ctrl:button name="btnBrowsePoliciesHelp" src="fw/def/image/help.gif" tooltip="form.iasdiary.browse.policy.help" />
										</forms:html>
										<forms:plaintext label="form.iasdiary.key.product" property="KEY_PRODUCT_ID" width="5" colspan="1"  />
									</forms:row>
									<forms:row>
										<forms:plaintext label="form.iasdiary.key.insured" property="KEY_INSURED"  width="65" colspan="1"/>
									</forms:row>
									<forms:row>
										<forms:html label="form.iasdiary.underwriter.status">
								        	<ctrl:select  property="udwselectedItem" size="1" disabled="true">
			            						<base:options property="underwriterOptions"  keyProperty="key" labelProperty="value"/>
								        	</ctrl:select>
								        	<c:if test="${not empty param.amendmentwarning}">
								        		<br/>
									        	<span style="color:red; font-weight: bold;">WARNING:  HAVE YOU ADDED YOUR AMENDMENT?</span>
			        						</c:if>
										</forms:html>
										<forms:html label="form.iasdiary.decision.date" join="true">
											<ctrl:plaintext  property="DECISION_DATE" width="10" />
											<ctrl:plaintext  property="USER" width="10"  />	
										</forms:html>
									</forms:row>
								</forms:section>
								<forms:section title="form.iasdiary.maintenance.section.title">
									<forms:buttonsection align="left">
										<%--<forms:button name="btnPolicyPersonCoverageMain" text="button.iasdiary.policy.person.coverage.main.without" title="button.iasdiary.policy.person.coverage.main.without" />																								--%>
										<c:choose>									
		      								<c:when test = "${requestScope.COMMENTS_IND == 'Y'}" >
		      									<forms:button name="btnPolicyExtendCommentsDisplay" text="button.iasdiary.comments.with" title="button.iasdiary.comments.with" />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnPolicyExtendCommentsDisplay" text="button.iasdiary.comments.without" title="button.iasdiary.comments.without" />
		      								</c:otherwise>
		    							</c:choose>
										<forms:button name="btnPolicyPersonDisplay" text="button.iasdiary.policy.person" title="button.iasdiary.policy.person" />		
		   								<c:choose>
		      								<c:when test = "${requestScope.NOTES_IND == 'Y'}" >
		        								<forms:button name="btnUnderwritingNotesDisplay"  text="button.iasdiary.notes.id.with" title="button.iasdiary.notes.id.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnUnderwritingNotesDisplay"  text="button.iasdiary.notes.id.without" title="button.iasdiary.notes.id.without"  />
		      								</c:otherwise>
		    							</c:choose>
		  								<c:choose>
		      								<c:when test = "${requestScope.AMENDMENT_IND == 'Y'}" >
		        								<forms:button name="btnAmendmentDisplay" text="button.iasdiary.amendment.with" title="button.iasdiary.amendment.with"  />
		      								</c:when>
		      								<c:otherwise>
		        								<forms:button name="btnAmendmentDisplay" text="button.iasdiary.amendment.without" title="button.iasdiary.amendment.without"  />
		      								</c:otherwise>
		    							</c:choose>
		<%--    							<c:choose>--%>
		<%--      								<c:when test = "${requestScope.RIDER_IND == 'Y'}" >--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.with" title="button.iasdiary.rider.with" />--%>
		<%--      								</c:when>--%>
		<%--      								<c:otherwise>--%>
		<%--        								<forms:button name="btnRider" text="button.iasdiary.rider.without" title="button.iasdiary.rider.without" />--%>
		<%--      								</c:otherwise>--%>
		<%--    							</c:choose>		--%>
									</forms:buttonsection>
							  </forms:section>
							</td>
						</tr>
						<tr>
							<td valign="top">
							<logic:present scope="session" name="events">
								<ctrl:list id="events" name="events" title="list.iasdiary.title" rows="25"  createButton="false" refreshButton="false" runat="server" locale="true">
									<ctrl:columndrilldown title="list.iasdiary.eventid" property="EVENT_ID"	width="10" enableProperty="columnMode"/>
									<ctrl:columntext title="list.iasdiary.eventst" property="STD_EVENT_ID"  width="10" />
									<ctrl:columntext title="list.iasdiary.std.event.status" property="STD_EVENT_STATUS"  width="10" />
									<ctrl:columntext title="list.iasdiary.event.desc" property="EVENT_DESCRIPTION"	 width="120" />
									<ctrl:columntext title="list.iasdiary.date.created" property="DATE_CREATED"  width="80" />
									<ctrl:columntext title="list.iasdiary.date.completed" property="DATE_COMPLETED"  width="80" />
									<ctrl:columntext title="list.iasdiary.event.person" property="EVENT_PERSON"  width="80" />
									<ctrl:columntext title="list.iasdiary.userid" property="USER_ID"  width="15" />	
								</ctrl:list>
							</logic:present>
							</td>
						</tr>
					</table>
				</forms:html>
			</forms:form>
		</html:form>
	</div>
</c:if>
