var gIasChangesWereMade = false;
var gIasSaveClicked = false;

function runUnloadIasValidation()
{
	if (gIasChangesWereMade == true && gIasSaveClicked == false) 
	{
		event.returnValue = ("Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or click 'Cancel' to return to this page to save the changes made.");
	}

}

