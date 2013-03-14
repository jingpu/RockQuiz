<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Announce"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Announcement Page</title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Announcement</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			
			<div id="body">
				<div class="inner">
	
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
	</div>
	</div>
	</center>
	</div>
	</div>
</body>
</html>