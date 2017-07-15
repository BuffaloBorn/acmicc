<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ page import="com.epm.acmi.datamodel.PolicyPersonMainDisplayList" %>


<c:if test='${sessionScope.IASModify == "display"}'>
	<html:form action="/iuauser/policyPersonMain" styleId="frmPolicyPersonMain">
		<forms:form formid="policyPersonMain" caption="form.iasdiary.policy.person.main.title" type="display" width="750" noframe="false">
			<forms:html align="center">
				<table>
					<tr>
						<td height="98%" valign="top" align="center">
							<forms:section title="form.iasdiary.policy.person.main.system.section.title">
								<forms:row>
									<forms:plaintext label="form.iasdiary.policy.person.main.policyid" property="policyid" colspan="1" />
									<forms:text label="form.iasdiary.policy.person.main.personid" property="personid" size="10" width="10" colspan="1" />
									<forms:plaintext label="form.iasdiary.policy.person.main.effective.date" property="effective_date" width="10" colspan="1" />
								</forms:row>
							</forms:section>
						</td>
					</tr>	
					<tr>
						<td valign="top">
								<logic:present scope="session" name="policyPeasonList">
									<ctrl:list id="policyPeasonList" name="policyPeasonList" title="list.iasdiary.policy.person.main.person.title" rows="50"  createButton="false" refreshButton="false" runat="server" locale="true" formElement="true">	
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tone" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="ttwo" size="1" editableProperty="editable">            							
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tthree" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tfour" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
	        							<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tfive"  width="500"  size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columntext title="list.iasdiary.policy.person.main.person" property="PERSON_ID"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.name" property="PERSON_SEARCH_NAME"	width="500"  />
										<ctrl:columntext title="list.iasdiary.policy.person.main.person.level.ind" property="PERSON_LEVEL_IND"	width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.person.status.ind" property="PERSON_STATUS"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.start.date" property="PERSON_STATUS_START_DATE"	width="10"  />
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.under.writer.status"  property="PERSON_STATUS_IND" size="1" editableProperty="editable">
	            							<base:options property="personStatusHelpOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>						
										<ctrl:columntext title="list.iasdiary.policy.person.main.und.rd" property="RIDER_IND"	width="10" />
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.smoker.ind" property="SMOKER_IND" size="1" editableProperty="editable">
	            							<base:options property="smokerOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
	        							<ctrl:columngroup title="list.iasdiary.policy.person.main.list.title" align="center">
	        								
	        								<ctrl:columndrilldown title="button.iasdiary.policy.person.main.rider" property="RIDER_SHOW" width="10" align="center" />
											
	        								
											<%--<% boolean checkRiderStatus = ((PolicyPersonMainDisplayList) policyPeasonList.getDataModel()).getCheckRiderStatus(); --%>
											
											<%--<% if (!checkRiderStatus) { %>--%>
											<%--	<ctrl:columnbutton  title="button.iasdiary.policy.person.main.rider" text="list.iasdiary.policy.person.main.rider.click.create" command="riderCreate" align="center"  />--%>
											<%--<% } else { %>--%>
											<%--	<ctrl:columnbutton  title="button.iasdiary.policy.person.main.rider" text="list.iasdiary.policy.person.main.rider.click.open" command="riderOpen" align="center"  />--%>
											<%--<% } %>--%>
											
											<ctrl:columnbutton  title="button.iasdiary.policy.person.main.sub.coverage" text="list.iasdiary.policy.person.main.coverage.click" command="coverage" align="center"/>
										
										</ctrl:columngroup>
									</ctrl:list>
								</logic:present>
								<forms:section title="form.iasdiary.policy.person.main.section.title">
									<forms:buttonsection default="btnBack">
										<%--<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />--%>
										<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
									</forms:buttonsection>
								</forms:section>
						</td>
					</tr>
				</table>
			</forms:html>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<html:form action="/iuauser/policyPersonMain" styleId="frmPolicyPersonMain">
		<forms:form formid="policyPersonMain" caption="form.iasdiary.policy.person.main.title" type="edit" width="750" noframe="false">
			<forms:html align="center">
				<table>
					<tr>
						<td height="98%" valign="top" align="center">
							<forms:section title="form.iasdiary.policy.person.main.system.section.title">
								<forms:row>
									<forms:plaintext label="form.iasdiary.policy.person.main.policyid" property="policyid" colspan="1" />
									<forms:text label="form.iasdiary.policy.person.main.personid" property="personid" size="10" width="10" colspan="1" />
									<forms:plaintext label="form.iasdiary.policy.person.main.effective.date" property="effective_date" width="10" colspan="1" />
								</forms:row>
							</forms:section>
							
						</td>
					</tr>	
					<tr>
						<td valign="top">
								<logic:present scope="session" name="policyPeasonList">
									<ctrl:list id="policyPeasonList" name="policyPeasonList" title="list.iasdiary.policy.person.main.person.title" rows="50"  createButton="false" refreshButton="false" runat="server" locale="true" formElement="true" >	
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tone" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="ttwo" size="1" editableProperty="editable">            							
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tthree" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tfour" size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
	        							<ctrl:columnselect  title="list.iasdiary.policy.person.main.t"  property="tfive"  width="500"  size="1" editableProperty="editable">
	            							<base:options property="transactionsOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
										<ctrl:columntext title="list.iasdiary.policy.person.main.person" property="PERSON_ID"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.name" property="PERSON_SEARCH_NAME"	width="500"  />
										<ctrl:columntext title="list.iasdiary.policy.person.main.person.level.ind" property="PERSON_LEVEL_IND"	width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.person.status.ind" property="PERSON_STATUS"	 width="10" />
										<ctrl:columntext title="list.iasdiary.policy.person.main.start.date" property="PERSON_STATUS_START_DATE"	width="10"  />
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.under.writer.status"  property="PERSON_STATUS_IND" size="1" editableProperty="editable">
	            							<base:options property="personStatusHelpOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>						
										<ctrl:columntext title="list.iasdiary.policy.person.main.und.rd" property="RIDER_IND"	width="10" />
										<ctrl:columnselect  title="list.iasdiary.policy.person.main.smoker.ind" property="SMOKER_IND" size="1" editableProperty="editable">
	            							<base:options property="smokerOptions" keyProperty="key" labelProperty="value" />
	        							</ctrl:columnselect>
	        							<ctrl:columngroup title="list.iasdiary.policy.person.main.list.title" align="center">
	        								<% boolean editable = ((PolicyPersonMainDisplayList) policyPeasonList.getDataModel()).getEditable(); %>
	
											<% if (!editable)  { %>
												<ctrl:columnbutton title="list.iasdiary.policy.person.main.edit.row"  text="list.iasdiary.policy.person.main.edit"     image="fw/def2/image/icons/edit.gif"   align="center"  width="60"  command="edit"/>
											<% } else { %>
												<ctrl:columnbutton title="list.iasdiary.policy.person.main.save.row"  text="list.iasdiary.policy.person.main.save"     property="editable"  image="app/images/imgSave.gif"           align="center"  width="60"  command="save"/>
												<ctrl:columnbutton title="list.iasdiary.policy.person.main.cancel.row"  text="list.iasdiary.policy.person.main.cancel"   property="editable"  image="fw/def2/image/icons/delete.gif"   align="center"  width="60"  command="cancel"/>
											<% } %>			
				
											<ctrl:columndrilldown title="button.iasdiary.policy.person.main.rider" property="RIDER_SHOW" width="10" align="center" />
											
											<%--<% boolean checkRiderStatus = ((PolicyPersonMainDisplayList) policyPeasonList.getDataModel()).getCheckRiderStatus(); --%>
											
											<%--<% if (!checkRiderStatus) { %>--%>
											<%--	<ctrl:columnbutton  title="button.iasdiary.policy.person.main.rider" text="list.iasdiary.policy.person.main.rider.click.create" command="riderCreate" align="center"  />--%>
											<%--<% } else { %>--%>
											<%--	<ctrl:columnbutton  title="button.iasdiary.policy.person.main.rider" text="list.iasdiary.policy.person.main.rider.click.open" command="riderOpen" align="center"  />--%>
											<%--<% } %>--%>
											
											<ctrl:columnbutton  title="button.iasdiary.policy.person.main.sub.coverage" text="list.iasdiary.policy.person.main.coverage.click" command="coverage" align="center"/>
										
										</ctrl:columngroup>
									</ctrl:list>
								</logic:present>
								<forms:section title="form.iasdiary.policy.person.main.section.title">
									<forms:buttonsection default="btnBack">
										<%--<forms:button base="buttons.src.def2" name="btnSave" text="button.title.save" title="button.title.save" />--%>
										<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
									</forms:buttonsection>
								</forms:section>
						</td>
					</tr>
				</table>
			</forms:html>
		</forms:form>
	</html:form>
</c:if>
