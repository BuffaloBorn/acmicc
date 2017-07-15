var gIasChangesWereMade = false;
var gIasSaveClicked = false;

var firstline = "Are you sure want to navigate away from this page?";
var middleline = "Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or \n click 'Cancel' to return to this page to save the changes made.";
var lastline = "Press OK to continue, or Cancel to stay on the current page."; 


function runUnloadIasValidation()
{
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		event.returnValue = middleline;
		gIasChangesWereMade =false;
		resetCookieToClose();
	}

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

function runPageValidation(formobject)
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
			form.submit();
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
		form.submit();
		return happen;
	}
}

function runPageValidationLink()
{
	var happen = false;

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
//			closeDeleteCookie();
			alert(window.location);
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
	
}
