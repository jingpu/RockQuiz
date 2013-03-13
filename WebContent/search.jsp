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
<%@ page import="util.Helper"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String query = request.getParameter("q");
	String pquery = query == null ? "" : query;
%>
<script type="text/javascript" src="searchpage.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="search_style.css" rel="stylesheet" type="text/css" />
<%
	String searchUser = "search_user.jsp?q=" + pquery;
	String sort = request.getParameter("s");
	List<Quiz> resultList = null;
	QuizManager man = new MyQuizManager();
	// query s - result set sorting methods:
	// null(default) - order by quiz relativity
	// d - order by creation date
	// t - order by taken times
	// c - order by creator;
	// g - order by category;
	// combo: dc, dg, tc, tg
	String byRelavance = "<a href='search.jsp?q=" + pquery
			+ "'>Relavance</a>";
	String byCreateDate = "<a href='search.jsp?s=d&q=" + pquery
			+ "'>Create date</a>";
	String byTakenCount = "<a href='search.jsp?s=t&q=" + pquery
			+ "'>Taken count</a>";
	String byCreator = "<a href='search.jsp?s=c&q=" + pquery
			+ "'>Creator ID</a>";
	String byCategory = "<a href='search.jsp?s=g&q=" + pquery
			+ "'>Category</a>";
	if (query != null && request.getParameter("s") == null) {
		byRelavance = "Relavence";
		resultList = man.searchForQuiz(query, 3);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("c")) {
		byCreator = "Creator ID";
		byCreateDate = "<a href='search.jsp?s=dc&q=" + pquery
				+ "'>Create date</a>";
		byTakenCount = "<a href='search.jsp?s=tc&q=" + pquery
				+ "'>Taken count</a>";
		resultList = man.searchForQuizCreator(query, 3);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("g")) {
		byCategory = "Category";
		byCreateDate = "<a href='search.jsp?s=dg&q=" + pquery
				+ "'>Create date</a>";
		byTakenCount = "<a href='search.jsp?s=tg&q=" + pquery
				+ "'>Taken count</a>";
		resultList = man.searchForCategory(query, 3);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("d")) {
		byCreateDate = "Create date";
		resultList = man.searchForQuiz(query, 1);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("dc")) {
		byCreateDate = "Create date";
		byCreator = "Creator ID";
		byTakenCount = "<a href='search.jsp?s=tc&q=" + pquery
				+ "'>Taken count</a>";
		resultList = man.searchForQuizCreator(query, 1);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("dg")) {
		byCreateDate = "Create date";
		byCategory = "Category";
		byTakenCount = "<a href='search.jsp?s=tg&q=" + pquery
				+ "'>Taken count</a>";
		resultList = man.searchForCategory(query, 1);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("t")) {
		byTakenCount = "Taken count";
		resultList = man.searchForQuiz(query, 2);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("tc")) {
		byTakenCount = "Taken count";
		byCreator = "Creator ID";
		byCreateDate = "<a href='search.jsp?s=dc&q=" + pquery
				+ "'>Create date</a>";
		resultList = man.searchForQuizCreator(query, 2);
	} else if (query != null && request.getParameter("s") != null
			&& request.getParameter("s").equals("tg")) {
		byTakenCount = "Taken count";
		byCategory = "Category";
		byCreateDate = "<a href='search.jsp?s=dg&q=" + pquery
				+ "'>Create date</a>";
		resultList = man.searchForCategory(query, 2);
	} 
	String psort = sort == null? "": sort;
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
							<input type="search" name="query" value="<%=pquery%>" size="40">
							<input type="hidden" name="s" value="<%=sort%>">
						</div>
						<div class="readmore">
							<input type="image" src="images/search.gif" />
						</div>
					</form>
				</dd>
			</dl>

			<div id="body">
				<p>
					Order by:
					<%=byRelavance%>
					|
					<%=byCategory%>
					|
					<%=byCreator%>
					||
					<%=byCreateDate%>
					|
					<%=byTakenCount%></p>
				<%--exactly matched user result--%>
				<%
					if (query != null && UserManager.alreadyExist(query)) {
						out.println("<p>Related user</p>");
						StringBuilder str = new StringBuilder();
						str.append("<p>"+ Helper.displayUser(query) +"</p>");
						out.println(str.toString());
					}
				%>

				<%--related quizzes search results--%>


				<%
					if (query != null && (resultList == null || resultList.isEmpty())) {
						out.println("<p>There is no related quiz.</p>");
					} else if (query != null) {
						out.println("<p>Related quizzes</p>");
				%>
					<ul>
						<%
							for (Quiz quiz : resultList) {
									String quizUrl = quiz.getSummaryPage();
									String creator = quiz.getCreatorId();
									int takenTimes = quiz.getTakenTimes();
									Timestamp time = quiz.getCreateTime();
									String category =quiz.getCategory();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						%>
						<li><a href='<%=quizUrl%>'><div name='resultset' style='display:inline;'><%=quiz.getQuizName()%>
						</div></a> Category: <a href='search.jsp?s=g&q=<%=category%>'><div name='categoryset' style='display:inline;'><%=category%></div></a>
							<br>Created by: <a href="userpage.jsp?id=<%=creator%>"><div name='creatorset' style='display:inline;'><%=creator%></div></a>
							On <%=sdf.format(time)%> Taken counts: <%=takenTimes%><br>Description: <div name='resultset' style='display:inline;'>
							<%=quiz.getQuizDescription()%></div></li>
						<%
							}
						%>
					</ul>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	highlight("<%=pquery%>","<%=psort%>");
</script>
</html>