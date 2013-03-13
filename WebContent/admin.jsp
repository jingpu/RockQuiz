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

<script language="javascript" type="text/javascript">
	function confirmDeletion() {
		var name = document.forms[1].id.value;
		var r = confirm("Are you sure to delete " + name + "?");
		if (r) {
			return true;
		}
		return false;
	}

	function confirmAppointment() {
		var name = document.forms[2].id.value;
		var r = confirm("Are you sure to change " + name + "'s authority?");
		if (r) {
			return true;
		}
		return false;
	}

	function confirmDeleteQuiz() {
		var name = document.forms[3].quiz.value;
		var e = document.getElementById("quizOp");
		var selected = e.options[e.selectedIndex].value;
		if (selected == '1') {
			return confirm("Are you sure to delete quiz " + name + "?");
		}
		if (selected == '2') {
			return confirm("Are you sure to clear quiz " + name
					+ "'s history'?");
		}
	}
</script>

<title>Admin - <%=userId%></title>
</head>
<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<%
		String del = request.getParameter("del");
		if (del != null) {
	%>
	<p>
		Deleting Account Failed.
		<%=del%>
		Doesn't Exist.
	</p>
	<%
		}
	%>
	<%
		String apt = request.getParameter("adm");
		if (apt != null) {
	%>
	<p>
		Appointing Administrator Failed.
		<%=apt%>
		Doesn't Exist.
	</p>
	<%
		}
	%>
	<%
		String ann = request.getParameter("ann");
		if (ann != null) {
	%>
	<p>New Announcement is Posted.</p>
	<%
		}
	%>
	<%
		String delq = request.getParameter("delq");
		if (delq != null) {
	%>
	<p>
		Quiz Operation Failed.
		<%=delq%>
		Doesn't Exist.
	</p>
	<%
		}
	%>
	<h1>Write Announcement</h1>
	<form action="WriteAnnounce" method="post" id="announce"></form>
	<p>
		<textarea rows="6" cols="50" name="content" form="announce"
			placeholder="Write new announcement"></textarea>
	</p>
	<input type="submit" value="Post" form="announce">
	<h1>Account Management</h1>
	<form action="DeleteAccount" method="post"
		onsubmit="return confirmDeletion()">
		<p>
			<input type="text" name="id" placeholder="User name"> <input
				type="submit" value="Delete Account">
		</p>
	</form>
	<form action="ChangeStatus" method="post"
		onsubmit="return confirmAppointment()">
		<p>
			Set <input type="text" name="id" placeholder="User name"> as
			<select name="status">
				<option value="u">Normal User</option>
				<option value="s">Administrator</option>
			</select> <input type="submit" value="Change Authority">
		</p>
	</form>
	<h1>Quiz Management</h1>
	<form action="DeleteQuiz" method="post"
		onsubmit="return confirmDeleteQuiz()">
		<p>
			<input type="text" name="quiz" placeholder="Quiz name"> <select
				name="operation" id="quizOp">
				<option value="2">Clear taken history</option>
				<option value="1">Delete quiz</option>
			</select> <input type="submit">
		</p>
	</form>
	<h1>RockQuiz Statistics</h1>
</body>
</html>