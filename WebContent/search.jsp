<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="user.Account"%>
<%@ page import="user.UserManager"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="search_style.css" rel="stylesheet" type="text/css" />
<%
	String query = request.getParameter("q");
	String pquery = query == null ? "" : query;
	String searchUser = "search_user.jsp?q=" + pquery;
	String sort = request.getParameter("s");
	List<Quiz> resultList = null;
	QuizManager man = new MyQuizManager();
	// query s - result set sorting methods:
	// c - order by creator;
	// d - order by creation date
	// t - order by taken times
	// null(default) - order by quiz relativity
	String byRelavance = "<a href='search.jsp?q=" + pquery
			+ "'>Relavance</a>";
	String byCreateDate = "<a href='search.jsp?s=d&q=" + pquery
			+ "'>Create date</a>";
	String byTakenCount = "<a href='search.jsp?s=t&q=" + pquery
			+ "'>Taken count</a>";
	String byCreator = "<a href='search.jsp?s=c&q=" + pquery
			+ "'>Creator ID</a>";
	if (query != null && request.getParameter("s") == null) {
		byRelavance = "Relavence";
		resultList = man.searchForQuiz(query, 3);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("c")) {
		byCreator = "Creator ID";
		resultList = man.searchForQuizCreator(query, 3);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("d")) {
		byCreateDate = "Create date";
		resultList = man.searchForQuiz(query, 1);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("t")) {
		byTakenCount = "Taken count";
		resultList = man.searchForQuiz(query, 2);
	}
%>
<title>Search Results - <%=pquery%></title>
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
					Quizzes Search | <a href=<%=searchUser%>>Users Search</a>
				</dt>
				<dd class="searchform">
					<%--quizzes/users search box--%>
					<form action="Search" method="post">
						<div>
							<input type="search" name="query" size="40" value=<%=pquery%>>
						</div>
						<div class="readmore">
							<input type="image" src="images/search.gif" />
						</div>
					</form>
				</dd>
			</dl>

			<div id="body">
				<h4>
					Order by:
					<%=byRelavance%>
					|
					<%=byCreateDate%>
					|
					<%=byTakenCount%>
					|
					<%=byCreator%></h4>
				<%--exactly matched user result--%>
				<%
					if (query != null && UserManager.alreadyExist(query)) {
						out.println("<h5>Related user</h5>");
						StringBuilder str = new StringBuilder();
						str.append("<p><a href='userpage.jsp?id=" + query + "'>"
								+ query + "</a></p>");
						out.println(str.toString());
					}
				%>

				<%--related quizzes search results--%>
				<ul>
					<%
						if (query != null && (resultList == null || resultList.isEmpty())) {
							out.println("<h5>There is no related quiz.</h5>");
						} else if (query != null) {
							out.println("<h5>Related quizzes</h5>");
							for (Quiz quiz : resultList) {
								String quizUrl = quiz.getSummaryPage();
								String creator = quiz.getCreatorId();
								int takenTimes = quiz.getTakenTimes();
								Timestamp time = quiz.getCreateTime();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					%>
					<li>Quiz <a href='<%=quizUrl%>'><%=quiz.getQuizName()%></a>
						Created by:<a href="userpage.jsp?id=<%=creator%>"><%=creator%></a>
						On <%=sdf.format(time)%> Taken counts: <%=takenTimes%><br>Description: <%=quiz.getQuizDescription()%></li>
					<%
						}
						}
					%>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>