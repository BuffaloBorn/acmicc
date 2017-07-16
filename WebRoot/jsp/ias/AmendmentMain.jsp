<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<%	
	String IASModify = null;
	String modify = null;
	String amendMainModify = null;
	
	IASModify = (String) session.getAttribute("IASModify");
	
	if (IASModify != null)
	{
		if(IASModify.equalsIgnoreCase("edit"))
		{
			modify = (String) request.getParameter("modify");
			
			amendMainModify = (String) session.getAttribute("amendMainModify");
			
			if(amendMainModify != null)
			{
				modify = amendMainModify;
			}	
			
			if (modify != null)
			{
				session.setAttribute("amendMainModify", modify);
				
				if (modify.equalsIgnoreCase("create"))
				{
					pageContext.setAttribute("modifyStatus", "create");
				}
				
				if (modify.equalsIgnoreCase("edit"))
				{
					pageContext.setAttribute("modifyStatus", "edit");				
				} 
				
			}				
		}
		
		if(IASModify.equalsIgnoreCase("display"))
		{
			pageContext.setAttribute("modifyStatus", "display");		
		}
		
		if(IASModify.equalsIgnoreCase("create"))
		{
			pageContext.setAttribute("modifyStatus", "create");		
		}
		
	}
	
	
%>

<c:if test='${pageScope.modifyStatus == "create"}'>
	<html:form action="/iuauser/amendmentMain"   styleId="frmAmendmentMain">
		<forms:form formid="amendmentMain"  caption="form.iasdiary.amendment.main.title" type="edit" width="750">
			<forms:section>
				<forms:row>
					<forms:plaintext label="form.iasdiary.amendment.main.policyid" property="policyid" colspan="1" />
					<forms:plaintext label="form.iasdiary.amendment.main.name" property="name"  colspan="1" />
				</forms:row>   
	            <forms:row colspan="2">
					<forms:text label="form.iasdiary.amendment.main.description" property="description" size="75" maxlength="75" colspan="1" onchange="gIASChangesWereMade=true"/>
				</forms:row>
				<forms:row colspan="2">
					<pre><forms:textarea style="font-family: courier new" id="amendmentTextArea" label="form.iasdiary.amendment.main.amendment.text" property="amendmentTextArea"  cols="60" rows="9" maxlength="600" valign="top" wrap="hard" onkeydown="gIASChangesWereMade=true"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnSave">		
				<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="runPageValidation(this)" />
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${pageScope.modifyStatus == "display"}'>
	<html:form action="/iuauser/amendmentMain"   styleId="frmAmendmentMain">
		<forms:form formid="amendmentMain"  caption="form.iasdiary.amendment.main.title" type="edit" width="750">
			<forms:section>
				<forms:row>
					<forms:plaintext label="form.iasdiary.amendment.main.policyid" property="policyid" colspan="1" />
					<forms:plaintext label="form.iasdiary.amendment.main.name" property="name"  colspan="1" />
				</forms:row>   
	            <forms:row colspan="2">
					<forms:plaintext label="form.iasdiary.amendment.main.description" property="description" colspan="1" />
				</forms:row>
				<forms:row colspan="2">
					<pre><forms:textarea readonly="true" style="font-family: courier new" id="amendmentTextArea" label="form.iasdiary.amendment.main.amendment.text" property="amendmentTextArea"  cols="60" rows="10" maxlength="600" valign="top"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${pageScope.modifyStatus == "edit"}'>
	<html:form action="/iuauser/amendmentMain"   styleId="frmAmendmentMain">
		<forms:form formid="amendmentMain"  caption="form.iasdiary.amendment.main.title" type="edit" width="750">
			<forms:section>
				<forms:row>
					<forms:plaintext label="form.iasdiary.amendment.main.policyid" property="policyid" colspan="1" />
					<forms:plaintext label="form.iasdiary.amendment.main.name" property="name"  colspan="1" />
				</forms:row>  
	            <forms:row colspan="2">
					<forms:text label="form.iasdiary.amendment.main.description" property="description" size="50" maxlength="50" colspan="1" onchange="gIASChangesWereMade=true"/>
				</forms:row>
				<forms:row colspan="2">
					<pre><forms:textarea style="font-family: courier new" id="amendmentTextArea" label="form.iasdiary.amendment.main.amendment.text" property="amendmentTextArea"  cols="60" rows="9" maxlength="600" valign="top" wrap="hard" onkeydown="gIASChangesWereMade=true"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnEdit">
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" onmouseup="gIasSaveClicked=true" onclick="runPageValidation(this)"/>			
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="runPageValidation(this)"/>
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>
