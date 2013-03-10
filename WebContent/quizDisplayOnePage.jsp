<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="quiz.*"%>
<!DOCTYPE html>
<html>
<%
	String quizName = request.getParameter("quizName");
	MyQuiz quiz = new MyQuiz(quizName);
	// return to summary page if this quiz is not for one page display
	if (!quiz.isOnePage()) {
		response.sendRedirect(quiz.getSummaryPage());
		return;
	}
	//get the question list
	List<QuestionBase> questionList = quiz.getQuestionList();
	if(quiz.isRandom())
		Collections.shuffle(questionList);
	// set start time
	session.setAttribute("quizStartTime", new Date().getTime());
%>
<head>
<meta charset="UTF-8">
<title>Quiz - <%=quiz.getQuizName()%></title>
</head>
<body>
	<h1>
		Quiz -
		<%=quiz.getQuizName()%></h1>
	<form action="QuizResultSinglePageServlet" method="post">
		<%
			for (int i = 0; i < questionList.size(); i++) {
		%>
		<h2>
			Question
			<%=i + 1%></h2>
		<%
				QuestionBase question = questionList.get(i);
				out.print(question.printReadHtmlForSingle());
			}
		%>
		<input type="hidden" name="quizName" value="<%= quiz.getQuizName() %>">
		<input type="submit" value="Submit">
	</form>
</body>
</html>