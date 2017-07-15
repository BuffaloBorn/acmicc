<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%@ page import="com.epm.acmi.struts.form.FormLoaderHelper, com.epm.acmi.struts.form.NBWSDetailsForm"%>
<%@ page import="com.epm.acmi.util.EPMHelper, com.epm.acmi.struts.Constants"%>




<style type="text/css">
	.ext-link {
		font-size: 9pt;
		color: navy;
		font-weight: bold;
	}
</style>


<script>
var radioStatus = new Object();
var submitcount = 0;
function radioToggle(obj) {

	if (radioStatus[obj.name]==null) { 
		radioStatus[obj.name] = obj.value;
		}
	else {
		if (radioStatus[obj.name] == obj.value) {
			obj.checked = false;
			radioStatus[obj.name] = null;
		}
		else {
			radioStatus[obj.name] = obj.value;
		}
	}
}


function confirmDecline() {
	
	var retVal = confirm("You are about to decline this task. Do you want to continue?");
	if (retVal == true) {
		NBWSDetailsForm.submit();
	}	
	
}

function saveClicked(activityButtonClicked) {
	
	var act = activityButtonClicked;    
    var message = "Please correct the following errors:\n";
	var goodVals = true;    
	var listBillVar = NBWSDetailsForm.listBill.value;
	var shortTermPolicyVar = NBWSDetailsForm.shortTermPolicy.value;
	var agentNumberVar = NBWSDetailsForm.agentNumber.value;
	var policyNumberVar = NBWSDetailsForm.policyNumber.value;
	var screenDateStringVar = NBWSDetailsForm.screenDateString.value;
	var taskNameVar = NBWSDetailsForm.taskName.value;
    
    NBWSDetailsForm.buttonSelected.value=act;    
    
    // Here just check the fields for correct formatting, dates, numbers, etc    	
	if (listBillVar.length > 0) {
		if(isNaN(listBillVar)) 
	   	{ 
			message += " - The List Bill field must only contain numbers.\n";
			goodVals = false;
		}
	}
	
	if (agentNumberVar.length > 0) {
			if(isNaN(agentNumberVar)) { 
			message += " - The Agent Number field must only contain numbers.\n";
			goodVals = false;
		}
	}
	
	if (policyNumberVar.length > 0) {
		if(isNaN(policyNumberVar)) 
	   	{ 
			message += " - The Policy# field must only contain numbers.\n";
			goodVals = false;
		}
	}
	
	if (shortTermPolicyVar.length > 0) {
		if(isNaN(shortTermPolicyVar)) 
	   	{ 
			message += " - The Short Term Policy field must only contain numbers.\n";
			goodVals = false;
		}
	}
	
	if (screenDateStringVar != null && screenDateStringVar.length > 0) {
		if (isDate(screenDateStringVar) == false) {
			message += " - The Screen Date format is invalid. Please use mm/dd/yyyy\n";
			goodVals = false;		
		}
	}
		
	if ((taskNameVar == "Conduct Initial Interview" || taskNameVar == "Conduct Follow Up Interview" || taskNameVar == "Revise Interview" ) && 
		theRole == "NC")
	{	
		var followUpDateStringVar = NBWSDetailsForm.followUpDateString.value;
		var followUpTimeStringVar = NBWSDetailsForm.followUpTimeString.value;
		
		if (followUpDateStringVar != null && followUpDateStringVar.length > 0) {
			if (isDate(followUpDateStringVar) == false) {
				message += " - The Follow Up Interview Date format is invalid. Please use mm/dd/yyyy\n";
				goodVals = false;		
			}
		}
		
		if (followUpTimeStringVar != null && followUpTimeStringVar.length > 0) {
			if (isTime(followUpTimeStringVar) == false) {
				message += " - The Follow Up Interview Time format is invalid. Please use hh:mm AM/PM or hh:mmAM/PM\n";
				goodVals = false;		
			}
		}
	}
    
    if (goodVals != true) {
		alert(message);		
	}
	else {
	    if (submitcount==0) {
			gSaveClicked=true;
			NBWSDetailsForm.submit();
			submitcount++;
			document.body.style.cursor = "wait";
		}   
		else 
      	{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
		    return false;
        }
	}
           
}

function theButtonClicked(activityButtonClicked) 
{	
    var act = activityButtonClicked;
    var retVal; 
    var retVal2;
    
    NBWSDetailsForm.buttonSelected.value=act;
    retVal = validateInput(act);
    
    if (retVal == true) {
    	retVal2 = confirm("You are about to complete this task. Do you want to continue?");
    
    	if (retVal2 == true) {
    	 if (submitcount==0) {
    		gSaveClicked=true;
			setCloseIasDiaryUnloadFlags();
			submitcount++;
			NBWSDetailsForm.submit();
			document.body.style.cursor = "wait";
		  }
		  else 
      	 {
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
		    return false;
         }
    	}
    	else {    		
    		document.getElementById('btnActivityActionHidden').value='';
    	}
    }
}

function validateInput(theButton) {
	
	var theButtonVar = theButton;
	var taskNameVar = NBWSDetailsForm.taskName.value;
	var pastActivities = NBWSDetailsForm.pastActivities.value;
	var pastActions = NBWSDetailsForm.pastActions.value;
	var taskNameVarSubString = taskNameVar.substring(0, 17);
	
	var screenDateStringVar = NBWSDetailsForm.screenDateString.value;
	var screenByVar = NBWSDetailsForm.screenBy.value; 
	var productNameVar = NBWSDetailsForm.productName.value;
	var screenDateStringVar = NBWSDetailsForm.screenDateString.value;
	var receivedDateStringVar = NBWSDetailsForm.receivedDateString.value;
	var listBillVar = NBWSDetailsForm.listBill.value;
	var shortTermPolicyVar = NBWSDetailsForm.shortTermPolicy.value;
	var agentNumberVar = NBWSDetailsForm.agentNumber.value;
	var policyNumberVar = NBWSDetailsForm.policyNumber.value;
	var keyApplicantFirstNameVar = NBWSDetailsForm.keyApplicantFirstName.value;
	var keyApplicantLastNameVar = NBWSDetailsForm.keyApplicantLastName.value;
	var agentNameVar = NBWSDetailsForm.agentName.value;
	var uwInitailVar = NBWSDetailsForm.uwInitail.value;
	var stateVar = NBWSDetailsForm.state.value;
	var pharmaProfileVar = NBWSDetailsForm.pharmaProfile.value; 
	var noResultsFoundVar = NBWSDetailsForm.noResultsFound.value; 
	var message = "Please correct the following errors:\n";
	var numInterviews = <%=session.getAttribute("numNewInterviewRequests")%>;
	var goodVals = true;			
	
	if (pastActivities.indexOf("Screen Application") >= 0) {
		
		if (screenByVar == null || screenByVar.length == 0)
		{
			message += " - The Screen By field is required.\n";
			goodVals = false;		
		}
		if (screenDateStringVar == null || screenDateStringVar.length == 0)
		{
			message += " - The Screen Date field is required.\n";
			goodVals = false;
		}
		else if (screenDateStringVar != null && screenDateStringVar.length > 0) {
			if (isDate(screenDateStringVar) == false) {
				message += " - The Screen Date format is invalid. Please use mm/dd/yyyy\n";
				goodVals = false;		
			}
		}
	} // END SCREEN APPLICATION Activity CHECKS
	

	if (pastActivities.indexOf("Enter Application") >= 0 &&
	   (theButtonVar == "To UW" || theButtonVar == "RX Query" ||
	    pastActions.indexOf("To UW") >=0 || pastActions.indexOf("RX Query") >=0))
	{
		if (policyNumberVar == null || policyNumberVar.length == 0)
		{
			message += " - The Policy Number field is required.\n";
			goodVals = false;		
		}
		else if (policyNumberVar.length > 0) {
			if (isNaN(policyNumberVar) || policyNumberVar == "0" || trim(policyNumberVar) == "") { 
				message += " - The Policy Number field must only contain numbers greater than zero.\n";
				goodVals = false;
			}
		}
		if (keyApplicantFirstNameVar == null || keyApplicantFirstNameVar.length == 0)
		{
			message += " - The Applicant First Name field is required.\n";
			goodVals = false;		
		}
		if (keyApplicantLastNameVar == null || keyApplicantLastNameVar.length == 0)
		{
			message += " - The Applicant Last Name field is required.\n";
			goodVals = false;			
		}
		if (agentNameVar == null || agentNameVar.length == 0)
		{
			message += " - The Agent Name field is required.\n";
			goodVals = false;		
		}			
		if (agentNumberVar == null || agentNumberVar.length == 0)
		{
			message += " - The Agent Number field is required.\n";
			goodVals = false;		
		}				
		if (productNameVar == null || productNameVar.length == 0)
		{
			message += " - The Product Name field is required.\n";
			goodVals = false;
		}
		if (stateVar == null || stateVar.length == 0)
		{
			message += " - The State field is required.\n";
			goodVals = false;		
		}	
	} // END ENTER APPLICATION Activity CHECKS
	
	
	if (pastActivities.indexOf("Initiate RX Query") >= 0) {		
		if (pharmaProfileVar == null || pharmaProfileVar.length == 0)
		{
			message += " - The RX Queried By field is required.\n";
			goodVals = false;		
		}		
	} // END INITIATE RX QUERY Activity CHECKS
	
	if (pastActivities.indexOf("Review Application") >= 0) {		
		if (uwInitailVar == null || uwInitailVar.length == 0)
		{
			message += " - The Underwriter Initial field is required.\n";
			goodVals = false;		
		}								
	} // END REVIEW APPLICATIONS Activity CHECKS
		
	
	if ((taskNameVar == "Resolve Screening Issue" || taskNameVar == "Resolve Screening Issue." || taskNameVar == "Resolve Initial Review Issue"  || taskNameVar == "Resolve Initial Review Issue." ) && 
		theRole == "UA" &&
		theButtonVar == "Return App")
	{
		var returnReasonCode = NBWSDetailsForm.returnCode.value;
	
		if (returnReasonCode == null || returnReasonCode == "")
		{
			message += " - The Return Reason Code field is required.\n";
			goodVals = false;		
		}	
	
	}// END RESOLVE SCREENING ISSUE/RESOLVE INTIAL REVIEW ISSUE Activity CHECKS
	
	
	if (taskNameVar == "Process D/I/W Letter"  &&
		theButtonVar == "Refund Rqrd")
	{
		var refundDays = NBWSDetailsForm.numberOfDaysRefund.value;
	
		if (refundDays == null || refundDays == "")
		{
			message += " - The Refund Time in Days field is required.\n";
			goodVals = false;		
		}	
	
	}// END Process D/I/W Letter Activity CHECKS
	
	if (taskNameVar == "Make Underwriting Decision"  &&
		theButtonVar == "Apprv o/t Appld - No Quote")
	{
		var AOTSReasonCode = NBWSDetailsForm.reasonCode.value;
	
		if (AOTSReasonCode == null || AOTSReasonCode == "")
		{
//			message += " - The AOTS Return Reason Code field is required.\n";
			message += " - The Apprv o/t Appld Reason is required.\n";
			goodVals = false;		
		}	
	
	}// END Make Underwriting Decision Activity CHECKS
		
	
	if (theButtonVar == "Addl Info") {
		if (NBWSDetailsForm.memoAttachedYesNo[0].checked == false &&
			NBWSDetailsForm.memoAttachedYesNo[1].checked == false &&
			NBWSDetailsForm.paraMedReqd.checked == false &&
			NBWSDetailsForm.phoneInterviewRequired.checked == false &&
			NBWSDetailsForm.dmvReqd.checked == false &&
			NBWSDetailsForm.medRecReqd.checked == false &&
			NBWSDetailsForm.medConsultReqd.checked == false) {
			
			message += " - An Additional Info field is required.\n";
			goodVals = false;	
		}	
		
		if (NBWSDetailsForm.phoneInterviewRequired.checked == true &&
			numInterviews < 1) {			
			message += " - Need new interviews if Addl Info button was chosen\n";
			message += " and Phone Interview checkbox has been checked.\n";
			goodVals = false;	
		}		
		
		if (NBWSDetailsForm.phoneInterviewRequired.checked == false &&
			numInterviews > 0) {			
			message += " - There are new interview requests. Need Phone Interview checkbox to be checked.\n";
			goodVals = false;	
		}							
	} // END Additional Info Action CHECKS
		
		
	if (numInterviews > 0 && theRole == "UW" && theButtonVar != "Addl Info") {
		message += " - Only 'Addl Info' button can be selected if there are new interview requests.\n";
		goodVals = false;	
	}
		
	if ((taskNameVar == "Conduct Initial Interview" || taskNameVar == "Conduct Follow Up Interview" || taskNameVar == "Revise Interview" ) && 
		theRole == "NC")
	{	
		var followUpDateStringVar = NBWSDetailsForm.followUpDateString.value;
		var followUpTimeStringVar = NBWSDetailsForm.followUpTimeString.value;
		var initialInterviewNurseConsultantVar = NBWSDetailsForm.initialInterviewNurseConsultant.value;
			
		if (initialInterviewNurseConsultantVar == null || initialInterviewNurseConsultantVar.length == 0 || initialInterviewNurseConsultantVar == "Not Selected") {
			message += " - Nurse Consultant should be selected\n";
			goodVals = false;					
		}
			
		if (theButtonVar == "Follow Up Reqd")
		{
			if (followUpDateStringVar == null || followUpDateStringVar.length == 0) {
				message += " - Follow Up Interview Date should not be blank if follow up interview is required\n";
				goodVals = false;		
			}
			
			if (followUpTimeStringVar == null || followUpTimeStringVar.length == 0) {
				message += " - Follow Up Interview Time should not be blank if follow up interview is required\n";
				goodVals = false;		
			}
			
			if (followUpDateStringVar != null && followUpDateStringVar.length > 0) {
				if (isDate(followUpDateStringVar) == false) {
					message += " - The Follow Up Interview Date format is invalid. Please use mm/dd/yyyy\n";
					goodVals = false;		
				}
			}
			
			if (followUpTimeStringVar != null && followUpTimeStringVar.length > 0) {
				if (isTime(followUpTimeStringVar) == false) {
					message += " - The Follow Up Interview Time format is invalid. Please use hh:mm:ss or hh:mm:ss AM/PM or hh:mm AM/PM\n";
					goodVals = false;		
				}
			}
		}
		
		if (theButtonVar == "Unable To Complete")
		{
			var unableToCompleteReasonVar = NBWSDetailsForm.unableToCompleteReason.value;
			
			if (unableToCompleteReasonVar == null || unableToCompleteReasonVar.length == 0 || unableToCompleteReasonVar == "Not Selected") {
				message += " - Unable to Complete Reason should be selected if 'Unable to Complete' button was chosen\n";
				goodVals = false;					
			}			
		}		
	} //END Nurse Consultant Actions check...
			
	if (listBillVar.length > 0) {
		if(isNaN(listBillVar)) 
	   	{ 
			message += " - The List Bill field must only contain numbers.\n";
			goodVals = false;
		}
	}
	if (agentNumberVar.length > 0) {
			if(isNaN(agentNumberVar)) { 
			message += " - The Agent Number field must only contain numbers.\n";
			goodVals = false;
		}
	}
	if (shortTermPolicyVar.length > 0) {
		if(isNaN(shortTermPolicyVar)) 
	   	{ 
			message += " - The Short Term Policy field must only contain numbers.\n";
			goodVals = false;
		}
	}

	if (goodVals != true) {
		alert(message);
		return false;	
	}
	else {
		return true;
	}		
}

function trim(s) {
  while (s.substring(0,1) == ' ') {
    s = s.substring(1,s.length);
  }
  while (s.substring(s.length-1,s.length) == ' ') {
    s = s.substring(0,s.length-1);
  }
  return s;
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

function isTime(strTime) {
   	var strTime1 = /^(\d{1,2})(\:)(\d{2})(\ )\w[AM|PM|am|pm]$/;
   	var strTime2 = /^(\d{1,2})(\:)(\d{2})\w[AM|PM|am|pm]$/;

   	var strFormat1 = strTime.match(strTime1);
   // Check to see if it matches one of the
   //     2 Format Strings.

   	if (strFormat1 != null) {
  		// Validate for this format: 3:48:01 PM

		if (strFormat1[1] > 12 || strFormat1[1] < 00) {
			return false;
		}
	
	
		if (strFormat1[3] > 59 || strFormat1[3] < 00) {
			return false;
		}
	
		if (strFormat1[4] > 59 || strFormat1[4] < 00) {
			return false;
		}
	} else
	{			
	   	var strFormat2 = strTime.match(strTime2);
	
	   	if (strFormat2 != null) {
	  		// Validate for this format: 3:48:01 PM
	
			if (strFormat2[1] > 12 || strFormat2[1] < 00) {
				return false;
			}
		
		
			if (strFormat2[3] > 59 || strFormat2[3] < 00) {
				return false;
			}
		
			if (strFormat2[4] > 59 || strFormat2[4] < 00) {
				return false;
			}
		} else
			return false;
	}
	
  	return true;
}


<% EPMHelper epmHelper = new EPMHelper();
	String role = epmHelper.getCurrentRoleFromWorkItemIdInSession(request);
%>
var theRole = "<%= role %>";

function openRoleAssignWindow() 
{
	if (gChangesWereMade == true && gSaveClicked == false) {
		returnval = confirm("Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or click 'Cancel' to return to this page to save the changes made.");
		if (returnval) 
		{			
			gChangesWereMade = false;
			

		}		
		
		if (!returnval) {
		 
			return; 
		}
	}
	
	if (submitcount==0) {
		
		window.open("/acmicc/userRoleAssign.do?selectedRole=" + theRole ,null,"resizable=no,left=200,top=200,height=275,width=440,status=no,toolbar=no,menubar=no,location=no");			
	} else
	{
      		alert("You have already submitted the request to server. Please wait until the page is refreshed.");
		return false;
        }
	
    
	//showModalDialog("/acmicc/userRoleAssign.do?selectedRole" + theRole,window,"status:false;dialogWidth:445px;dialogHeight:340px");
}

function resubmitReassignWindow() 
{
		if (submitcount==0) 
	{
		 submitcount++;
		 NBWSDetailsForm.buttonSelected.value="reassign";    
   		 NBWSDetailsForm.submit();
	} else
	{
      	alert("You have already submitted the request to server. Please wait until the page is refreshed.");
		return false;
    }
	
}

</script>




<html:form action="/nbs/details">

	<forms:form type="edit" caption="form.doc.applicantion"
		formid="NBWSDetailsForm" name="NBWSDetailsForm">
		<forms:section noframe="false">
			<forms:row width="100%">
				<forms:html colspan="2" align="right">
					<c:if test="${showManuallyStartedSession != null}">
						<font class="legend" color="red"><c:out
							value="THIS PROCESS WAS MANUALLY ACTIVATED." /></font>
					</c:if>
				</forms:html>
				<c:if test="${showFundedHSASession != null}">
					<forms:html align="right" >
						<font class="legend" color="red"> <c:out value="FUNDED HSA" /> </font>
					</forms:html>
				</c:if>
			</forms:row>
			<forms:row width="100%">
				<c:if test="${showOTHERPROCEXISTSession != null}">
					<forms:html align="right" >
						<font class="legend" color="red"> <c:out value="ONE OR MORE PROCESSES HAVE NOT BEEN COMPLETED FOR THIS APPLICATION" /> </font>
					</forms:html>
				</c:if>

			</forms:row>
			
			<forms:row>				
				<forms:text label="Details.ReceivedDate" property="receivedDateString" size="10" width="10" colspan="1"
					disabled="true" />
				<forms:select colspan="1" label="Details.Rewrite" onchange="gChangesWereMade=true"
					property="rewrite" required="false">
					<base:option key="N">No</base:option>
					<base:option key="Y">Yes</base:option>
				</forms:select>
				<forms:text label="Details.UWInitail" property="uwInitail" size="3" maxlength="3"
					width="3" colspan="2" disabled="false" onchange="gChangesWereMade=true" />
			</forms:row>
			
			<forms:row>
				<forms:text property="policyNumber" colspan="1" disabled="false" maxlength="15" onchange="gChangesWereMade=true">
					<forms:label label="Details.PolicyNumber" />
				</forms:text>
				<forms:text property="eft" label="Details.ETF" colspan="1" maxlength="50"
					disabled="false" onchange="gChangesWereMade=true" />
				<forms:text label="Details.ListBill" property="listBill" size="15" maxlength="9"
					width="15" colspan="1" disabled="false" onchange="gChangesWereMade=true" />
	
			</forms:row>
	
			<forms:row>
				<forms:text property="shortTermPolicy"
					label="Details.ShortTermPolicy" colspan="1" disabled="false" maxlength="50" onchange="gChangesWereMade=true" />
				<!-- USR 7930-->
				<forms:plaintext property="electronicApp" colspan="1"></forms:plaintext>											
				<!--  End USR 7930-->
			</forms:row>

			<forms:section noframe="true" title="NBWS.KeyAppliant">
			<forms:row>
				<forms:text property="keyApplicantFirstName" colspan="1" maxlength="30"
					disabled="false" onchange="gChangesWereMade=true">
					<forms:label label="Details.FirstName" />
				</forms:text>
				<forms:text property="keyApplicantLastName" colspan="1" maxlength="30"
					disabled="false" onchange="gChangesWereMade=true">
					<forms:label label="Details.LastName" />
				</forms:text>
				<forms:text property="keyApplicantMI" size="1" width="1" colspan="1" maxlength="1"
					disabled="false" onchange="gChangesWereMade=true">
					<forms:label label="Details.MI" />
				</forms:text>
			</forms:row>
			<forms:row>
				<forms:text label="Details.Suffix" property="keyApplicantSuffix" maxlength="4"
					size="4" width="4" colspan="1" onchange="gChangesWereMade=true" />
			</forms:row>
			<forms:html>
					&nbsp;
			</forms:html>

			<forms:row>
				<forms:select label="Details.State" property="state" 
					style="color: black" onchange="gChangesWereMade=true">
					<base:options property="statesMap" keyProperty="key" 
						labelProperty="value" filter="false" empty="Details.selectvalue" />
				</forms:select>
				<forms:text property="agentName" colspan="1" disabled="false" maxlength="50" onchange="gChangesWereMade=true">
					<forms:label label="Details.AgentName" />
				</forms:text>
				<forms:text property="agentNumber" size="5" width="5" colspan="1" maxlength="5"
					disabled="false" onchange="gChangesWereMade=true">
					<forms:label label="Details.AgentNumber" />
				</forms:text>
			</forms:row>
			<forms:row>
				<forms:select colspan="2" label="Details.ProductName" 
					property="productName" style="color: black" onchange="gChangesWereMade=true">
					<base:options property="productsMap" keyProperty="key" empty="Details.selectvalue"
						labelProperty="value" filter="false" />
				</forms:select>
				<forms:checkbox label="Details.FundedHSA"
					property="fundedHSAboolean" onclick="gChangesWereMade=true"/>
			</forms:row>
		</forms:section>	

			<forms:html>
					&nbsp;
			</forms:html>
			<forms:row>
				<forms:text property="screenBy" label="Details.ScreenBy" colspan="1"
					disabled="false" onchange="gChangesWereMade=true"/>
				<forms:text property="screenDateString" label="Details.ScreenDate" size="10" width="10" colspan="1"
					disabled="false" onchange="gChangesWereMade=true"/>
				<forms:text property="planId" label="Details.Plan" colspan="1" maxlength="50"
					disabled="false" onchange="gChangesWereMade=true" />
			
			</forms:row>
			<forms:row>				
				<c:if test="${showIntervieweeName != null}">					
					<forms:text property="intervieweeName" label="Details.IntervieweeName" colspan="1" maxlength="50"
					disabled="true" onchange="gChangesWereMade=true" />					
				</c:if>
				<c:if test="${showIntervieweeName == null}">
					<forms:html colspan="1"></forms:html>
				</c:if>				
				<c:if test="${showNurseConsultant != null}">									
					<forms:text colspan="1" label="Details.InitialInterviewNurseConsultant" maxlength="50"
						property="initialInterviewNurseConsultant" style="color: black" onchange="gChangesWereMade=true" disabled="true">
					</forms:text>					
				</c:if>
				<c:if test="${showNurseConsultant == null}">
					<forms:html colspan="1"></forms:html>
				</c:if>
				<forms:text property="network" label="Details.Network" colspan="1" maxlength="50"
					disabled="false" onchange="gChangesWereMade=true" />
			</forms:row>
			<forms:row join="true">				
				<c:if test="${showAndEnableFollowUpDateAndTime != null}">
					<forms:text property="followUpDateString" label="Details.FollowUpDate" size="10" width="10" colspan="1"
						disabled="false" onchange="gChangesWereMade=true"/>
					<forms:text property="followUpTimeString" label="Details.FollowUpTime" size="10" width="10" colspan="1"
					disabled="false" onchange="gChangesWereMade=true"/>
				</c:if>				
				<c:if test="${showAndEnableFollowUpDateAndTime == null}">
					<forms:html colspan="2"></forms:html>
				</c:if>
				<forms:text property="deductible" label="Details.Deductible" maxlength="50"
					colspan="1" disabled="false" onchange="gChangesWereMade=true" />
			</forms:row>
			<forms:row join="true">		
				<c:if test="${showUnableToCompleteReason != null}">						
					<c:if test="${enableUnableToCompleteReason != null}">			
						<forms:select colspan="2" label="Details.UnableToCompleteReason" 
							property="unableToCompleteReason" style="color: black" onchange="gChangesWereMade=true" disabled="false">
							<base:options property="unableToCompleteReasonMap" keyProperty="key" empty="Details.selectvalue"
								labelProperty="value" filter="false" />
						</forms:select>
					</c:if>					
					<c:if test="${enableUnableToCompleteReason == null}">			
						<forms:select colspan="2" label="Details.UnableToCompleteReason" 
							property="unableToCompleteReason" style="color: black" onchange="gChangesWereMade=true" disabled="true">
							<base:options property="unableToCompleteReasonMap" keyProperty="key" empty="Details.selectvalue"
								labelProperty="value" filter="false" />
						</forms:select>
					</c:if>
				</c:if>
				<c:if test="${showUnableToCompleteReason == null}">	
					<forms:html colspan="2"></forms:html>
				</c:if>
				<forms:text property="coPay" label="Details.CoPay" colspan="1" maxlength="50" 
					disabled="false" onchange="gChangesWereMade=true" />
			</forms:row>
			<forms:row join="true">					
				<c:if test="${showScheduledDateTime != null}">						
					<forms:plaintext property="interviewScheduledDateTimeString" colspan="2"></forms:plaintext>
				</c:if>
				<c:if test="${showScheduledDateTime == null}">	
					<forms:html colspan="2"></forms:html>
				</c:if>
				<forms:text property="coInsurance" label="Details.CoInsurance"
					colspan="1" disabled="false" onchange="gChangesWereMade=true" />
			</forms:row>
			<forms:row>
				<forms:text property="pharmaProfile" colspan="1" disabled="false" maxlength="50" onchange="gChangesWereMade=true">
					<forms:label label="Details.PharmaProfile" />
				</forms:text>
				<forms:text property="noResultsFound" colspan="2" disabled="false" size="77" maxlength="77" onchange="gChangesWereMade=true">
					<forms:label label="Details.NoResultsFound" />
				</forms:text>
			</forms:row>
			<forms:html>
					&nbsp;
			</forms:html>
			<forms:row>
			<c:if test="${showDeclineTemplateNumberOfDays != null}">
					<forms:select label="Details.NumberOfDays"
						property="numberOfDaysRefund" style="color: black" onchange="gChangesWereMade=true">
						<base:option key="">Not Selected</base:option>
						<base:option key="4">4</base:option>						
						<base:option key="5">5</base:option>
						<base:option key="6">6</base:option>
						<base:option key="7">7</base:option>
						<base:option key="8">8</base:option>
						<base:option key="9">9</base:option>
						<base:option key="10">10</base:option>
						<base:option key="11">11</base:option>
						<base:option key="12">12</base:option>
						<base:option key="13">13</base:option>
						<base:option key="14">14</base:option>
						<base:option key="15">15</base:option>
						<base:option key="16">16</base:option>
						<base:option key="17">17</base:option>
						<base:option key="18">18</base:option>
						<base:option key="19">19</base:option>
						<base:option key="20">20</base:option>
						<base:option key="21">21</base:option>
					</forms:select>
			</c:if>
			<%
				String taskName =  (String)session.getAttribute(Constants.taskName);
				
				// Changed based on Karen's input 2/22/06 - Dragos Mandruleanu
				//if (taskName.equals("Screen Application") || taskName.trim().equals("Enter Application") || taskName.equals("Review Application")) {
				
				if (role != null && role.equals("UA") && 
					(taskName.trim().equals("Resolve Screening Issue.") || taskName.trim().equals("Resolve Screening Issue") || taskName.trim().equals("Resolve Initial Review Issue") || taskName.trim().equals("Resolve Initial Review Issue."))) 
				{
			%>
			
				<forms:select label="Details.ReturnReason" property="returnCode" 
					style="color: black" colspan="2" width="410" onchange="gChangesWereMade=true">
					<base:options property="returnReasonMap" keyProperty="key" empty="Details.selectvalue"
						labelProperty="value" filter="false" />
				</forms:select>
			<%
				}
			%>
			
			</forms:row>
			
			<forms:row>
			<%
				String tname =  (String)session.getAttribute(Constants.taskName);
				if (tname.equals("Make Underwriting Decision")) {
			%>
				<forms:select label="Details.OTSReason" property="reasonCode" 
					style="color: black" onchange="gChangesWereMade=true">
					<base:options property="otsReasonMap" keyProperty="key" empty="Details.selectvalue"
						labelProperty="value" filter="false"  />
				</forms:select>
			<%
				}
			%>
			</forms:row>
			
			<!--  USR 8399-1 CHANGES -->
			<forms:row>
			<%
				String tname1 =  "";
				String reasonC = "";
				NBWSDetailsForm form =  (NBWSDetailsForm)session.getAttribute("NBWSDetailsForm");
				
				if (session.getAttribute(Constants.taskName) != null)
					tname1 = (String)session.getAttribute(Constants.taskName);
				
				if (form.getReasonCode() != null) 
					reasonC = form.getReasonCode().trim();
					
				if (tname1.equals("Verify File for Policy Issue") && reasonC.length() == 0) {
			%>
				<forms:select label="Details.OTSReason" property="reasonCode" 
					style="color: black" onchange="gChangesWereMade=false">
					<base:option key="">Not Selected</base:option>
				</forms:select>
			<%}else
				if (tname1.equals("Verify File for Policy Issue") && reasonC.length() != 0) {
			%>
				<forms:select label="Details.OTSReason" property="reasonCode" 
					style="color: black" onchange="gChangesWereMade=false">
					<base:options property="otsReasonMap" keyProperty="key" empty="Details.selectvalue"
						labelProperty="value" filter="false"  />
				</forms:select>
			<%  }%>
			</forms:row>
			<!--  Ends USR 8399-1 CHANGES -->
		
			</forms:section>


		<c:if test="${showAdditionalInfoUDAsSession != null}">
			<forms:row>
					<forms:checkbox label="Details.DeclineIndividual" property="declineIndividual" onchange="gChangesWereMade=true"/>
			</forms:row>
	
			<c:if test="${showRecordIncomingEXAMSession != null}">
				<forms:row>
					<forms:checkbox label="Details.RecordIncomingEXAM" property="recordIncomingEXAM" onchange="gChangesWereMade=true" />
				</forms:row>
			</c:if>
			<c:if test="${showRecordIncomingQUOTESession != null}">
				<forms:row>
					<forms:checkbox label="Details.RecordIncomingQUOTE" property="recordIncomingQUOTE" onchange="gChangesWereMade=true" />
				</forms:row>
			</c:if>
			
			
			<forms:section title="Details.Additional.Info">
				<forms:row>
  					<forms:radio onclick="radioToggle(this)"  property="memoAttachedYesNo" label="Details.MemoWithAttachment" value="Yes" onchange="gChangesWereMade=true"/>
				    <forms:radio onclick="radioToggle(this)" property="memoAttachedYesNo" label="Details.MemoNoAttachReqd" value="No" onchange="gChangesWereMade=true"/>
					<forms:checkbox label="Details.ParaMedReqd" property="paraMedReqd" onchange="gChangesWereMade=true" />
					<forms:checkbox label="Details.PhoneInterview" property="phoneInterviewRequired" onchange="gChangesWereMade=true" />
				</forms:row>
			
				<forms:row>
					<forms:checkbox label="Details.DmvReqd" property="dmvReqd" onchange="gChangesWereMade=true" />
					<forms:checkbox label="Details.MedRecReqd" property="medRecReqd" onchange="gChangesWereMade=true" />
					<c:if test="${showDRConsult != null}">
						<forms:checkbox label="Details.MedConsultReqd" property="medConsultReqd" onchange="gChangesWereMade=true" />
					</c:if>						
				</forms:row>
			</forms:section>
		</c:if>
		
		<c:if test="${showRecordIncomingMEMOSession != null}">
			<forms:row>
					<forms:checkbox label="Details.RecordIncomingMEMO" property="recordIncomingMEMO" onchange="gChangesWereMade=true" />
			</forms:row>
		</c:if>
		<c:if test="${showRecordIncomingMEDRECSession != null}">
			<forms:row>
					<forms:checkbox label="Details.RecordIncomingMEDREC" property="recordIncomingMEDREC" onchange="gChangesWereMade=true" />
			</forms:row>
		</c:if>
		
		
		<forms:section title="Details.std.actions">
			<forms:buttonsection align="left">
				<forms:button name="btnSave" text="button.title.update"
					title="button.title.save" onclick="saveClicked('save')" />
				<forms:button name="btnReassign" text="button.title.Reassign"
					title="button.title.Reassign" onclick="openRoleAssignWindow()" />
				<!-- This button has been removed as per ACMI's request -->
				<!--<xforms:button name="btnDecline" text="button.title.Decline"
					onclick="confirmDecline()" title="button.title.Decline" />-->
			</forms:buttonsection>
		</forms:section>

		<input type="hidden" name="buttonSelected"/>
		<html:hidden property="lastUpdatedDateTimeString"/>	
		<html:hidden property="taskName"/>
		<html:hidden property="pastActivities"/>
		<html:hidden property="pastActions"/>
		

	</forms:form>
	<br />
	<div align="right" class="section"><forms:form type="edit"
		styleClass="section" caption="NBWS.Details.Blank" style=""
		formid="ActivityActions" width="100%" name="ActivityActions"
		noframe="true">
		<ctrl:headline caption="Activity Actions"
			locale="false" />
	</forms:form></div>
</html:form>