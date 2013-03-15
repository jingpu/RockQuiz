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
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<link href="CSS/quiz_display.css" rel="stylesheet" type="text/css" />
<title>Quiz - <%=quiz.getQuizName()%></title>
</head>
<body>
<div id="wrapper">
		<div id="inner">
			<div id="header">
			<h1>
		Quiz -
		<%=quiz.getQuizName()%></h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	
	<form action="QuizResultSinglePageServlet" method="post">
		<%
			for (int i = 0; i < questionList.size(); i++) {
		%>
		<div   class="question">
		<h2>
			Question
			<%=i + 1%></h2>
		<%
				QuestionBase question = questionList.get(i);
				out.print(question.printReadHtmlForSingle());
		%>
		</div>
		<%		} 
		%>
		<input type="hidden" name="quizName" value="<%= quiz.getQuizName() %>">
		<div id = "submit_btn">
		<input type="submit" value="Submit">
		</div>
	</form>
	</div>
	</div>
	</div>
	</div>
	
</body>
</html>