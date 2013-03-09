<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Message"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Announce"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="quiz.Quiz"%>
<%@ page import="quiz.QuizManager"%>
<%@ page import="quiz.MyQuizManager"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="homestyle.css" rel="stylesheet" type="text/css" />

<title>RockQuiz - Guest</title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
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
							<input type="text" name="query" class="text"
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

						<%
							QuizManager man = new MyQuizManager();
							List<Quiz> popQuizzes = man.getPopularQuiz(3);
							System.out.println(popQuizzes);
							int i = 0;
							for (Quiz quiz : popQuizzes) {
								i++;
								String quizUrl = quiz.getSummaryPage();
								String creator = quiz.getCreatorId();
						%>
						<p>
							<a href=<%=quizUrl%>><%=i%>. <%=quiz.getQuizName()%></a> (by:<a
								href="userpage.jsp?id=<%=creator%>"><%=creator%></a>)
						</p>
						<%
							}
						%>


						<p class="readmore">
							<a href="popQuiz.jsp"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Recent Quizzes</h3>

						<%
							List<Quiz> recentQuizzes = man.getRecentCreateQuiz(3);
							int j = 0;
							for (Quiz quiz : recentQuizzes) {
								j++;
								String quizUrl = quiz.getSummaryPage();
								String creator = quiz.getCreatorId();
						%>
						<p>
							<a href=<%=quizUrl%>><%=j%>. <%=quiz.getQuizName()%></a>(by:<a
								href="userpage.jsp?id=<%=creator%>"><%=creator%></a>)
						</p>
						<%
							}
						%>

						<p class="readmore">
							<a href="recentQuiz.jsp"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>

					<div class="clear br"></div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
