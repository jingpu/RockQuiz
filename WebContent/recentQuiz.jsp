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
<title>Recent Quizzes</title>
</head>
<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<h1>Recent Quizzes</h1>
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
	<li style='list-style-type: decimal; margin-top: 1em;'><%=Helper.displayQuiz(quiz, true)%><br>
		Category: <a href='search.jsp?s=g&q=<%=category%>'><%=category%></a>
		On <%=sdf.format(time)%> Taken counts: <%=takenTimes%></li>
	<%
		}
	%>
</body>
</html>