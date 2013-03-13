<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	}
	String id = request.getParameter("id");
	if (id == null) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
%>
<title>Invalid Information</title>
</head>
<body>
<h1><%=id%> Doesn't exist.</h1>
<a href="home.jsp?id=<%=guest%>">Back to home</a>
</body>
</html>