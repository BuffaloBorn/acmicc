var iasdiary = null;
var iasdiaryAutoSaveWin = null;

function runIasdiary()
{
	url = "/acmicc/iuauser/iasdiary.do?intPolicy=true";

	var width = 850;
	var height = 520;

	var winLeft = (screen.width-width)/2; 
	var winTop = (screen.height-(height+110))/2; 

	if(null == iasdiary || iasdiary.closed)
	{
		var options = 'width=' + width  + ', height=' + height + ', top='+ winTop + ', left='+ winLeft  + ',fullscreen=no,toolbar=yes,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes';
		iasdiary =window.open(url,'name',options);
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

function closeIasdiary()
{
	if(iasdiary)
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

addLoadListener(init);
//addLoadListener(addListeners);
//addEvent(window, 'unload', closeIasdiary, false);

