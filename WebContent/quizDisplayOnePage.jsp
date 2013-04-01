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
