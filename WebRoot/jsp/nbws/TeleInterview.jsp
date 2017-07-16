<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>

<!-- Had to add the validate() method to override cc double submit proplem.
     if the user hits enter instead of clicking the mouse, the form would bypass
     js validation and execute the action. 
-->
<script type="text/javascript">	
window.onload = addherfListeners;
</script>

<script language="Javascript">



var submitcount=0;

function validate() {
	if (validateInput()) 
	{
		try
		{	
			gTabClick=true;
			tInterviewForm.submit();
		}
		catch(ex)
		{
			//don't want a genuine error
			//lets just restrict this our Unspecified one
			if(ex.message.indexOf('Unspecified') == -1)
			{
					
			}
	 	}		
		
	}
}
	  
function validateInput() {
	
	var reqDate = tInterviewForm.requestDate.value;
	var firstName = tInterviewForm.firstName.value;
	var lastName = tInterviewForm.lastName.value;
	var applicantType = tInterviewForm.applicantType.value;
	var uwName = tInterviewForm.uwIDName.value;	
	var exIndex = uwName.indexOf("|");
	uwName = uwName.substr(0, exIndex);
	
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
	if (uwName == null || uwName.length == 0) {
	        message += " - Underwriter field is required\n";
	        goodVals = false;
	}
	
	if (goodVals != true) {
		alert(message);		
	}
	else {
		gSaveClicked=true; 
		
		if (submitcount==0)
		{
			gTabClick=true;
			submitcount++;
			return true;
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
	
		try
		{	
			gTabClick=true;
			tInterviewForm.submit();
		}
		catch(ex)
		{
			//don't want a genuine error
			//lets just restrict this our Unspecified one
			if(ex.message.indexOf('Unspecified') == -1)
			{
					
			}
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
<html:form action="/telInterviewAction">	
	<c:if test="${isInterviewTabReadOnly != 'true'}">
		<div style="text-align:right;font-size:70%">To request a telephone interview
		for an applicant click the "+" sign below.</div>		
		<ctrl:list id="userlist1" action="telInterviewAction" name="interviewRecs"
			title="tinterview.requests.list" noframe="false" noheader="false" width="100%"
			refreshButton="false" createButton="true">
			
			<ctrl:columntext title="tinterview.fullName" property="fullName"
				width="150" />
	 		<ctrl:columntext title="tinterview.reqDate.label" property="reqDate" width="60" />
			<ctrl:columntext title="tinterview.appType" property="appType" width="30" />
	 		<ctrl:columntext title="tinterview.status" property="status" width="50" />
			<ctrl:columntext title="tinterview.requestedby" property="requestedBy" width="150" />		     
			<ctrl:columnedit title="list.edit" property="editable" onclick="gTabClick=true"/>
			<ctrl:columndelete title="list.delete" property="editable" onclick="gTabClick=true"/>
			
		</ctrl:list>	
	</c:if>
	<c:if test="${isInterviewTabReadOnly == 'true'}">		
		<ctrl:list id="userlist1" action="telInterviewAction" name="interviewRecs"
			title="tinterview.requests.list" noframe="false" noheader="false" width="100%"
			refreshButton="false">
			<ctrl:columntext title="tinterview.fullName" property="fullName"
				width="150" />
	 		<ctrl:columntext title="tinterview.reqDate.label" property="reqDate" width="60" />
			<ctrl:columntext title="tinterview.appType" property="appType" width="30" />
	 		<ctrl:columntext title="tinterview.status" property="status" width="50" />
			<ctrl:columntext title="tinterview.requestedby" property="requestedBy" width="150" />
			<ctrl:columntext title="list.edit" width="30"/>
			<ctrl:columntext title="list.delete" width="30"/>
		</ctrl:list>	
	</c:if>
	<br/><br/>
	
	<c:if test="${tInterviewForm.showNewInterviewRequest == true}">
		<!-- Set the display variable set to FALSE -->
		<c:set target="${tInterviewForm}" property="showNewInterviewRequest" value="false" />
		
		<forms:form width="100%" caption="tInterview.title" type="edit"
			name="tInterviewForm" formid="tInterviewForm">
			<html:hidden property="interviewRequestId"/>
			<forms:row>
  				<forms:text onchange="gChangesWereMade=true" label="tinterview.firstName" size="22" maxlength="30" property="firstName" required="true" /> 
				<forms:text onchange="gChangesWereMade=true" label="tinterview.lastName" size="22" maxlength="30" property="lastName" required="true" /> 
				<forms:text onchange="gChangesWereMade=true" label="tinterview.mi" size="22" maxlength="1" property="mi"/> 
				<forms:text onchange="gChangesWereMade=true" label="tinterview.suffix" size="22" maxlength="50" property="suffix"/>
			</forms:row>
			<forms:row>
				<forms:select onchange="gChangesWereMade=true" label="tinterview.appType" property="applicantType" required="true">
					<base:option key="">Not Selected</base:option>
					<base:option key="K">Key Applicant</base:option>
					<base:option key="D">Dependent</base:option>
					<base:option key="S">Spouse</base:option>
				</forms:select>			
				<forms:text onchange="gChangesWereMade=true" label="tInterview.reqDate" size="12" maxlength="23" property="requestDate" required="true" />
   			    <forms:select onchange="gChangesWereMade=true" label="tInterview.RequestedBy" property="uwIDName" required="true">
   					<base:options property="uwList"/>   
				</forms:select> 
				
				<forms:buttonsection default="" join="true">
					<forms:button name="btnSave" onclick="validate();" text="list.update" />
					<forms:button name="btnCancel" onclick="cancelClicked();" text="list.cancel" />
				</forms:buttonsection>
			</forms:row>
		</forms:form>
	</c:if>
</html:form>

