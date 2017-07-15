<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>

<%--<script language='JavaScript' src='app/help/help.js'></script>--%>

<script LANGUAGE="JavaScript" TYPE="text/javascript">

	
function mask(str,textbox,loc,delim){
	var locs = loc.split(',');

	
		for (var i = 0; i <= locs.length; i++){
			for (var k = 0; k <= str.length; k++){
			 if (k == locs[i]){
			  if (str.substring(k, k+1) != delim){
			   if (event.keyCode != 8)
			   {
			    str = str.substring(0,k) + delim + str.substring(k,str.length);
		       }
			  }
			 }
			}
		 }
	
	textbox.value = str
}

</script>

<c:if test='${sessionScope.IASModify == "create"}'>
	<html:form action="/iuauser/riderMain" styleId="frmRiderMain">
		<forms:form formid="riderMain" caption="form.iasdiary.rider.main.title" type="edit" width="750" noframe="false">
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.person.id" property="PERSON_ID" size="10" width="10" colspan="1" help="<%=help %>"/>--%>
						<forms:plaintext label="form.iasdiary.rider.main.person.id" property="PERSON_ID" colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME"  colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" colspan="1" />
					</forms:row>
					<forms:html  label="">
	            			<span style="font-family:Arial; font-size:12px;">
	                				Would you like to attach a rider, but restricted by state law?
	            			</span>
	            			<ctrl:select property="RIDER_FLAG" size="1">
		            			<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />
		        			</ctrl:select>
	        	    </forms:html>
						<%--<forms:row>--%>
						<%--<forms:select label="form.iasdiary.rider.main.restricted" property="RIDER_FLAG" size="1">--%>
						<%--<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />--%>
						<%--</forms:select>--%>
						<%--</forms:row>--%>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_ONE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderOneHelpCreate" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_ONE" maxlength="5" size="5" colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_ONE" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_ONE" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_ONE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_ONE" cols="50" rows="4" maxlength="250" valign="top" wrap="hard" /></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_TWO" maxlength="8" size="8"	colspan="1" />
							<forms:html>						
								<ctrl:button name="btnRiderTwoHelpCreate" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_TWO" maxlength="5" size="5" colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_TWO" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_TWO" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_TWO" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_TWO" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_THREE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderThreeHelpCreate" src="fw/def/image/help.gif" />	
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_THREE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_THREE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_THREE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_THREE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label=""	style="font-family: courier new" property="RIDER_TEXT_THREE" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FOUR" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderFourHelpCreate" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FOUR" maxlength="5" size="5" colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FOUR" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FOUR" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FOUR" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FOUR" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FIVE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderFiveHelpCreate" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FIVE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FIVE" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FIVE" maxlength="10" size="10" colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FIVE" colspan="1" />					
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FIVE" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnSave" text="button.title.update" title="button.title.update" />
				<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${sessionScope.IASModify == "display"}'>
	<html:form action="/iuauser/riderMain" styleId="frmRiderMain">
		<forms:form formid="riderMain" caption="form.iasdiary.rider.main.title" type="display" width="750" noframe="false">
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.person.id" property="PERSON_ID" size="10" width="10" colspan="1" help="<%=help %>"/>--%>
						<forms:plaintext label="form.iasdiary.rider.main.person.id" property="PERSON_ID" colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME"  colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" colspan="1" />
					</forms:row>
					<forms:html  label="">
	            			<span style="font-family:Arial; font-size:12px;">
	                				Would you like to attach a rider, but restricted by state law?
	            			</span>
	            			<ctrl:select property="RIDER_FLAG" size="1">
		            			<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />
		        			</ctrl:select>
	        	    </forms:html>
						<%--<forms:row>--%>
						<%--<forms:select label="form.iasdiary.rider.main.restricted" property="RIDER_FLAG" size="1">--%>
						<%--<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />--%>
						<%--</forms:select>--%>
						<%--</forms:row>--%>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_ONE" maxlength="8" size="8"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_ONE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_ONE" maxlength="10" size="10"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_ONE" maxlength="10" size="10"	colspan="1" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_ONE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_ONE" cols="50" rows="5" maxlength="250" valign="top" wrap="hard" /></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_TWO" maxlength="8" size="8"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_TWO" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_TWO" maxlength="10" size="10"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_TWO" maxlength="10" size="10"	colspan="1" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_TWO" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_TWO" cols="50" rows="5" maxlength="250" valign="top" wrap="hard" /></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_THREE" maxlength="8" size="8"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_THREE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_THREE" maxlength="10" size="10"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_THREE" maxlength="10" size="10"	colspan="1" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_THREE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label=""	style="font-family: courier new" property="RIDER_TEXT_THREE" cols="50" rows="5" maxlength="250" valign="top" wrap="hard" /></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FOUR" maxlength="8" size="8"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FOUR" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FOUR" maxlength="10" size="10"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FOUR" maxlength="10" size="10"	colspan="1" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FOUR" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FOUR" cols="50" rows="5" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FIVE" maxlength="8" size="8"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FIVE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FIVE" maxlength="10" size="10"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FIVE" maxlength="10" size="10"	colspan="1" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FIVE" colspan="1" />					
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FIVE" cols="50" rows="5" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>

<c:if test='${sessionScope.IASModify == "edit"}'>
	<html:form action="/iuauser/riderMain" styleId="frmRiderMain">
		<forms:form formid="riderMain" caption="form.iasdiary.rider.main.title" type="edit" width="750" noframe="false">
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.cert.id" property="POLICY_ID" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" size="10" width="10" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.coverage" property="PRODUCT_CODE" colspan="1" />
						<%--<forms:text label="form.iasdiary.rider.main.person.id" property="PERSON_ID" size="10" width="10" colspan="1" help="<%=help %>"/>--%>
						<forms:plaintext label="form.iasdiary.rider.main.person.id" property="PERSON_ID" colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.insured" property="INSURED_SEARCH_NAME"  colspan="1" />
					</forms:row>
					<forms:row>
						<%--<forms:text label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" size="65"  maxlength="65" colspan="1" />--%>
						<forms:plaintext label="form.iasdiary.rider.main.key.ins" property="KEY_INSURED_SEARCH_NAME" colspan="1" />
					</forms:row>
					<forms:html  label="">
	            			<span style="font-family:Arial; font-size:12px;">
	                				Would you like to attach a rider, but restricted by state law?
	            			</span>
	            			<ctrl:select property="RIDER_FLAG" size="1">
		            			<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />
		        			</ctrl:select>
	        	    </forms:html>
						<%--<forms:row>--%>
						<%--<forms:select label="form.iasdiary.rider.main.restricted" property="RIDER_FLAG" size="1">--%>
						<%--<base:options property="restrictedOptions" keyProperty="key" labelProperty="value" />--%>
						<%--</forms:select>--%>
						<%--</forms:row>--%>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_ONE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderOneHelpEdit" src="fw/def/image/help.gif" />	
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_ONE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_ONE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_ONE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_ONE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_ONE" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_TWO" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderTwoHelpEdit" src="fw/def/image/help.gif" />	
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_TWO" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_TWO" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_TWO" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_TWO" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_TWO" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_THREE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderThreeHelpEdit" src="fw/def/image/help.gif" />								
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_THREE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_THREE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');"  onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_THREE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_THREE" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label=""	style="font-family: courier new" property="RIDER_TEXT_THREE" cols="50" rows="4" maxlength="250" valign="top"  wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FOUR" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderFourHelpEdit" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FOUR" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FOUR" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FOUR" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FOUR" colspan="1" />
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FOUR" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
				<forms:section title="form.iasdiary.rider.main.section">
					<forms:row>
						<forms:group orientation="horizontal">
							<forms:text label="form.iasdiary.rider.main.rider.code" property="RIDER_CODE_FIVE" maxlength="8" size="8"	colspan="1" />
							<forms:html>
								<ctrl:button name="btnRiderFiveHelpEdit" src="fw/def/image/help.gif" />
							</forms:html>
							<forms:text label="form.iasdiary.rider.main.rider.time.period" property="RID_TIME_PERIOD_FIVE" maxlength="5" size="5"	colspan="1" />
							<forms:text label="form.iasdiary.rider.main.rider.start.date" property="RIDER_START_DATE_FIVE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;"/>
							<forms:text label="form.iasdiary.rider.main.rider.end.date" property="RIDER_END_DATE_FIVE" maxlength="10" size="10"	colspan="1" onkeyup="javascript:return mask(this.value,this,'2,5','/');" onblur="javascript:return mask(this.value,this,'2,5','/');" onkeypress="if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;" />
						</forms:group>
					</forms:row>
					<forms:row>
						<forms:plaintext label="form.iasdiary.rider.main.rider.description" property="RIDER_DESCRIPTION_FIVE" colspan="1" />					
					</forms:row>
					<forms:row>
						<pre><forms:textarea  label="" style="font-family: courier new"	property="RIDER_TEXT_FIVE" cols="50" rows="4" maxlength="250" valign="top" wrap="hard"/></pre>
					</forms:row>
				</forms:section>
			<forms:buttonsection default="btnBack">
				<forms:button base="buttons.src.def2" name="btnEdit" text="button.title.update" title="button.title.update" />
				<forms:button base="buttons.src.def2" name="btnBack" text="button.policy.person.main.back" title="button.title.back" />
			</forms:buttonsection>
		</forms:form>
	</html:form>
</c:if>