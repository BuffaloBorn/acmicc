var gIasChangesWereMade = false;
var gIasSaveClicked = false;
var submitcount=0;

var firstline = "Are you sure want to navigate away from this page?";
var middleline = "Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or \n click 'Cancel' to return to this page to save the changes made.";
var lastline = "Press OK to continue, or Cancel to stay on the current page."; 

var expDays = 1; // number of days the cookie should last

var exp = new Date();
exp.setTime(exp.getTime() + (expDays*24*60*60*1000));

function runUnloadIasValidation()
{	
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
//		if(askquestion())
//		{
//			setCookie('iaspopupclose', 'close', exp, '/acmicc/');
//		}
//		else
//		{
//			setCookie('iaspopup', 'open', exp, '/acmicc/');
//		}
		event.returnValue = middleline;
		setCookie('timer', 'stop', exp, '/acmicc/');
	}

}


function askquestion()
{
	
	var ret = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
	
	if(ret)
		return true;
	else
		return false;

}
function setIaspopupcloseToTrue()
{
	setCookie('iaspopupclose', 'open', exp, '/acmicc/');
}

function setIaspopupcloseToFalse()
{
	setCookie('iaspopupclose', 'close', exp, '/acmicc/');
}


function getEnclosingForm(node) {

	if (null != node && node.nodeName == 'FORM') {
		return node;
	}

	// search the form wich embbeds the Element
	var parent = node.parentNode;
	
	if (null == parent) return null;

	if (parent.nodeName == 'FORM') {
		return parent;
	} else {
		return arguments.callee(parent);
	}
}

function runPageValidationList()
{
	var happen = new Boolean(false);

	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			return happen;
			
		}
		else{
			happen = false;
			window.onbeforeunload=null;
			return happen;
			
		}
	}
	else
	{
		happen = true;
		window.onbeforeunload=null;
		return happen;
	}
}


function runPageValidationHref(linkobject)
{
	var happen = false;
	
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			window.location.href = linkobject.href;	
			return happen;
		}
		else{
			happen = false;
			return happen;
		}
	}
	else
	{
		happen = true;
		window.onbeforeunload=null;
		window.location.href = linkobject.href;
		return happen;
	}
}

function runPageValidation(formobject)
{
	if (checkChangedFeilds(formobject))
	{ 
		if (submitcount==0)
		{
			try
		  	{	
				submitcount++;
				CCUtility.submitEnclosingForm(formobject); 
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
	else 
	{
	   if (document.getElementById('btnBackHidden'))
	   {
	   		document.getElementById('btnBackHidden').value='';
	   } 
	   return false;
	}
}

function runPageValidationTwo(formobject)
{
	var happen = false;
	
	var form = getEnclosingForm(formobject);

	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			//form.submit();
			return happen;
		}
		else{
			happen = false;
			return happen;
		}
	}
	else
	{
		happen = true;
		window.onbeforeunload=null;
		//form.submit();
		return happen;
	}
}

function goDairy()
{
	var happen = false;

	if (gIasChangesWereMade == true && gIasSaveClicked == false)
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			window.location.href = "/acmicc/iuauser/iasdiary.do?intPolicy=true";
			//return happen;
		}
		else{
			happen = false;
			window.onbeforeunload=null;
			//return happen;
		}
	
	}
	else
	{
		happen = true;
		window.onbeforeunload=null;
		window.location.href = "/acmicc/iuauser/iasdiary.do?intPolicy=true";
		//return happen;
	}
	
	
}

function disableunload()
{
	window.onbeforeunload=null;
	window.onunload=null;
}

function runPageValidationLink()
{
	var happen = false;

	var form = document.getElementsByTagName("form")[0];
	
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			closeDeleteCookie();
			return happen;
		}
		else{
			happen = false;
			window.onbeforeunload=null;
			return happen;
		}
	}
	else
	{
		happen = true;
		window.onbeforeunload=null;
		closeDeleteCookie();
		return happen;
	}
//window.onbeforeunload = runUnloadIasValidation;	
}

function getFormAsString(form){
        
  //Setup the return String
  returnString ="";
   
        
  //Get the form values
  formElements= form.elements;
        
  //loop through the array, building up the url
  //in the format '/strutsaction.do&name=value'
 
  for(var i=formElements.length-1;i>=0; --i ){
        //we escape (encode) each value
        returnString+="&" 
        +escape(formElements[i].name)+"=" 
        +escape(formElements[i].value);
 }
        
 //return the values
 return returnString; 
}

function checkChangedFeilds()
{
		
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		var answer = confirm(firstline + "\n\n" + middleline + "\n\n" + lastline);
		
		if (answer)
		{
			happen = true;
			window.onbeforeunload=null;
			return true;
		}
		else{
			return false;
		}
	}
	else
	{
		window.onbeforeunload=null;
		return true;
	}		

}
