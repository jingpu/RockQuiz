<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="user.Account"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("user");
	if(guest.equals("guest")) {
		response.sendRedirect("index.html");   
	} else if(!guest.equals(userId)){
		response.sendRedirect("userpage.jsp?id=" + userId);   
	}
	Account user = new Account(userId);
	// generate achievements history
	List<String> achieves = user.getAchievements();
	StringBuilder achievesString = new StringBuilder();
	for(String str : achieves){
		achievesString.append(str).append('\n');
	}
	// generate quizzes taken history
	List<String> taken = user.getQuizTaken();
	/*
	for(int i = 0; i < 5; i++){
		out.println("<li><p>"+ taken.get(i) +"</p></li>");
	}
	*/
	// generate quizzes created history
	List<String> created = user.getQuizCreated();
	/*
	for(int i = 0; i < 5; i++){
		out.println("<li><p>"+ created.get(i) +"</p></li>");
	}
	*/
	// mail messgae
	
%>
<title>RockQuiz - <%=userId%></title>
</head>
<body>
	<%--  --%>
</body>
</html>