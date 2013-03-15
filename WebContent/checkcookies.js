function getCookie(c_name)
{
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++)
	{
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x=x.replace(/^\s+|\s+$/g,"");
		if (x==c_name)
		{
			return unescape(y);
		}
	}
	return null;
}

function checkCookie()
{
	var username = getCookie("username");
	if (username!=null && username!="")
	{
		alert("Welcome again " + username);
		window.location.href="home.jsp?id="+username;
	}
}