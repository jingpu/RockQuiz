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
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<%
	String userId = request.getParameter("id");

	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest") || !UserManager.alreadyExist(guest)) {
		response.sendRedirect("index.html");
		return;
	} else if (!UserManager.getAccountInfo(guest, "status")
					.equals("s")) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	} else if (userId == null || !guest.equals(userId)){
		response.sendRedirect("admin.jsp?id=" + guest);
		return;
	}
	Administrator admin = new Administrator(userId);
%>

<script language="javascript" type="text/javascript" src="adminpage.js"></script>

<title>Admin - <%=userId%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>
					Admin:
					<%=userId%></h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>


			<div id="body">
				<div id='errorNotice'>
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
				</div>
				<div class="inner">
					<div class="leftbox">
						<h3>Write Announcement</h3>
						<form action="WriteAnnounce" method="post" id="announce"
							onsubmit="return checkAnn();"></form>
						<p>
							<textarea rows="6" cols="40" name="content" form="announce"
								placeholder="Write new announcement"></textarea>
						</p>
						<input type="submit" value="Post" form="announce"
							margin-bottom="35"> <br> <br> <br>
						<h3>Account Management</h3>
						<form action="DeleteAccount" method="post"
							onsubmit="return checkDeletion();">
							<p>
								Delete <input type="text" name="id" placeholder="User name">
								<input type="submit" value="Submit">
							</p>
						</form>
						<form action="ChangeStatus" method="post"
							onsubmit="return checkAppointment();">
							<p>
								Set <input type="text" name="id" placeholder="User name">
								as <select name="status">
									<option value="u">Normal User</option>
									<option value="s">Administrator</option>
								</select> <input type="submit" value="Change Authority">
							</p>
						</form>
						<br> <br>
						<h3>Quiz Management</h3>
						<form action="DeleteQuiz" method="post"
							onsubmit="return checkDeleteQuiz();">
							<p>
								<input type="text" name="quiz" placeholder="Quiz name">
								<select name="operation" id="quizOp">
									<option value="2">Clear taken history</option>
									<option value="1">Delete quiz</option>
								</select> <input type="submit">
							</p>
						</form>
					</div>

					<div class="rightbox">
						<h3>RockQuiz Statistics</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
