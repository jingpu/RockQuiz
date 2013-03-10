<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Activity"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
	String userId = request.getParameter("id");
%>
<title>Quizzes Taken - <%=userId%></title>
</head>
<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<h1>Quizzes Taken</h1>
	<%
		String guest = (String) session.getAttribute("guest");
		if (guest == null || guest.equals("guest")) {
			response.sendRedirect("index.html");
			return;
		}
		if (userId == null || !UserManager.alreadyExist(userId)) {
			response.sendRedirect("home.jsp");
			return;
		}
		Account user = new Account(userId);
		List<Activity> taken = user.getQuizTaken();
		if (taken.isEmpty()) {
			String prefix = guest.equals(userId)? "You" : userId;
	%>
	<p><%=prefix%>
		did't take any quiz yet.
	</p>
	<%
		} else {
			for (Activity act : taken) {
				if (guest.equals(userId)) {
					out.println("<p>" + act.toStringMe() + "</p>");
				} else {
					out.println("<p>" + act.toString() + "</p>");
				}
			}
		}
	%>
</body>
</html>