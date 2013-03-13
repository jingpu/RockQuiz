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
		<a href="home.jsp?id=<%=guest%>">Home</a>
	</h2>
	<h1>Interesting Fields</h1>
	<%
		for (int i = 0; i < categories.length; i++) {
	%>
	<h2>
		<a href='search.jsp?s=g&q=<%=categories[i]%>'><%=categories[i]%></a>
	</h2>
	<%
		}
	%>
	<form action="Search" method="post">
		<input type="search" name="query" class="text"
			placeholder="Search quizzes"><br> <input type="submit"
			value="Search">
	</form>

</body>
</html>