<%@ page language="java" import="com.epm.acmi.struts.Constants,com.epm.acmi.struts.form.DocumentMetaDataForm,com.epm.acmi.struts.form.dsp.DisplayDocument"%>
<%@ page import="com.epm.acmi.struts.form.dsp.DocumentTypeList"%>
<%@ page import="com.epm.acmi.struts.form.dsp.DisplayDocument"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/cc-base.tld" prefix="base"%>
<%@ taglib uri="/WEB-INF/tlds/cc-forms.tld" prefix="forms"%>
<%@ taglib uri="/WEB-INF/tlds/cc-controls.tld" prefix="ctrl"%>
<%@ taglib uri="/WEB-INF/tlds/cc-utility.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds-jstl/jstl-c.tld" prefix="c"%>
<%pageContext.setAttribute(com.cc.framework.Globals.LOCALENAME_KEY, "true");%>
<%   DocumentMetaDataForm dmf1 = (DocumentMetaDataForm)request.getAttribute("docUpdateMetaDataForm");
	 boolean isapplicantEditable = false;
	 boolean isotherEditable = false;
	 boolean isemployerReqd = false;
	
	 if (null != dmf1 ) {
	 String docCode1 = dmf1.getDocCode();
	
	     if (null != docCode1) {
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
<script language='JavaScript' src='app/help/help.js'></script>

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
	 	var index = docUpdateMetaDataForm.docCode.selectedIndex;
	 	docCode= docUpdateMetaDataForm.docCode.options[index].value;
	 	isdatachanged= true;
	 }
	 
	function validateDocCode(aform) {

		 var docCode=docUpdateMetaDataForm.docCode.options[docUpdateMetaDataForm.docCode.selectedIndex].value;
		 var goodVals1 = true;
	     var message1 = "Please correct the following errors:\n" ;
	     var length1 = docCode.length;
	     if (length1 == 0)
	     {
	            message1 += " - The Document Type field is required.\n";
	            goodVals1 = false;
	     }  
	      if (goodVals1 != true) {
	            alert(message1);         
	      } else {
	            docUpdateMetaDataForm.submit();
	      }    
	 }
	 
	 function validateInput() {
		  var policyNumber = docUpdateMetaDataForm.policyNumber.value;
		  var file = docUpdateMetaDataForm.file.value;
	      var state = docUpdateMetaDataForm.state.options[docUpdateMetaDataForm.state.selectedIndex].value;
		  var keyAppDOB = docUpdateMetaDataForm.keyAppDOB.value;
	      var agentNumber = docUpdateMetaDataForm.agentNumber.value;
	      var keyAppLastName = docUpdateMetaDataForm.keyAppLastName.value;
	      var keyAppFirstName = docUpdateMetaDataForm.keyAppFirstName.value;
		  var product = docUpdateMetaDataForm.product.options[docUpdateMetaDataForm.product.selectedIndex].value;
	      var keyAppSSN = docUpdateMetaDataForm.keyAppSSN.value;
   	  	  <% if (isapplicantEditable) { %>
	      	var appLastName = docUpdateMetaDataForm.appLastName.value;
	      	var appFirstName = docUpdateMetaDataForm.appFirstName.value;
	      <% }%>
   	          var employer = docUpdateMetaDataForm.employer.value;
	   	      var checkMaker = docUpdateMetaDataForm.checkMaker.value;
	   	

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
	       if (file == null || file.length == 0)
	      {
	            message += " - Please browse and select the file you want upload.\n";
	            goodVals = false;
	      }
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
	            docUpdateMetaDataForm.submit();
           		document.body.style.cursor = "wait";
	      }           
	}

	function cancelClicked() {
	      document.getElementById('btnCancelHidden').value='clicked'
	      document.getElementById('btnUploadHidden').value='';
	      docUpdateMetaDataForm.submit();
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
<div align="center">
	<html:form action="/main/nbw/upload" method="post" enctype="multipart/form-data">
		<forms:form type="edit" formid="frmEdit" caption="form.doc.upload.headline" width="100%" noframe="false">

			<forms:info width="100%">
			Select the type of document to be uploaded and click the <b>
					Go
				</b> button.  Next click the <b>
					Browse
				</b> button to locate and select the document to be uploaded.  Enter relevant information related to the document and click the <b>
					Upload
				</b> button.  Click the <b>
					Cancel
				</b> button to ignore any changes made and to reselect a different document
		</forms:info>

			<forms:section title="form.boc.select.doctype" join="true">
				<forms:html valign="top" width="100%" align="left" join="true">
			Select on document type, then click on <b>
					Go
				</b> button to load <font color="blue">
					Document Metadata
				</font> panel. Next click on <b>
					Browse
				</b>
			button to <font color="blue">
					select
				</font> the file from your
			machine. Enter relevant document information in Document Metadata panel and click on upload button
			</forms:html>
				<forms:row>
					<forms:select label="form.doc.DocType" property="docCode" required="true">
						<base:options property="documentTypesUploadable" keyProperty="docCode" labelProperty="docType" empty="form.select.doctypes" />
					</forms:select>
					<forms:buttonsection width="130" noframe="true">
						<forms:button name="btnGo" text="button.title.go" default="true" width="80" onclick="return validateDocCode(this.form);" />
					</forms:buttonsection>
					<forms:html label="form.doc.upload.file" valign="middle">
					<html:file property="file" size="45" />
					</forms:html>
				</forms:row>
			</forms:section>
		</forms:form>
		<br />

		<% if (null != dmf1) { %>
		<forms:form type="create" caption="form.doc.metadata.edit" formid="frmCreate" name="docUpdateMetaDataForm" width="100%" scope="request">
			<forms:section noframe="true" title="form.doc.applicantion">
				<forms:row>
					<forms:select label="form.doc.Product" property="product" colspan="2" style="color: black">
						<base:options property="productList" keyProperty="key" empty="Details.selectvalue" labelProperty="value" filter="false" />
					</forms:select>
					<forms:select label="form.doc.State" property="state" style="color: black">
						<base:options property="statesList" keyProperty="key" labelProperty="key" filter="false" />
					</forms:select>

				</forms:row>

				<forms:row>
					<forms:text property="policyNumber" colspan="1" maxlength="9">
						<forms:label label="form.doc.PolicyNumber" />
					</forms:text>
					<forms:text property="agentName" colspan="1" maxlength="30">
						<forms:label label="form.doc.AgentName" />
					</forms:text>
					<forms:text property="agentNumber" size="5" width="5" colspan="1" maxlength="5">
						<forms:label label="form.doc.AgentNumber" />
					</forms:text>
				</forms:row>
				<forms:row>
					<forms:text property="employer" colspan="1" disabled="false" maxlength="30">
						<forms:label label="form.doc.Employer" />
					</forms:text>
					<forms:text property="lbid" colspan="1" disabled="false" maxlength="15">
						<forms:label label="form.doc.LBID" />
					</forms:text>
				</forms:row>
			</forms:section>
			<forms:section noframe="true" title="form.doc.key.applicant">
				<forms:row>
					<forms:text property="keyAppLastName" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppLastName" />
					</forms:text>
					<forms:text property="keyAppFirstName" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppFirstName" />
					</forms:text>
					<forms:text property="keyAppMiddle" size="1" width="1" colspan="1" maxlength="1">
						<forms:label label="form.doc.KeyAppMiddle" />
					</forms:text>
					<forms:text label="form.doc.KeyAppSuffix" property="keyAppSuffix" maxlength="4" size="4" width="4" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text property="keyAppSSN" colspan="1" maxlength="9" disabled="false">
						<forms:label label="form.doc.KeyAppSSN" />
					</forms:text>
					<forms:text property="keyAppDOB" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppDOB" />
					</forms:text>
				</forms:row>
			</forms:section>
			<% if (isapplicantEditable) {
				%>
			<forms:section noframe="true" title="form.doc.applicant">
				<forms:row>
					<forms:text property="appLastName" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppLastName" />
					</forms:text>
					<forms:text property="appFirstName" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppFirstName" />
					</forms:text>
					<forms:text property="appMiddle" size="1" width="1" colspan="1" maxlength="1">
						<forms:label label="form.doc.AppMiddle" />
					</forms:text>
					<forms:text label="form.doc.AppSuffix" property="appSuffix" maxlength="4" size="4" width="4" colspan="1" />
				</forms:row>
				<forms:row>
					<forms:text property="appDOB" colspan="1" maxlength="30">
						<forms:label label="form.doc.KeyAppDOB" />
					</forms:text>
				</forms:row>
			</forms:section>
			<%}%>
			<forms:section noframe="true" title="form.doc.details">
				<forms:row>
					<forms:select label="form.doc.epmReviewed" property="epmReviewed">
						<base:option key="N">No</base:option>
						<base:option key="Y">Yes</base:option>
					</forms:select>
					<forms:select label="form.doc.CFP" property="cfp">
						<base:option key="N">No</base:option>
						<base:option key="Y">Yes</base:option>
					</forms:select>
					<forms:text property="checkMaker" colspan="1" maxlength="30">
						<forms:label label="form.doc.CheckMaker" />
					</forms:text>
				</forms:row>
				<forms:row>
					<forms:text property="docDescription" colspan="4" maxlength="30">
						<forms:label label="form.doc.DocDescription" />
					</forms:text>
				</forms:row>
				<forms:row join="true">
					<forms:buttonsection align="right" join="true">
						<forms:button name="btnUpload" text="button.title.uploaddoc" title="button.title.uploaddoc" onclick="return validateInput();" />
						<forms:button name="btnCancel" text="button.title.cancel" title="button.title.cancel" onclick="cancelClicked()"/>
					</forms:buttonsection>
				</forms:row>

			</forms:section>

		</forms:form>
		<% } %>

	</html:form>

</div>
