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
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
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
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>

			<dl id="browse">
				<%--choose whether to search user--%>
				<dt>
					<a href=<%=searchQuiz%>>Quizzes Search</a> <u>Users Search</u>
				</dt>
				<dd class="searchform">
					<%--quizzes/users search box--%>
					<form action="UserSearch" method="post">
						<div>
							<input type="search" name="query" value="<%=query%>" size="35">
						</div>
						<div class="readmore">
							<input type="image" src="images/search.gif" />
						</div>
					</form>
				</dd>
			</dl>

			<div id="body">
				<%-- matched user results--%>
				<p>Related Users</p>
				<%
					if(query != ""){
							List<String> userResult = UserManager.getUserList(query, "2013", new Date().toString(), false);
								if (userResult.isEmpty()) {
									out.println("<p>There is no related user.</p>");
								} else {
				%>
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
