<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Activity"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="util.Helper"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
%>
<title>Achievements - <%=userId%></title>
</head>
<body>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Achievements</h1>
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
		if (guest == null || guest.equals("guest")) {
			response.sendRedirect("index.html");
			return;
		}
		String id = request.getParameter("id");
		if (id == null) {
			response.sendRedirect("achieves.jsp?id=" + guest);
			return;
		} else if (!UserManager.alreadyExist(id) || id.equals("guest")) {
			response.sendRedirect("userinvalid.jsp?id=" + id);
			return;
		}
		Account user = new Account(userId);

		List<String> friends = user.getFriendsList();
		boolean forbid = userId.equals(guest) ? false : (user.getInfo(
				"privacy").equals("1") ? (friends.contains(guest) ? false
				: true) : false);
		if (!forbid) {
	%>
	<%
		List<Activity> achieves = user.getAchievements(-1);
			if (achieves.isEmpty()) {
				String prefix = guest.equals(userId) ? "You" : userId;
	%>
	<p><%=prefix%>
		don't have any achievements now.
	</p>
	<%
		} else {
				for (Activity act : achieves) {
					if (guest.equals(userId)) {
						out.println("<p>" + act.toStringMe(false) + "</p>");
					} else {
						out.println("<p>" + act.toString(false) + "</p>");
					}
				}
			}
		} else {
	%>
	<p style='font-family: serif; color: black;'>
		Sorry.
		<%=id%>
		set privacy. Only friends can see this page.
	</p>
	<%
		}
	%>
		</div>
	</div>
	</div>
	</div>
</body>
</html>