<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="java.text.SimpleDateFormat"%>
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
	} else if (!guest.equals(userId)
			|| !UserManager.getAccountInfo(userId, "status")
					.equals("s")) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
	Administrator admin = new Administrator(userId);
%>
<title>Admin - <%=userId%></title>
</head>
<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<h1>Write Announcement</h1>
	<form action="WriteAnnounce" method="post" id="announce"></form>
	<p>
		<textarea rows="6" cols="50" name="content" form="announce"
			placeholder="Write new announcement"></textarea>
	</p>
	<input type="submit" value="Post" form="announce">
	<h1>Account Management</h1>
	<form action="DeleteAccount" method="post">
		<p>
			<input type="text" name="user" placeholder="User name"> <input
				type="submit" value="Delete Account">
		</p>
	</form>
	<form action="AppointAdmin" method="post">
		<p>
			<input type="text" name="user" placeholder="User name"> <input
				type="submit" value="Appoint as Admin">
		</p>
	</form>
	<h1>Quiz Management</h1>
	<form action="DeleteQuiz" method="post">
		<p>
			<input type="text" name="user" placeholder="Quiz name"> <input
				type="submit" value="Delete Quiz">
		</p>
	</form>
	<h1>RockQuiz Statistics</h1>
</body>
</html>