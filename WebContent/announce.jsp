<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Announce"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Announcement Page</title>
</head>
<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<h1>Announcement</h1>
	<%
			String guest = (String) session.getAttribute("guest");
			if (guest == null || guest.equals("guest")) {
				response.sendRedirect("index.html");
				return;
			}
			List<Announce> annList = UserManager.getAllAnnounce();
			if (annList == null)
				out.println("<p>There is No Announcement Now.</p>");
			else {
				for (Announce ann : annList) {
		%>
	<p><%=ann.getContent()%></p>
	<p>
		-
		<%=ann.getAdmin()%>
		<%=ann.getTime()%></p>
	<%
			}
		%>

	<%
			}
		%>

</body>
</html>