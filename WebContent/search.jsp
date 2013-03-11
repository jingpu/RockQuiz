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
	String sort = request.getParameter("s");
	List<Quiz> resultList = null;
	QuizManager man = new MyQuizManager();
	// query s - result set sorting methods:
	// c - order by creator;
	// d - order by creation date
	// t - order by taken times
	// null(default) - order by quiz relativity
	if (query != null && request.getParameter("s") == null)
		resultList = man.searchForQuiz(query, 3);
	else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("c"))
		resultList = man.searchForQuizCreator(query, 3);
	else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("d"))
		resultList = man.searchForQuiz(query, 1);
	else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("t"))
		resultList = man.searchForQuiz(query, 2);

	String pquery = query == null ? "" : query;
	String searchUser = "search_user.jsp?q=" + pquery;
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
				<%--exactly matched user result--%>
				<p>Related user</p>
				<%
					if (query != null && UserManager.alreadyExist(query)) {
						StringBuilder str = new StringBuilder();
						str.append("<p><a href='userpage.jsp?id=" + query + "'>"
								+ query + "</a></p>");
						out.println(str.toString());
					}
				%>

				<%--related quizzes search results--%>
				<p>Related quizzes</p>
				<ul>
					<%
						if (query != null && (resultList == null || resultList.isEmpty())) {
							out.println("<p>There is no related quiz.</p>");
						} else if (query != null) {
							for (Quiz quiz : resultList) {
								String quizUrl = quiz.getSummaryPage();
								String creator = quiz.getCreatorId();
								Timestamp time = quiz.getCreateTime();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					%>
					<li>Quiz <a href='<%=quizUrl%>'><%=quiz.getQuizName()%></a> Created
						by:<a href="userpage.jsp?id=<%=creator%>"><%=creator%></a> On <%=sdf.format(time)%><br>Description: <%=quiz.getQuizDescription()%></li>
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