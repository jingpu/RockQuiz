<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="util.Helper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="searchpage.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="search_style.css" rel="stylesheet" type="text/css" />
<%
	String query = request.getParameter("q");
	query = query == null ? "" : query;
	String searchQuiz = "search.jsp?q=" + query;
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
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>

			<dl id="browse">
				<%--choose whether to search user--%>
				<dt>
					<a href=<%=searchQuiz%>>Quizzes Search</a> | Users Search
				</dt>
				<dd class="searchform">
					<%--quizzes/users search box--%>
					<form action="UserSearch" method="post">
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
				<%-- matched user results--%>
				<%
					if(query != ""){
							List<String> userResult = UserManager.getUserList(query);
								if (userResult.isEmpty()) {
									out.println("<p>There is no related user.</p>");
								} else {
				%>
				<p>Related users</p>
				<ul>
					<%
						for (String str : userResult) {
					%>
					<li><a href="userpage.jsp?id=<%=str%>"><div
								name='creatorset' style='display: inline;'><%=str%></div></a></li>
					<%
						}
					%>

				</ul>
				<%
					}}
				%>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	highlight("<%=query%>
	", "c");
</script>
</html>