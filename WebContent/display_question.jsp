<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.sql.*, quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz: quizName   Question#: i/j</title>
</head>
<body>
<h1>Quiz: quizName   Question#: i/j</h1>

<p>
<%
	MyQuiz quiz = (MyQuiz)session.getAttribute("quiz");
	int index; //index for question
	QuestionBase question = quiz.getQuestion(index); 
	String htmlStr = question.printHTML();
	String type = question.getQuestionType();
	String prompt = "Your Answer:";  //TODO: chang based on questionType question.getPrompt();
	String ctrlType = question.getCtrlType(); //TODO: change based on questionType
	String ctrlType2 = "hidden"; //TODO
	if (ctrlType.equals("radio")) ctrlType2 = "radio"; 
	String btnName = "Next";  //TODO: change based on index
	List<String> radioIds = question.getRadioIds(); 
%>
</p>
<p><%= htmlStr %></p>
<form action="QuestionServlet" method="get">
	<ul>
	<li><p><%= prompt %> <input type=<%= ctrlType %> name="userAnswer" <%= "id = \"" + radioIds.get(0) + "\"" %> /></p></li>
	<li><p><%= prompt %> <input type=<%= ctrlType2 %> name="userAnswer" <%= "id = \"" + radioIds.get(1) + "\""  %>/></p></li>
	<li><p><%= prompt %> <input type=<%= ctrlType2 %> name="userAnswer" <%= "id = \"" + radioIds.get(2) + "\""  %> /></p></li>
	<li><p><%= prompt %> <input type=<%= ctrlType2 %> name="userAnswer" <%= "id = \"" + radioIds.get(3) + "\""  %> /></p></li>
	</ul>
	<p><input type="submit" value = <%= btnName %>/></p>
</form>

</body>
</html>