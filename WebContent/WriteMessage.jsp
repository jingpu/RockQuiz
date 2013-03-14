<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Composing Message</title>
</head>
<%
	String userId = request.getParameter("id");
	String toUser = request.getParameter("to");
	toUser = toUser == null ? "" : toUser;
	String guest = (String) session.getAttribute("guest");
	if (userId == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
%>

<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
			<h1>Compose Message</h1>
			<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	<form action="SendMessage" id="Compose" method="post"></form>
	<p>
		To <input type="text" name="toUser" form="Compose" value="<%=toUser%>">
	</p>
	<p>
		<input type="text" name="title" placeholder="Subject" form="Compose">
	</p>
	<textarea rows="10" cols="50" name="content" form="Compose"
		placeholder="Composing message here"></textarea>
	<p>
		<input type="submit" value="Send" form="Compose">
		<button onClick = "javascript:window.close();">Cancel</button>
	</p>
	</div>
	</div>
	</div>
	</div>
</body>
</html>