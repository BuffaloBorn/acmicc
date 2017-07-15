var autoSave = null;
var gIASChangesWereMade = false;
var gIASSaveClicked = false;
var iasdiaryAutoSaveWin = null;

			
function autoSavePopUp()
{
	var width = 850;
	var height = 520;

	var winLeft = (screen.width-width)/2; 
	var winTop = (screen.height-(height+110))/2; 

	if (gIASChangesWereMade == true && gIASSaveClicked == false) 
	{
		document.forms('<%=(String) session.getAttribute("currentPageForm")%>').autoSave.value = 'true';
		document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
		
		var url = document.getElementById("currentPageURL").value; 
		
		var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes';
		iasdiaryAutoSaveWin =window.open(url,'',options);
	
	}				
}

function closeWin()
{
	self.close();
}

function updateBtnClick()
{
	document.forms('<%=(String) session.getAttribute("currentPageForm")%>').btnEditHidden.value = 'clicked';
	document.forms('<%=(String) session.getAttribute("currentPageForm")%>').submit();
	closeWin();
	return false;
}