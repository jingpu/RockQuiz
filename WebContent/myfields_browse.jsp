<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="user.*"%>
<%@ page import="quiz.*"%>
<%@ page import="util.Helper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="Mailbox_style.css" rel="stylesheet" type="text/css" />

<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (userId == null && UserManager.alreadyExist(guest)) {
		response.sendRedirect("myfields_browse.jsp?id=" + guest);
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
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1><%=userId%>'s Mailbox
				</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp?id=<%=guest%>" target="_top">Home</a>
					</h2>
				</div>
			</div>

			<dl id="browse">

	<dt>Interesting Fields</dt>
	<% if(categories != null){
		for (int i = 0; i < categories.length; i++) {
	%>
	<dd>
		<a href='myfields_search.jsp?s=d&q=<%=categories[i]%>'
			target='another'><%=categories[i]%></a>
	</dd>
	<%
		}}
	%>
	<dt>Other categories:</dt>
	<dd>
	<form action="myfields_search.jsp" method="post" target='another'>
		<input type="hidden" name="s" value="d"> <input type="search"
			name="q" class="text" placeholder="Search category"><input
			type="submit" value="Search">
	</form>
	</dd>

</body>
</html>
