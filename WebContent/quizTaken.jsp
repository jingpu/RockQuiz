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
<%@ page import="user.Activity"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
%>
<title>Quizzes Taken - <%=userId%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Quizzes Taken</h1>
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
			response.sendRedirect("quizTaken.jsp?id=" + guest);
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
			List<Activity> taken = user.getQuizTaken(-1);
			if (taken.isEmpty()) {
				String prefix = guest.equals(userId) ? "I" : userId;
	%>
	<p><%=prefix%>
		did't take any quiz yet.
	</p>
	<%
		} else {
				for (Activity act : taken) {
					if (guest.equals(userId)) {
						out.println("<li class='activity' style='text-align: center'>" + act.toStringMe(false) + "</li>");
					} else {
						out.println("<li class='activity' style='text-align: center'>" + act.toString(false) + "</li>");
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
