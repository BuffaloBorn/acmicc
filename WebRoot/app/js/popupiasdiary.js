var url = "/acmicc/iuauser/iasdiary.do?intPolicy=true";

var iasdiary = null;
var iasdiaryAutoSaveWin = null;
var gChangesWereMade = false;
var gSaveClicked = false;
var gTabClick = false;
var gValidationFirst = false;
var gPushActivity = false;

var expDays = 1; // number of days the cookie should last

var exp = new Date();
exp.setTime(exp.getTime() + (expDays*24*60*60*1000));

function runUnloadValidation() 
{
	
	if (gChangesWereMade == true && gSaveClicked == false) 
	{
		event.returnValue = ("Changes were made and have not been saved. Click 'OK' to ignore these changes and proceed with your request, or click 'Cancel' to return to this page to save the changes made.");
		
		gValidationFirst=true;
	}
}

function runUnloadCloseIasDiary()
{
	
	if (iasdiary && gTabClick == false)
	{
		event.returnValue =("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok \nIf you do not want to go to worklist click on cancel")
	}
	
	var iaspopup = getCookie('iaspopup');
	
	if((iaspopup == 'open') && (gTabClick == false))
	{
		event.returnValue =("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok \nIf you do not want to go to worklist click on cancel")
	}
}

function runCloseIasDiaryUnload()
{	
	if((gTabClick == false) && (gValidationFirst == true) && (iasdiary))
	{
	
		alert("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok");
		
			runIasdiaryInt();
			closeIasdiary();
	}
	
	
	var iaspopup = getCookie('iaspopup');
	
	if((iaspopup == 'open') && (gTabClick == false))
	{
		alert("Your IAS session will be closed \nIf you have changes you want saved switch to that session before clicking Ok");	
		
		runIasdiaryInt();
		closeIasdiary();
		deleteCookie('iaspopup');
	}

}

function setCloseIasDiaryUnloadFlags()
{
	gTabClick = true;
	gValidationFirst = false;
	gPushActivity = true;
}

function runIasdiary()
{
	var width = 850;
	var height = 520;

	var winLeft = (screen.width-width)/2; 
	var winTop = (screen.height-(height+110))/2; 

	if(null == iasdiary || iasdiary.closed)
	{
		var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes';
		setCookie('iaspopup', 'open', exp);
		iasdiary =window.open(url,'iasdiaryname',options);
	}
	else
	{
		iasdiary.location.href = url;
	}
	
	if (window.focus) 
	{
		iasdiary.focus();
	}

	return false;			
}

function runIasdiaryInt()
{

	var width = 850;
	var height = 520;

	var winLeft = (screen.width-width)/2; 
	var winTop = (screen.height-(height+110))/2; 

	if(null == iasdiary || iasdiary.closed)
	{
		var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes';
		iasdiary =window.open(url,'iasdiaryname',options);
	}
	else
	{
		iasdiary.location.href = url;
	}
	
	if (window.focus) 
	{
		iasdiary.focus();
	}

	return false;			
}


function initializeVar() {

}

	function closeIasdiaryCookie()
	{
		var iaspopup = getCookie('iaspopup')
		
		if(iaspopup == 'open')
		{
			runIasdiary();
			closeIasdiary();
			deleteCookie('iaspopup');
		}

	}

function closeIasdiary()
{
	if(null != iasdiary)
	{
		iasdiary.close();
	}
}

function whichElement(e)
{
	var targ;
	
	if (!e)
  	{
  		 e=window.event;
  	}
  	
	if (e.target)
  	{
  		targ=e.target;
  	}
	else if (e.srcElement)
  	{
  		targ=e.srcElement;
  	}

	if (targ.nodeType==3) // defeat Safari bug
  	{
  		targ = targ.parentNode;
  	}
	
	var tname;
	tname=targ.tagName;

	if (targ.nodeName.toLowerCase() == 'span')
	{
		tabAnchor = false;
	}
}
function addEvent(elm, evType, fn, useCapture) 
{
	// cross-browser event handling for IE5+, NS6+ and Mozilla/Gecko
	// By Scott Andrew
	if (elm.addEventListener) 
	{
		elm.addEventListener(evType, fn, useCapture);
		return true;
	}
	else if (elm.attachEvent) 
	{
		var r = elm.attachEvent('on' + evType, fn);
		return r;
	}
	else
	{
		elm['on' + evType] = fn;
	}
}

function removeEvent(elm, evType, fn, useCapture) 
{
	// cross-browser event handling for IE5+, NS6+ and Mozilla/Gecko
	// By Scott Andrew
	if (elm.removeEventListener) 
	{
		elm.removeEventListener(evType, fn, useCapture);
		return true;
	}
	else (elm.detachEvent) 
	{
		var r = elm.detachEvent('on' + evType, fn);
		return r;
	}
	
}



function handleLink(e) 
{
	var el;
	
	if (window.event && window.event.srcElement)
		el = window.event.srcElement;
	if (e && e.target)
		el = e.target;
	if (!el)
		return;
	
	while (el.nodeName.toLowerCase() != 'a' && el.nodeName.toLowerCase() != 'body')
		el = el.parentNode;
	
	if (el.nodeName.toLowerCase() == 'body')
		return;

	//removeEvent(window, 'unload', closeIasdiary, false);
}

function removeCloseIasdiary()
{
	removeEvent(window, 'unload', closeIasdiary, false);	
}


function addLoadListener(fn) 
{ 
 if (typeof window.addEventListener != 'undefined') 
 { 
   window.addEventListener('load', fn, false); 
 } 
 else if (typeof document.addEventListener != 'undefined') 
 { 
   document.addEventListener('load', fn, false);
 } 
 else if (typeof window.attachEvent != 'undefined') 
 { 
   window.attachEvent('onload', fn); 
 } 
 else 
 { 
   var oldfn = window.onload; 
   if (typeof window.onload != 'function') 
   { 
     window.onload = fn; 
   } 
   else 
   { 
     window.onload = function() 
     { 
       oldfn(); 
       fn(); 
     }; 
   } 
 } 
}

function addClickListener(elm, fn, useCapture) 
{ 
 if (typeof elm.addEventListener != 'undefined') 
 { 
   elm.addEventListener('click', fn, useCapture); 
 } 
 else if (typeof elm.attachEvent != 'undefined') 
 { 
  	elm.attachEvent('onclick', fn);
 } 
 else 
 { 
   var oldfn = elm.onclick; 
   if (typeof elm.onclick != 'function') 
   { 
     elm.onclick = fn; 
   } 
   else 
   { 
     elm.onclick = function() 
     { 
       oldfn(); 
       fn(); 
     }; 
   } 
 } 
}


function addListeners() 
{
	if (!document.getElementById)
		return;
	
	
	var all_links = document.getElementsByTagName('a');
	
	for (var i = 0; i < all_links.length; i++) 
	{
		
		if (all_links[i].title != 'IAS Diary')
		{
			addEvent(all_links[i], 'click', handleLink, false);
			//all_links[i].onclick = cancelClick;
		}
		else
		{
			addClickListener(all_links[i], runIasdiary, false);
			addClickListener(all_links[i], handleLink, false);
		}
	}
	
	addEvent(window, 'unload', closeIasdiary, false);
}

//addLoadListener(init);
//addLoadListener(addListeners);
//addEvent(window, 'unload', closeIasdiary, false);

	function setCookie (name, value) {
	  var argv = setCookie.arguments;
	  var argc = setCookie.arguments.length;
	  var expires = (argc > 2) ? argv[2] : null;
	  var path = (argc > 3) ? argv[3] : null;
	  var domain = (argc > 4) ? argv[4] : null;
	  var secure = (argc > 5) ? argv[5] : false;
	  
	  deleteCookie(name);
	  
	  document.cookie = name + "=" + escape (value) +
	    ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
	    ((path == null) ? "" : ("; path=" + path)) +
	    ((domain == null) ? "" : ("; domain=" + domain)) +
	    ((secure == true) ? "; secure" : "");
	}


	function getCookie(searchName)
	{
		var cookies = document.cookie.split(";");
		
		for (var i = 0; i < cookies.length; i++)
		{
			var cookieCrumbs = cookies[i].split("=");
			var cookieName = cookieCrumbs[0];
			var cookieValue = cookieCrumbs[1];
		
			if (cookieName == searchName)
			{
				return cookieValue;
			}
		}
		return false;
	}
	
	
	function deleteCookie (name) 
	{
  		var exp = new Date();
  		exp.setTime (exp.getTime() - 1);
  		var cval = getCookie (name);
  		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
	}		
	
	//window.setInterval(function() { getCookie('child') == 'closed' ? self.close() : ''; }, 1000);