<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
	String query = request.getParameter("q");
	query = query == null? "" : query;
	String searchUser = "search_user.jsp?q="+ query;
%>
<title>Search Results - <%=query%></title>
</head>
<body>
	<%--return to homepage --%>
	<p><a href="home.jsp">Home</a></p>
	
	<%--choose whether to search user--%>
	<p>
		Quizzes Search
		<a href=<%=searchUser%>>Users Search</a>
	</p>
	<%--quizzes/users search box--%>
	<form action="Search" method="post">
		<p>
			<input type="text" name="query" size="50" value=<%=query%>> 
			<input type="submit" value="Click">
		</p>
	</form>

	<%--exactly matched user result--%>
	<%
		if (UserManager.alreadyExist(query)) {
			StringBuilder str = new StringBuilder();
			str.append("<p><a href=\"userpage.jsp?id=" + query + "\">"
					+ query + "</a></p>");
			out.println(str.toString());
		}
	%>

	<%--related quizzes search results--%>
	<p>Related quizzes</p>
	<%
		
	%>
</body>
</html>