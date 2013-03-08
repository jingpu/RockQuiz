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
<link href="search_style.css" rel="stylesheet" type="text/css" />
<%
	String query = request.getParameter("q");
	query = query == null? "" : query;
	String searchUser = "search_user.jsp?q="+ query;
%>
<title>Search Results - <%=query%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Search Page</h1>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> |  <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			
			<dl id="browse">
				<%--choose whether to search user--%>
				<dt>Quizzes Search | <a href=<%=searchUser%>>Users Search</a></dt>
				<dd class="searchform">
					<%--quizzes/users search box--%>
					<form action="Search" method="post">
						<div>
							<input type="text" name="query" size="40" value=<%=query%>> 
						</div>
						<div class="readmore">
							<input type="image" src="images/search.gif" />
						</div>
					</form>				
				</dd>
			</dl>
			
			<div id="body">											
				<%--exactly matched user result--%>
				<p>Related user</p>
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
			</div>
		</div>
	</div>

</body>
</html>