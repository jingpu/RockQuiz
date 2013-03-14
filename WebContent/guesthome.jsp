<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Announce"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="util.Helper"%>
<%@ page import="java.sql.Timestamp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="homestyle.css" rel="stylesheet" type="text/css" />

<title>RockQuiz - Guest</title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Welcome to RockQuiz</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="index.html">Login</a> | <a href="createAccount.html">Register</a>
					</h2>
				</div>
			</div>

			<dl id="browse">

				<dt>Announcements</dt>
				<dd>
					<%
						Announce ann = UserManager.getLatestAnnounce();
						if (ann != null) {
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							Date time = sdf2.parse(ann.getTime());
					%>
					<p><%=ann.getContent()%></p>
					<p>
						-
						<%=ann.getAdmin()%>
						<%=sdf2.format(time)%></p>
					<%
						} else {
					%>
					<p>There is No Announcement Now.</p>
					<%
						}
					%>
				</dd>
				<dd class="readmore">
					<a href="announce.jsp"><b>MORE</b></a>
				</dd>

				<dt>Search Quizzers Or Users</dt>
				<dd class="searchform">
					<form action="Search" method="post">
						<div>
							<input type="search" name="query" class="text"
								placeholder="Search quizzes OR users here" />
						</div>
						<div class="readmore">
							<input type="image" src="images/search.gif" />
						</div>
					</form>
				</dd>
			</dl>

			<div id="body">
				<div class="inner">
					<div class="leftbox">

						<h3>Popular Quizzes</h3>
						<ul>
							<%
								QuizManager man = new MyQuizManager();
								List<Quiz> popQuizzes = man.getPopularQuiz(10);
								//System.out.println(popQuizzes);
								for (Quiz quiz : popQuizzes) {
									String display = Helper.displayQuiz(quiz, false);
									int takenTimes = quiz.getTakenTimes();
							%>
							<li class="quizlist"><%=display%> Taken counts: <%=takenTimes%></li>
							<%
								}
							%>
						</ul>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Recent Quizzes</h3>
						<ul>
							<%
								List<Quiz> recentQuizzes = man.getRecentCreateQuiz(10);
								//System.out.println(popQuizzes);
								for (Quiz quiz : recentQuizzes) {
									String display = Helper.displayQuiz(quiz, false);
									Timestamp time = quiz.getCreateTime();
									String category = quiz.getCategory();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							%>
							<li class="quizlist"><%=display%> Created On: <%=sdf.format(time)%></li>
							<%
								}
							%>
						</ul>
						<div class="clear"></div>
					</div>

					<div class="clear br"></div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
