<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="util.Helper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Recent Quizzes</title>
</head>
<body>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Recent Quizzes</h1>
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
		QuizManager man = new MyQuizManager();
		List<Quiz> recentQuizzes = man.getRecentCreateQuiz(20);
		System.out.println(recentQuizzes);
		int i = 0;
		for (Quiz quiz : recentQuizzes) {
			i++;
			int takenTimes = quiz.getTakenTimes();
			Timestamp time = quiz.getCreateTime();
			String category = quiz.getCategory();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	%>
	<li class='quizlist' style='text-align: left; margin-top: 0.5em'><%=Helper.displayQuiz(quiz, true)%><br>
		Category: <a href='search.jsp?s=g&q=<%=category%>'><%=category%></a>
		On <%=sdf.format(time)%> Taken counts: <%=takenTimes%></li>
	<%
		}
	%>
		</div>
	</div>
	</div>
	</div>
</body>
</html>