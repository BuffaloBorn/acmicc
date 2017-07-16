<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ page import="com.cc.acmi.presentation.taglib.TagUtils"%>

<script type="text/javascript">	
window.onload = addherfListeners;
</script>

<script language="Javascript">
var submitcount=0;

function validateInput() {
	var prevPolicyNum = prevPolicyForm.previousPolicyID.value;
	var status = prevPolicyForm.status.value;
	var ptd = prevPolicyForm.ptd.value;
	var effDate = prevPolicyForm.effDate.value;
	var insuredName = prevPolicyForm.insuredName.value;
	var message = "Please correct the following errors:\n";
	var goodVals = true;		
	
	/* if(isNaN(prevPolicyNum)) 
   	{ 
		message += " - The Policy Number field must only contain numbers.\n";
		goodVals = false;
	} */
	if (prevPolicyNum == null || prevPolicyNum.length == 0)
	{
		message += " - The Policy Number field is required.\n";
		goodVals = false;
	}
	/*if (isNaN(ptd))
	{
		message += " - The PTD field must only contain numbers.\n";
		goodVals = false;		
	}*/
	if (ptd != null && ptd.length > 0) {
		if (isDate(ptd) == false) {
			message += " - The PTD date format is incorrect. Please use mm/dd/yyyy\n";
			goodVals = false;		
		}
	}
	if (effDate != null && effDate.length > 0) {
		if (isDate(effDate) == false) {
			message += " - The Effective Date format is incorrect. Please use mm/dd/yyyy\n";
			goodVals = false;		
		}
	}
	
	if (goodVals != true) {
		alert(message);		
	}
	else {
		gSaveClicked=true; 
		if (submitcount==0)
		{
			try
		  	{	
				submitcount++;
				prevPolicyForm.submit();
			}
			catch(ex)
		 	{
		 		//don't want a genuine error
		 		//lets just restrict this our Unspecified one
		 		if(ex.message.indexOf('Unspecified') == -1)
		 		{
		 			
		 		}
		 	}	
				
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
		
	}		
}

function cancelClicked() {
	document.getElementById('btnCancelHidden').value='clicked'
	document.getElementById('btnSaveHidden').value='';
		if (submitcount==0)
		{
			try
		  	{
				submitcount++;
				gTabClick=true;
				prevPolicyForm.submit();
			}
			catch(ex)
		 	{
		 		//don't want a genuine error
		 		//lets just restrict this our Unspecified one
		 		if(ex.message.indexOf('Unspecified') == -1)
		 		{
		 			
		 		}
		 	}	
		} else
		{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
			return false;
        }
	


}

var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){

	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		return false
	}
return true
}
</script>
<div style="text-align:right;font-size:70%">To add previous policies
click the "+" sign below.</div>

<ctrl:list id="prevPol" action="prevPolicyAction" name="prevPolicies"
	title="prevPolicy.title" noframe="false" noheader="false" 
	refreshButton="false" createButton="true" width="100%">
	<ctrl:columntext title="prevPolicy.policy" property="previousPolicyID"
		width="60" />
	<ctrl:columntext title="prevPolicy.status" property="status" width="40" />
	<ctrl:columntext title="prevPolicy.PTD.label" property="ptd" width="50" />
	<ctrl:columntext title="prevPolicy.EffDate.label" property="effDate"
		width="60" />
	<ctrl:columntext title="prevPolicy.insuredName" property="insuredName"
		width="300" />
	<ctrl:columntext title="prevPolicy.shortTermPol"
		property="shortTermPolicy" width="70" />
	<ctrl:columnedit title="list.edit" onclick="gTabClick=true"/>
	<ctrl:columndelete title="list.delete" />
</ctrl:list>

<br>
<br>

<html:form action="/prevPolicyAction">
 
	<c:if test="${showPrevPolForm == true}">
		<!-- Set the display variable set to FALSE -->
		<c:set var="showPrevPolForm" scope="session" value="false"/>
		
		<forms:form caption="prevPolicy.detailsTitle" type="edit"
			name="prevPolicyForm" formid="prevPolicyForm">
			<forms:row>
				<forms:text onchange="gChangesWereMade=true" label="prevPolicy.prevPolicy" width="100"
					property="previousPolicyID" required="true" />
				<forms:text onchange="gChangesWereMade=true" label="prevPolicy.status" property="status" size="10" />
				<forms:text onchange="gChangesWereMade=true" label="prevPolicy.PTD" property="ptd" size="10"
					required="false" />
				<forms:text onchange="gChangesWereMade=true" label="prevPolicy.EffDate" property="effDate" size="10"
					required="false" />
			</forms:row>
			<forms:row>
				<forms:text onchange="gChangesWereMade=true" colspan="2" label="prevPolicy.insuredName"
					property="insuredName" maxlength="200" size="65" required="false" />
				<forms:select label="prevPolicy.shortTermPol" onchange="gChangesWereMade=true"
					property="shortTermPolicy" required="false">
					<base:option key="N">No</base:option>
					<base:option key="Y">Yes</base:option>

				</forms:select>

				<forms:buttonsection default="btnSave">
					<forms:button name="btnSave" onclick="validateInput();"
						text="list.update" />
					<forms:button name="btnCancel" onclick="cancelClicked()"
						text="list.cancel" />
				</forms:buttonsection>
			</forms:row>
		</forms:form>
	</c:if>

</html:form>

<util:jsp directive="endofpage" />
