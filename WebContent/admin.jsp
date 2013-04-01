<!--
  The MIT License (MIT)
  Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to 
  deal in the Software without restriction, including without limitation the
  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
  sell copies of the Software, and to permit persons to whom the Software is 
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in 
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
  THE SOFTWARE.
-->
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
<%@ page import="util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<%
	String userId = request.getParameter("id");

	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")
			|| !UserManager.alreadyExist(guest)) {
		response.sendRedirect("index.html");
		return;
	} else if (!UserManager.getAccountInfo(guest, "status").equals("s")) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	} else if (userId == null || !guest.equals(userId)) {
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
					<h5>
						Deleting Account Failed.
						<%=del%>
						Doesn't Exist.
					</h5>
					<%
						}
					%>
					<%
						String apt = request.getParameter("adm");
						if (apt != null) {
					%>
					<h5>
						Appointing Administrator Failed.
						<%=apt%>
						Doesn't Exist.
					</h5>
					<%
						}
					%>
					<%
						String ann = request.getParameter("ann");
						if (ann != null) {
					%>
					<h5>New Announcement is Posted.</h5>
					<%
						}
					%>
					<%
						String delq = request.getParameter("delq");
						if (delq != null) {
					%>
					<h5>
						Quiz Operation Failed.
						<%=delq%>
						Doesn't Exist.
					</h5>
					<%
						}
					%>
				</div>
				<div class="inner">
					<div class="leftbox">
						<h3>Write Announcement</h3>
						<form action="WriteAnnounce" method="post" id="announce"
							onsubmit="return checkAnn();"></form>
						<div style='align: center;'>
							<textarea rows="10" cols="40" name="content" form="announce"
								placeholder="Write new announcement"></textarea>
						</div>
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
								</select>
							<div class='button'>
								<input type="submit" value="Change Authority">
							</div>
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
								</select>
							<div class='button'>
								<input type="submit">
							</div>
							</p>
						</form>
					</div>

					<div class="rightbox">
						<h3>RockQuiz Statistics</h3>
						<h4>Users INFO:</h4>
						<%
							String fromTime = request.getParameter("fromTime");
							String toTime = request.getParameter("toTime");
							fromTime = fromTime == null ? "" : fromTime;
							toTime = toTime == null ? "" : toTime;
							List<String> userList;
							if (fromTime != "" && toTime != "") {
								userList = UserManager.getUserList("", fromTime, toTime, true);
							} else {
								userList = UserManager.getUserList("", "2012",
										new Date().toString(), true);
							}
						%>

						<form action="admin.jsp?id=<%=guest%>" method="post" onsubmit='return checkTime();'>
							<p>
								From: <input type="text" name="fromTime" id="fromTime" value="<%=fromTime%>"
									size="10"> To: <input type="text" name="toTime" id="toTime"
									value="<%=toTime%>" size="10"><input type="submit" style='margin-left: 10px;'>
							</p>
							<p>
								(format: YYYY-MM-DD) 
							</p>
						</form>
						<p>
							Number:<%=userList.size()%></p>
						<p>Users List:</p>
						<div id='infolist'>
							<ul>
								<%
									for (String id : userList) {
										Account user = new Account(id);
								%>
								<li><%=Helper.displayUser(id)%> registers on <%=user.getInfo("registrationTime")%></li>
								<%
									}
								%>
							</ul>
						</div>
						<h4>Quizzes INFO:</h4>
						<%
							QuizManager man = new MyQuizManager();
							List<Quiz> resultList = man.searchForQuizCreator("", 2);
						%>
						<p>
							Number:
							<%=resultList.size()%></p>
						<p>Quizzes List:</p>
						<div id='infolist'>
							<ul>
								<%
									int count = 0;
									for (Quiz quiz : resultList) {
										if (quiz.getTakenTimes() > 0)
											count++;
								%>
								<li><%=Helper.displayQuiz(quiz, false)%>  taken counts: <%=quiz.getTakenTimes()%></li>
								<%
									}
								%>
							</ul>
						</div>
						<p>
							Number of Quizzes Taken:
							<%=count%></p>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
