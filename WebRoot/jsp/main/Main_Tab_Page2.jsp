<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>

<script language="Javascript">
var submitcount=0;

function validateInput() {
	var policyNum = activatePendingForm.policyNumber.value;
	var goodVals = true;		
	
	if (policyNum == null || policyNum.length == 0) {
		message = " Please enter a policy number before clicking search.\n";
		goodVals = false;		
	}
	
	if (goodVals != true) {
		alert(message);		
	}
	else {
		if (submitcount==0)
		{
			submitcount++;
			activatePendingForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }	
     }	
}

function confirmActivate() {
	
	var retVal = confirm("Are you certain you want to activate the selected process(es)?");

	if (retVal == true && submitcount==0)
		{
			submitcount++;
			activatePendingForm.submit();
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
	
}
function disableenterkey()
{
	if (window.event.keyCode == 13) 
	{
		window.event.keyCode = 0;
		return;
	}
}

function setfocus()
{
	document.forms[0].policyNumber.focus();
}

</script>

<body  onbeforeunload=" runUnloadCloseIasDiary()" onunload="runCloseIasDiaryUnload()">

<html:form action="/main/actpend">

<table width="100%" align="center">
	<tr>
<td class="legend" align="right" height="8">Back to <a href="main/secondarytabsetBrowse.do?ctrl=secondarymaintabset&action=TabClick&param=tab5" onclick="setWorklistFlag()">WorkList</a></td>
	</tr>
</table>

<center>
<forms:form width="50%" type="edit" caption="task.policy"
			name="activatePendingForm" formid="activatePendingForm">

<forms:info>
Enter the policy number and click on the <b>Search</b> button. To Activate a pending process click on the check box then click the <b>Activate</b> button.
</forms:info>
<br/>
<forms:row>
	<forms:text label="actPend.policy" size="22" property="policyNumber" onkeypress="disableenterkey()" />
	
	<forms:buttonsection default="btnSearch" join="true">
		<forms:button onclick="validateInput();" name="btnSearch" text="button.title.search"  />		
	</forms:buttonsection>
</forms:row>
</forms:form>
</center>
<br><br>



<c:if test="${processList != null}">

<center>
<ctrl:list id="userlist1" action="/main/actpend" name="processList"
		title="actPend.title" 
		noframe="false" 
		noheader="false" 
		width="75%"
		rows="10"
		refreshButton="false" 
		select="multiple"
		formElement="false">
		<ctrl:columntext title="actPend.procName" property="processType" width="200" />
		<ctrl:columntext title="actPend.suspend" property="suspendDate" width="200" />
		<ctrl:columncheckbox editable="true" title="Reassign.Tasks.Select" property="checkState" checkAll="true" width="80"  />

</ctrl:list>


<br />
<table width="100%">
	<tr>
		<td class='legend' nowrap>
			<font color="Blue">
				Standard&nbsp;Actions
			</font>
		</td>
		<td valign="middle" width="100%">
			<hr>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left"><ctrl:button onclick="confirmActivate();" name="btnActivate" text="actPend.activate"/></td>

	</tr>
</table>
</center>

</c:if>

</html:form>

<util:jsp directive="endofpage" />
</body>



