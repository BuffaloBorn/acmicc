<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>


<%	
	String IASModify = null;
	String modify = null;
	String underwritMainModify = null;
	
	IASModify = (String) session.getAttribute("IASModify");
	
	if (IASModify != null)
	{
		if(IASModify.equalsIgnoreCase("edit"))
		{
			modify = (String) request.getParameter("modify");
			
			underwritMainModify = (String) session.getAttribute("underwritMainModify");
			
			if(underwritMainModify != null)
			{
				modify = underwritMainModify;
			}	
			
			if (modify != null)
			{
				session.setAttribute("underwritMainModify", modify);
				
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
		
	}
	
	
%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
	function doOnClick()
	{
		gIASSaveClicked=true;
	}
</script>

<c:if test='${pageScope.modifyStatus == "create"}'>
	<html:form action="/iuauser/underwritingNotesMain">
		<forms:form formid="underwritingNotesMain" caption="form.iasdiary.policy.underwriting.notes.main.title" type="edit" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.description" property="description" colspan="1"/>
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="notesArea" label="form.iasdiary.policy.underwriting.notes.main.notes" style="font-family: courier new" property="notesArea" cols="60" rows="9" maxlength="11460" valign="top" wrap="hard"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnSave">
				<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" />
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${pageScope.modifyStatus == "display"}'>
	<html:form action="/iuauser/underwritingNotesMain">
		<forms:form formid="underwritingNotesMain" caption="form.iasdiary.policy.underwriting.notes.main.title" type="display" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.description" property="description" colspan="1"/>
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="notesArea" label="form.iasdiary.policy.underwriting.notes.main.notes" style="font-family: courier new" property="notesArea" cols="60" rows="10" maxlength="11460" valign="top" wrap="hard"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>	
	</html:form>
</c:if>

<c:if test='${pageScope.modifyStatus == "edit"}'>
	<html:form action="/iuauser/underwritingNotesMain">
		<forms:form formid="underwritingNotesMain" caption="form.iasdiary.policy.underwriting.notes.main.title" type="edit" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.underwriting.notes.main.description" property="description" colspan="1"/>
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="notesArea" label="form.iasdiary.policy.underwriting.notes.main.notes" style="font-family: courier new" property="notesArea" cols="60" rows="9" maxlength="11460" valign="top" wrap="hard"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnEdit">					
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update"/>		
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>