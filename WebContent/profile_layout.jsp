<!--
  The MIT License (MIT)
  Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to 
  deal in the Software without restriction, including without limitation the
  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
  sell copies of the Software, and to permit persons to whom the Software is 
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in 
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
  THE SOFTWARE.
-->
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="createAccount_style.css" rel="stylesheet" type="text/css" />
<title>Edit Profile</title>
</head>
<%
	String userId = request.getParameter("id");

	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}

	Account user = new Account(userId);
	String status = user.getInfo("status") == "s" ? "admin" : "user";
	boolean male = user.getInfo("gender") == "m";
	String email = user.getInfo("email");
	String password = user.getInfo("password");
%>


<script language="javascript" type="text/javascript">
	function judge(){   	
		// email
   		var pos1 = document.forms[0].email.value.indexOf("@");
   		var pos2 = document.forms[0].email.value.indexOf(".");
   		if (pos1 == -1 && pos2 == -1){
     		alert("Please fill in the correct email address!");
      		document.forms[0].email.focus( );
      		document.forms[0].email.select( );
      		return false;
   		}
		return true;
	}
</script>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1><%=userId%></h1>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
					<br>
					<p>
						Registration Time:
						<%=status%></p>
					<p>
						Status:
						<%=status%></p>
				</div>
			</div>
		</div>
	</div>

	<center>
		<br> <a href="password.jsp">Change Password</a>
		<form action="SaveProfile" method="post" onsubmit="return judge()">
			<p>
				Gender: <input type="radio" name="gender" value="male"
					checked=<%=male%>>Male <input type="radio" name="gender"
					value="female" checked=<%=male%>>Female
			</p>
			<p>
				Email: <input type="text" name="email" value="<%=email%>">
			</p>
			<p>
				<input type="submit" value="Save">
			</p>
		</form>
	</center>
</body>
</html>
