<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Message"%>
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
<title>Popular Quizzes</title>
</head>

<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<%
		QuizManager man = new MyQuizManager();
		List<String> popQuizzes = man.getPopularQuiz();
		System.out.println(popQuizzes);
		int i = 0;
		for (String name : popQuizzes) {
			i++;
			Quiz quiz = man.getQuiz(name);
			String quizUrl = quiz.getSummaryPage();
			String creator = quiz.getCreatorId();
	%>
	<p>
		<a href=<%=quizUrl%>><%=i%>. <%=name%></a> (by:<a
			href="userpage.jsp?id=<%=creator%>"><%=creator%></a>)
	</p>
	<%
		}
	%>
</body>
</html>