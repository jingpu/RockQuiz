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
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Popular Quizzes</title>
</head>

<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
			<h1>Popular Quizzes</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	
	<ul>
	<%
		QuizManager man = new MyQuizManager();
		List<Quiz> popQuizzes = man.getPopularQuiz(20);
		System.out.println(popQuizzes);
		int i = 0;
		for (Quiz quiz : popQuizzes) {
			i++;
			int takenTimes = quiz.getTakenTimes();
			Timestamp time = quiz.getCreateTime();
			String category =quiz.getCategory();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	%>
	<li style='list-style-type: decimal; margin-top: 1em;'>
		<%=Helper.displayQuiz(quiz, true)%><br>
		Category: <a href='search.jsp?s=g&q=<%=category%>'><%=category%></a>
		On <%=sdf.format(time)%> Taken counts: <%=takenTimes%>
	</li>
	<%
		}
	%>
	</ul>
	</div>
	</div>
	</div>
	</div>
</body>
</html>