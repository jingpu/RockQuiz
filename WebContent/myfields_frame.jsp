<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="user.*"%>
<%@ page import="quiz.*"%>
<%@ page import="util.Helper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (userId == null && UserManager.alreadyExist(guest)) {
		response.sendRedirect("myfields_frame.jsp?id=" + guest);
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
	Account user = new Account(userId);
	String[] categories = Helper.string2Array(user.getInfo("category"));
	String first = "";
	if(categories.length > 0) 
		first = categories[0];
%>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Following fields - <%=userId%></title>
</head>

<Frameset cols="25%,*">
	<frame src="myfields_browse.jsp?id=<%=userId%>" marginheight=60 framebolder="0">
	<frame src="myfields_search.jsp?s=d&q=<%=first%>" name=another
		marginheight=60 framebolder="0">
</Frameset>

</html>