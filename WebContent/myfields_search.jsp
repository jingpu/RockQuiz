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
<%@ page import="quiz.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="util.Helper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="searchpage.js"></script>
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="myfields_style.css" rel="stylesheet" type="text/css" />
<%
	String query = request.getParameter("q");
	String pquery = query == null ? "" : query;
	String sort = request.getParameter("s");
	String byCreateDate = "<a href='myfields_search.jsp?s=d&q="
			+ pquery + "'>Recently</a>";
	String byTakenCount = "<a href='myfields_search.jsp?s=t&q="
			+ pquery + "'>Popular</a>";
	QuizManager man = new MyQuizManager();
	List<Quiz> resultList = null;
	if (sort.equals("d")) {
		byCreateDate = "Recently";
		resultList = man.searchForCategory(query, 1);
	} else if (sort.equals("t")) {
		byTakenCount = "Popular";
		resultList = man.searchForCategory(query, 2);
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="myfield_style.css" rel="stylesheet" type="text/css" />
<title>Search Category Results</title>
</head>
<body>
	<div id="body">
	<br>
				<h3>
					<%=byCreateDate%> | <%=byTakenCount%>
				</h3>

	<h4><%=pquery%></h4>
	<%
		if (query != null && (resultList == null || resultList.isEmpty())) {
			out.println("<p>There is no related quiz.</p>");
		} else if (query != null) {
	%>
	<ul>
		<%
			for (Quiz quiz : resultList) {
					String quizUrl = quiz.getSummaryPage();
					String creator = quiz.getCreatorId();
					int takenTimes = quiz.getTakenTimes();
					Timestamp time = quiz.getCreateTime();
					String category = quiz.getCategory();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		%>
		<li><a href='<%=quizUrl%>'><div name='resultset'
					style='display: inline;'><%=quiz.getQuizName()%>
				</div></a> Category: <a href='search.jsp?s=g&q=<%=category%>'><div
					name='categoryset' style='display: inline;'><%=category%></div></a> <br>Created
			by: <a href="userpage.jsp?id=<%=creator%>"><div name='creatorset'
					style='display: inline;'><%=creator%></div></a> On <%=sdf.format(time)%>
			Taken counts: <%=takenTimes%><br>Description:
			<div name='resultset' style='display: inline;'>
				<%=quiz.getQuizDescription()%></div></li>
		<%
			}
		%>
	</ul>

	<%
		}
	%>
	</div>

</body>
<script type="text/javascript">
	highlight("<%=pquery%>","g");
</script>
</html>
