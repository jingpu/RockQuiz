<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
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
	Fri Mar 08 13:36:11 PST 2013
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
</body>
</html>