<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ page import="com.cc.acmi.presentation.taglib.TagUtils"%>
<script language="Javascript">


var submitcount=0;

function validateInput() {
	
	var reqDate = medRecForm.requestDate.value;
	var firstName = medRecForm.firstName.value;
	var lastName = medRecForm.lastName.value;
	var physicianName = medRecForm.physicianName.value;
	var applicantType = medRecForm.applicantType.value;	
	
	var message = "Please correct the following errors:\n";
	var goodVals = true;		
	
	if (firstName == null || firstName.length == 0)
	{
		message += " - The First Name field is required.\n";
		goodVals = false;
	}
	if (lastName == null || lastName.length == 0)
	{
		message += " - The Last Name field is required.\n";
		goodVals = false;
	}
	if (physicianName == null || physicianName.length == 0)
	{
		message += " - The Physician Name field is required.\n";
		goodVals = false;
	}
	if (applicantType == null || applicantType.length == 0)
	{
		message += " - The Applicant Type field is required.\n";
		goodVals = false;
	}
	if (reqDate == null || reqDate.length == 0)
	{
		message += " - The Request Date field is required.\n";
		goodVals = false;
	}
	else if (reqDate != null && reqDate.length > 0) {
		if (isDate(reqDate) == false) {
			message += " - The Request Date format is invalid. Please use mm/dd/yyyy\n";
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
			submitcount++;
			medRecForm.submit();
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
	
	medRecForm.submit();
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
<html:form action="/medRecAction">
	<div style="text-align:right;font-size:70%">To request a medical record
	for an applicant click the "+" sign below.</div>
	<ctrl:list id="userlist1" action="medRecAction" name="medRecs"
		title="medRec.requests.list" noframe="false" noheader="false" width="100%"
		refreshButton="false" createButton="true">
		<ctrl:columntext title="medRec.fullName" property="fullName"
			width="150" />
		<ctrl:columntext title="medRec.reqDate.label" property="reqDate" width="60" />
		<ctrl:columntext title="medRec.appType" property="appType" width="30" />
		<ctrl:columntext title="medRec.physName" property="physicianName"
			width="150" />
		<ctrl:columntext title="medRec.physLoc" property="physicianLocated"
			width="80" />
		<ctrl:columntext title="medRec.phiMiss" property="phiMissing"
			width="60" />
		<ctrl:columnedit title="list.edit" />
		<ctrl:columndelete title="list.delete" />
	</ctrl:list>
	<br/><br/>
	<c:if test="${showMedRecForm == true}">
		<!-- Set the display variable set to FALSE -->
		<c:set var="showMedRecForm" scope="session" value="false"/>
	
		<forms:form width="100%" caption="medRec.title" type="edit"
			name="medRecForm" formid="medRecForm">
			<forms:row>
				<forms:text onchange="gChangesWereMade=true" label="user.form.fname" size="22" maxlength="30" property="firstName" required="true" />
				<forms:text onchange="gChangesWereMade=true" label="user.form.lname" size="22" maxlength="30" property="lastName" required="true" />
				<forms:text onchange="gChangesWereMade=true" label="form.doc.KeyAppMiddle" size="1" maxlength="1" property="initialMid" required="false" />
				<forms:text onchange="gChangesWereMade=true" label="form.doc.KeyAppSuffix" size="10" maxlength="50" property="suffix" required="false" />			
			</forms:row>
			<forms:row>			
				<forms:text onchange="gChangesWereMade=true" label="medRec.physName" size="22" maxlength="30" property="physicianName" required="true"  />
				<forms:checkbox onchange="gChangesWereMade=true" label="medRec.physLoc" property="physicianLocated" />
				<forms:checkbox onchange="gChangesWereMade=true" label="medRec.phiMiss" property="phiMissing" />
			</forms:row>
			<forms:row>
				<forms:select onchange="gChangesWereMade=true" label="medRec.appType" property="applicantType" required="true">
					<base:option key="">Not Selected</base:option>
					<base:option key="K">Key Applicant</base:option>
					<base:option key="D">Dependent</base:option>
					<base:option key="S">Spouse</base:option>
				</forms:select>
				<forms:text onchange="gChangesWereMade=true" label="medRec.reqDate" size="12" maxlength="23" property="requestDate"  required="true" />
				<forms:buttonsection default="btnSave" join="true">
					<forms:button name="btnSave" onclick="validateInput()" text="list.update" />
					<forms:button name="btnCancel" onclick="cancelClicked()" text="list.cancel" />
				</forms:buttonsection>
			</forms:row>
		</forms:form>
	</c:if>
</html:form>
<util:jsp directive="endofpage" />
