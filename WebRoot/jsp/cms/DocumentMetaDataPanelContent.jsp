<%@ page language="java" import="com.epm.acmi.struts.Constants, com.epm.acmi.struts.form.DocumentMetaDataForm,  com.epm.acmi.struts.form.dsp.DisplayDocument"%>
<%@ page import="com.epm.acmi.struts.form.dsp.DocumentTypeList" %>
<%@ page import="com.epm.acmi.struts.form.dsp.DisplayDocument" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%pageContext.setAttribute(com.cc.framework.Globals.LOCALENAME_KEY, "true");%>
<%   DocumentMetaDataForm dmf1 = (DocumentMetaDataForm)request.getAttribute("myFormControl");
     boolean isapplicantEditable = false;
	 boolean isotherEditable = false;
	 boolean isemployerReqd = false;
	 if (null != dmf1 ){	
		 String docCode1 = dmf1.getDocCode();
		 	 if (null != docCode1 ){	
		 	 if (Constants.applicantLevelDocs.indexOf(docCode1) >= 0) {
				  isapplicantEditable = true;
			 }
			 if (Constants.checkEFTDocs.indexOf(docCode1) >= 0) {
				  isotherEditable = true;	
			 }
			 if (Constants.checkLBSLBI.indexOf(docCode1) >= 0) {
				  isemployerReqd = true;	
			 }

		 }
	}
    
	  
%>

<script language="javascript">
	var isdatachanged= false;
	var docCode = <%= "\"" + request.getParameter("docType") +  "\";"%>
	var appDocTypes = <%= "\"" +Constants.applicantLevelDocs +"\";" %>
	
	function ConfirmChoice()
	{
		if (isdatachanged == true) 
		{
			var answer = confirm("If you do not save, your changes will be lost?");
			if (answer !=0)
			{
				isdatachanged == false;
				return ture;
			} else
			{
				return false;
			}
		}
	}
	 function dataChanged()
	 {
	 	var index = docMetaDataForm.docCode.selectedIndex;
	 	docCode= docMetaDataForm.docCode.options[index].value;
	 	isdatachanged= true;
	 }
	function validateInput() {
		  var policyNumber = docMetaDataForm.policyNumber.value;
	      var state = docMetaDataForm.state.options[docMetaDataForm.state.selectedIndex].value;
		  var keyAppDOB = docMetaDataForm.keyAppDOB.value;
	      var agentNumber = docMetaDataForm.agentNumber.value;
	      var keyAppLastName = docMetaDataForm.keyAppLastName.value;
	      var keyAppFirstName = docMetaDataForm.keyAppFirstName.value;
		  var product = docMetaDataForm.product.options[docMetaDataForm.product.selectedIndex].value;
	      var keyAppSSN = docMetaDataForm.keyAppSSN.value;
   	  	  <% if (isapplicantEditable) { %>
	      	var appLastName = docMetaDataForm.appLastName.value;
	      	var appFirstName = docMetaDataForm.appFirstName.value;
	      <% }%>
   	        var checkMaker = docMetaDataForm.checkMaker.value;
	   	    var employer = docMetaDataForm.employer.value;
	   	  

	      var message = "Please correct the following errors:\n";
	      var goodVals = true;          

				  
	      if (policyNumber == null || policyNumber.length == 0)
	      {
	            message += " - The Policy Number field is required.\n";
	            goodVals = false;
	      } else if(isNaN(policyNumber)) 
	   	  { 
			message += " - The Policy Number field must only contain numbers.\n";
			goodVals = false;
		  }
		  
	      if (state == null ||  state.length == 1)
	      {
	            message += " - The State field is required.\n";
	            goodVals = false;       
	      }
// product should not be required (issue #55) 03/20/06 cmontero   	      
//	       if (product == null || product.length == 0)
//	      {
//	            message += " - The Product field is required.\n";
//	            goodVals = false;       
//	      }
		   if (keyAppFirstName == null || keyAppFirstName.length == 0)
	      {
	            message += " - The keyAppFirstName field is required.\n";
	            goodVals = false;       
	      }
		   if (keyAppLastName == null || keyAppLastName.length == 0)
	      {
	            message += " - The keyAppLastName field is required.\n";
	            goodVals = false;       
	      }
	 	  if (keyAppSSN != null ){
	 			if( keyAppSSN.length > 0 && keyAppSSN.length < 9 ) {
		            if (isDate(keyAppSSN) == false) {
		                  message += " - The Key Applicant SSN is invalid.\n";
		                  goodVals = false;       
		            }
	            }
	      }
	      <% if (isapplicantEditable) { %>
	        if (appLastName == null || appLastName.length == 0)
		      {
		            message += " - The AppLastName field is required.\n";
		            goodVals = false;       
		      }
			  if (appFirstName == null || appFirstName.length == 0)
		      {
		            message += " - The AppFirstName field is required.\n";
		            goodVals = false;       
		      }
	      <% } %>
    	  <% if (isotherEditable) { %>
 				if (checkMaker == null || checkMaker.length == 0)
		      {
		            message += " - The CheckMaker field is required.\n";
		            goodVals = false;       
		      }
	      <% } %>
    	  <% if (isemployerReqd) { %>
		      if (employer == null || employer.length == 0)
		      {
		            message += " - The Employer field is required.\n";
		            goodVals = false;       
		      }
	      <% } %>
	      
	      if (goodVals != true) {
	            alert(message);         
	      }
	      else {
	            docMetaDataForm.submit();
           		document.body.style.cursor = "wait";
	      }           
	}
	
	function cancelClicked() {
	      document.getElementById('btnCancelHidden').value='clicked'
	      document.getElementById('btnSaveHidden').value='';
	      docMetaDataForm.submit();
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
<html:form action="/main/docexep/update">
	<forms:info width="930">
		Information related to this document is displayed below.  Click the <b>View</b> link to view the document.  Click the <b>Reload</b> button when changing the Document Type before making any changes to the information.  Click the <b>Save</b> button to save any changes made to the information.  Click the <b>Cancel</b> button to ignore any changes made and return to the Documents tab.
	</forms:info>
	<forms:form type="edit" caption="form.doc.metadata.edit"
		formid="frmEdit" name="myFormControl" width="930" scope="request">
			<forms:section noframe="true" title="form.doc.applicantion">
			<forms:row>
				<forms:select label="form.doc.DocType" property="docCode"  colspan="1"
					style="color: black">
					<base:options property="documentTypes" keyProperty="docCode" empty="Details.selectvalue"
						labelProperty="docType" filter="false" />
				</forms:select>
				<forms:buttonsection align="left" join="true" colspan="1" noframe="true">
				<forms:button name="btnReload" text="button.title.reload" 
						title="button.title.reload"  />
				</forms:buttonsection>
				<forms:html colspan="2">&nbsp;</forms:html>
			</forms:row>
			<forms:row>
				<forms:select label="form.doc.Product" property="product" colspan="2"
					style="color: black" >
					<base:options property="productList" keyProperty="key"  empty="Details.selectvalue"
						labelProperty="value" filter="false" />
				</forms:select>
				<forms:select label="form.doc.State" property="state" 
					style="color: black" >
					<base:options property="statesList" keyProperty="key" 
						labelProperty="value" filter="false" />
				</forms:select>
							
			</forms:row>
			
			<forms:row>
				<forms:text property="policyNumber" colspan="1"  maxlength="9" >
					<forms:label label="form.doc.PolicyNumber" />
				</forms:text>
				<forms:text property="agentName" colspan="1"  maxlength="30" >
					<forms:label label="form.doc.AgentName" />
				</forms:text>
				<forms:text property="agentNumber" size="5" width="5" colspan="1" maxlength="5"
					 >
					<forms:label label="form.doc.AgentNumber" />
				</forms:text>
			</forms:row>
			<forms:row>
				<forms:text property="employer" colspan="1" disabled="false" maxlength="30" >
					<forms:label label="form.doc.Employer" />
				</forms:text>
				<forms:text property="lbid" colspan="1" disabled="false" maxlength="15" >
					<forms:label label="form.doc.LBID" />
				</forms:text>
			</forms:row>
			</forms:section>
			<forms:section noframe="true" title="form.doc.key.applicant">
			<forms:row>
				<forms:text property="keyAppLastName" colspan="1" maxlength="30"
					 >
					<forms:label label="form.doc.KeyAppLastName" />
				</forms:text>
				<forms:text property="keyAppFirstName" colspan="1" maxlength="30"
					 >
					<forms:label label="form.doc.KeyAppFirstName" />
				</forms:text>
				<forms:text property="keyAppMiddle" size="1" width="1" colspan="1" maxlength="1"
					 >
					<forms:label label="form.doc.KeyAppMiddle" />
				</forms:text>
					<forms:text label="form.doc.KeyAppSuffix" property="keyAppSuffix" maxlength="4"
					size="4" width="4" colspan="1"  />
			</forms:row>
			<forms:row>
				<forms:text property="keyAppSSN" colspan="1" maxlength="9"
					disabled="false" >
					<forms:label label="form.doc.KeyAppSSN" />
				</forms:text>
				<forms:text property="keyAppDOB" colspan="1" maxlength="30"
					 >
					<forms:label label="form.doc.KeyAppDOB" />
				</forms:text>
			</forms:row>
			</forms:section>
			<% if (isapplicantEditable) {
				%>
			<forms:section noframe="true" title="form.doc.applicant">
			<forms:row>
				<forms:text property="appLastName" colspan="1" maxlength="30"
					 >
					<forms:label label="form.doc.KeyAppLastName" />
				</forms:text>
				<forms:text property="appFirstName" colspan="1" maxlength="30"
					 >
					<forms:label label="form.doc.KeyAppFirstName" />
				</forms:text>
				<forms:text property="appMiddle" size="1" width="1" colspan="1" maxlength="1"
					>
					<forms:label label="form.doc.AppMiddle" />
				</forms:text>
					<forms:text label="form.doc.AppSuffix" property="keyAppSuffix" maxlength="4"
					size="4" width="4" colspan="1"  />
			</forms:row>
			<forms:row>
			<forms:text property="appDOB" colspan="1" maxlength="30"
					>
					<forms:label label="form.doc.KeyAppDOB" />
				</forms:text>
			</forms:row>
		</forms:section>
		<%}%>
		<forms:section noframe="true" title="form.doc.details">
			<forms:row>
				<forms:select label="form.doc.epmReviewed" property="epmReviewed">
					<base:option key=""></base:option>
					<base:option key="Y">Yes</base:option>
					<base:option key="N">No</base:option>
				</forms:select>
				<forms:select  label="form.doc.CFP" property="cfp">
					<base:option key=""></base:option>
					<base:option key="Y">Yes</base:option>
					<base:option key="N">No</base:option>
				</forms:select>
				<forms:text property="checkMaker" colspan="1" maxlength="30">
					<forms:label label="form.doc.CheckMaker" />
				</forms:text>
			</forms:row>
			<forms:row>
				<forms:text property="docDescription" colspan="4" maxlength="30"
					>
					<forms:label label="form.doc.DocDescription" />
				</forms:text>
			</forms:row>
			<% if (null != dmf1){ %>
			<forms:html label="">
						<b>Click to   <%=dmf1.getURL()%>   the document</b>
			</forms:html>
			<%}%>
			<forms:row join="true">
				<forms:buttonsection align="right" join="true">
					<forms:button name="btnSave" text="button.title.update"
						title="button.title.save" onclick="return validateInput();" />
					<forms:button name="btnCancel" text="button.title.cancel"
						title="button.title.cancel"  onclick="cancelClicked()" />
				</forms:buttonsection>
			</forms:row>
		
		</forms:section>
	</forms:form>
</html:form>
