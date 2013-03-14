<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="user.*"%>
<%@ page import="quiz.*"%>
<%@ page import="util.Helper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (userId == null && UserManager.alreadyExist(guest)) {
		response.sendRedirect("myfields.jsp?id=" + guest);
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
	Account user = new Account(userId);
	String[] categories = Helper.string2Array(user.getInfo("category"));
%>
<title>Following fields - <%=userId%></title>
</head>
<body>
	<h2>
		<a href="home.jsp?id=<%=guest%>" target="_top">Home</a>
	</h2>
	<h2>Interesting Fields</h2>
	<%
		for (int i = 0; i < categories.length; i++) {
	%>
	<h3>
		<a href='myfields_search.jsp?s=d&q=<%=categories[i]%>'
			target='another'><%=categories[i]%></a>
	</h3>
	<%
		}
	%>
	<h3>Other categories:</h3>
	<form action="myfields_search.jsp" method="post" target='another'>
		<input type="hidden" name="s" value="d"> <input type="search"
			name="q" class="text" placeholder="Search category"><input
			type="submit" value="Search">
	</form>

</body>
</html>