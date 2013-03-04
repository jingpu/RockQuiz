<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="quiz.*"%>
<%@ page import="quiz.MyQuiz.QuizEvent"%>

<!DOCTYPE html>
<html>
<%
	String userName = (String) session.getAttribute("guest");
	if (userName == null) {
		// TODO remove it and do error checking instead
		userName = "guest";
		session.setAttribute("userName", userName);
	}
	String quizName = request.getParameter("quizName");
	MyQuiz quiz = new MyQuiz(quizName);
%>
<head>
<meta charset="UTF-8">
<title>Quiz Summary - <%=quiz.getQuizName()%></title>
</head>
<body>
	<h1>
		Quiz Summary -
		<%=quiz.getQuizName()%></h1>

	<!-- The text description of the quiz. -->
	<h2>Quiz Description</h2>
	<p>
		<%=quiz.getQuizDescription()%>
	</p>

	<!--  The time created -->
	<h3>Date Created</h3>
	<p>
		<%=quiz.getCreateTime()%></p>

	<!-- The creator of the quiz(hot linked to the creator’s user page -->
	<h3>Quiz Creator</h3>
	<p><%=quiz.getCreatorId()%></p>

	<h3>Tags</h3>
	<p>
		<%
			for (String tag : quiz.getTags()) {
				out.print("#" + tag + ", \n");
			}
		%>
	</p>


	<!-- TODO A list of the user’s past performance on this specific quiz. -->

	<!-- A list of the highest performers of all time. -->
	<h3>High Scores</h3>
	<table border="1">
		<tr>
			<th>User Name</th>
			<th>Score</th>
			<th>Time Used</th>
			<th>Date Submitted</th>
		</tr>
		<%
			List<QuizEvent> highScores = quiz.highScoreEvents(5);
			for (QuizEvent e : highScores) {
		%>
		<tr>
			<td><%=e.getUserName()%></td>
			<td><%=e.getScore()%></td>
			<td><%=e.getTimeElapsed() / 1000.0%>s</td>
			<td><%=e.getSubmitTime()%></td>
		</tr>
		<%
			}
		%>
	</table>

	<!-- A list of top performers in the last day. List -->
	<h3>High Scores in the Last Day</h3>
	<table border="1">
		<tr>
			<th>User Name</th>
			<th>Score</th>
			<th>Time Used</th>
			<th>Date Submitted</th>
		</tr>
		<%
			List<QuizEvent> highScoresLastday = quiz.highScoreLastDayEvents(5);
			for (QuizEvent e : highScoresLastday) {
		%>
		<tr>
			<td><%=e.getUserName()%></td>
			<td><%=e.getScore()%></td>
			<td><%=e.getTimeElapsed() / 1000.0%>s</td>
			<td><%=e.getSubmitTime()%></td>
		</tr>
		<%
			}
		%>
	</table>


	<!-- A list showing the performance of recent test takers List -->
	<h3>Recent Taken Log</h3>
	<table border="1">
		<tr>
			<th>User Name</th>
			<th>Score</th>
			<th>Time Used</th>
			<th>Date Submitted</th>
		</tr>
		<%
			List<QuizEvent> recentEvents = quiz.recentTakenEvents(5);
			for (QuizEvent e : recentEvents) {
		%>
		<tr>
			<td><%=e.getUserName()%></td>
			<td><%=e.getScore()%></td>
			<td><%=e.getTimeElapsed() / 1000.0%>s</td>
			<td><%=e.getSubmitTime()%></td>
		</tr>
		<%
			}
		%>
	</table>

	<!-- TODO Summary statistics on how well all users have performed on the quiz-->


	<!-- A way to initiate taking the quiz. A way to
		start the quiz in practice mode, if available.-->

	<form action="<%=quiz.getQuizStartPage()%>" method="POST">
		<input type="hidden" name="quizName" value=<%=quiz.getQuizName()%>>
		<%
			String disabledAttr = "";
			if (!quiz.isCanPractice())
				disabledAttr = "disabled";
		%>
		<input type="checkbox" name="practiceMode" value="true"
			<%=disabledAttr%> > Start in practice mode<br> <input
			type="submit" value="Start Quiz">
	</form>

	<%
		// A way to start editing the quiz, if the user is the quizowner. 
		if (userName.equals(quiz.getCreatorId())) {
	%>
	<form action="<%=quiz.getQuizEditPage()%>" method="POST">
		<input type="hidden" name="quizName" value="<%=quiz.getQuizName()%>">
		<input type="submit" value="EditQuiz">
	</form>
	<%
		}
	%>
</body>
</html>