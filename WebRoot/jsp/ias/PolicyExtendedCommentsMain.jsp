<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">

	function SetTextAreaCursor() {

        var txtArea = document.getElementById("myText");

        var oRange = txtArea.createTextRange();

 

        txtArea.focus();

        

        

        //Emtpy Line

        var regExg = /\r\n\r\n/im;

        

        //Find first empty line.

        matches = regExg.exec(txtArea.value);

 

        if (matches != null && matches.length > 0) {

        

            //IE 6 Bug fix.

            var pos = matches.index + 1;

            var text = txtArea.value

            for(var i = 0; i < matches.index; i++)

            {

                if (text.substring(i, i + 1) == "\r")

                    pos--;

            }

            //IE 6 Bug Fix not sure about IE7

            

            oRange.moveStart("character", pos);

            oRange.moveEnd("character", 0);

            oRange.collapse(true);

            oRange.select();

        }

        else {

            oRange.moveStart("character", txtArea.value.length);

            oRange.moveEnd("character", 0);

            oRange.collapse(false);

            oRange.select();

        }

    }
	 
	
</script>


<c:if test='${sessionScope.IASModify == "create"}'>
	<html:form action="/iuauser/policyExtendedCommentsMain" styleId="frmPolicyExtendedCommentsMain">
		<html:hidden property="autoSave" value=""/>
		<forms:form formid="extendedComments" caption="form.iasdiary.policy.extended.comments.main.title" type="edit" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.description" property="description" colspan="1" />
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="freeTextArea" label="form.iasdiary.policy.extended.comments.main.freetext" style="font-family: courier new" property="freeTextArea" cols="60" rows="9" maxlength="11460" valign="top" wrap="hard" onkeydown="gIASChangesWereMade=true"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnSave">
				<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" styleId="updateId" onmouseup="javascript:doOnClick();"/>		
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back"  onmouseup="javascript:removeAutoSavePopUp();"/>
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>


<c:if test='${sessionScope.IASModify == "display"}'>
	<html:form action="/iuauser/policyExtendedCommentsMain" styleId="frmPolicyExtendedCommentsMain">
		<html:hidden property="autoSave" value=""/>
		<forms:form formid="extendedComments" caption="form.iasdiary.policy.extended.comments.main.title" type="edit" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.description" property="description" colspan="1" />
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="freeTextArea" label="form.iasdiary.policy.extended.comments.main.freetext" style="font-family: courier new" property="freeTextArea" cols="60" rows="10" maxlength="11460" valign="top" wrap="hard" /></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnBack">	
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>


<c:if test='${sessionScope.IASModify == "edit"}'>
	
	<script language="Javascript">
		window.onload=selectFocus;
		
		function selectFocus()
		{
			document.policyExtendedCommentsMainForm.freeTextArea.focus();
			SetTextAreaCursor();
		}
	</script>
	
	<html:form action="/iuauser/policyExtendedCommentsMain" styleId="frmPolicyExtendedCommentsMain">
		<html:hidden property="autoSave" value=""/>
		<forms:form formid="extendedComments" caption="form.iasdiary.policy.extended.comments.main.title" type="edit" width="750" noframe="false">
			<forms:section title="Details.std.actions">
				<forms:row>
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.policyid" property="policy_id" colspan="1" />
				</forms:row>
				<forms:row>	
					<forms:plaintext label="form.iasdiary.policy.extended.comments.main.description" property="description" colspan="1" />
				</forms:row>
				<forms:row>
					<pre><forms:textarea id="freeTextArea" label="form.iasdiary.policy.extended.comments.main.freetext" style="font-family: courier new" property="freeTextArea" cols="60" rows="9" maxlength="11460" valign="top" wrap="hard" onkeydown="gIASChangesWereMade=true"/></pre>
				</forms:row>
			</forms:section>
			<forms:buttonsection default="btnEdit">
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" styleId="updateId" onmouseup="javascript:doOnClick();"/>
				<forms:button base="buttons.src.def2" name="btnBack" text="button.title.back" title="button.title.back" onclick="javascript:removeAutoSavePopUp();"/>
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

   