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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="quiz.*"%>
<%@ page import="user.*"%>
<%@ page import="util.*"%>
<%@ page import="quiz.MyQuiz.QuizEvent"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html>
<%
	String userName = (String) session.getAttribute("guest");
	String quizName = request.getParameter("quizName");
	MyQuiz quiz = new MyQuiz(quizName);
	
	// copied from home.jsp
	String userId = userName;
	String guest = userName;
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}

	Account user = new Account(userId);
	String status = user.getInfo("status");
	// generate achievements history
	List<Activity> achieves = user.getAchievements(3);
	// generate quizzes taken history
	List<Activity> taken = user.getQuizTaken(4);
	// generate quizzes created history
	List<Activity> created = user.getQuizCreated(10);
	// friends' activities
	List<Activity> friendsAct = user.getFriendsRecentActivity(10);

	// mail messages
	List<String> unread = user.getUnreadMessage();
	int unreadCount = unread.size();

	String mailBoxUrl = "Mailbox_frame.jsp?id=" + userId;
	String userpageUrl = "userpage.jsp?id=" + userId;
%>
<head>
<script type="text/javascript" src="challenge-msg.js"></script>
<meta charset="UTF-8">
<title>Quiz Summary - <%=quiz.getQuizName()%></title>
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="CSS/table.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#body div.alignleft, #body  h4.alignleft {
	text-align: left
}

#body h4.alignright {
	text-align: right
}

#body {
	font-size: 14px;
}

#body h2 {
	font-size: 20px;
	color: #227293;
}
#quizInfo{
	padding: 0px 15px 0px;
}
</style>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Quiz Summary</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp?id=<%=guest%>">Home</a> | <a
							href=<%=mailBoxUrl%>>Mailbox </a> | <a href="Logout">Log Out</a>
					</h2>
				</div>
			</div>
			<dl id="browse">
				<dt>Announcements</dt>
				<%
					Announce ann = user.getLatestAnnounce();
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
				<dd class="readmore">
					<a href="announce.jsp"><b>MORE</b></a>
				</dd>
				<dt>
					<a href='<%=mailBoxUrl%>'>New Message(<%=unreadCount%>)
					</a>
				</dt>
				<%
					if (unread.isEmpty()) {
						out.println("<dd>No New Message</dd>");
					} else {
						int i = 0;
						for (String msgCode : unread) {
							if (i == 3)
								break;
							Message msg = user.getMessage("inbox", msgCode);
							String str = msg.getTitle();
							str = str.length() > 30 ? str.substring(0, 29) : str;
							String description = msg.from + ": \"" + str + "\"";
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss.S");
							Date time = sdf.parse(msg.getTime());
							Date now = new Date();
							String timeDscr = TimeTrsf.dscr(time, now);
							out.println("<dd><a href='Mailbox_frame.jsp?id=" + userId
									+ "&box=inbox&msg=" + msgCode
									+ "' target='_blank'>" + description + " "
									+ timeDscr + "</a></dd>");
							i++;
						}
					}
				%>

				<dt>Quick Link</dt>
				
				<dd>
					<a href="quiz_create.jsp" >Create Quiz</a>
				</dd>
				<%
					if (user.getInfo("status").equals("s")) {
				%>
				<dd>
					<a href="admin.jsp?id=<%=userId%>">Administration</a>
				</dd>
				<%
					}
				%>
				<dd>
					<a href="friendpage.jsp?id=<%=userId%>">Friends</a>
				</dd>
				<dd>
					<a href="profile.jsp?id=<%=userId%>" target='_blank'>Edit
						Profile</a>
				</dd>
				<dd>
					<a href="myfields_frame.jsp?id=<%=userId%>">Interesting Fields</a>
				</dd>
				<dd>
					<a href="About_RockQuiz.html">About Rock Quiz</a>
				</dd>
				<dt>Search Quizzes Or Users</dt>
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
				<div class="clear"></div>
				<!-- The text description of the quiz. -->
				<h2><%=quiz.getQuizName()%></h2>
				<div id="quizInfo">
				<h4 class="alignright">
					<%
						SimpleDateFormat simpleDateFormat =
					        new SimpleDateFormat("MMM d, yyyy");
					%>
					created by
					<%=Helper.displayUser(quiz.getCreatorId())%>
					on
					<%=simpleDateFormat.format(quiz.getCreateTime())%>
				</h4>
				<div class="alignleft">
				<h4 class="alignleft" >Quiz Description</h4>
					<p>
						<%=quiz.getQuizDescription()%>
					</p>

					<p>
						<b>Category: </b> <a
							href='search.jsp?s=g&q=<%=quiz.getCategory()%>'><%=quiz.getCategory()%></a>
					</p>

					<p>
						<b>Tags: </b>
						<%=Helper.displayTags(quiz.getTags(), false)%>
					</p>
				</div>

				<div class="clear"></div>

				<div style="float: left">
					<%--challenge --%>
					<input name='' type='button' value='Challenge my friends!'
						onclick='AddElement()' />

					<%--challenge --%>
				</div>
				
				<div style="float: right">
					<form action="<%=quiz.getQuizStartPage()%>" method="POST">
						<input type="hidden" name="quizName" value=<%=quiz.getQuizName()%>>
						<%
							String disabledAttr = "";
							if (!quiz.isCanPractice())
								disabledAttr = "disabled";
						%>
						<!-- A way to initiate taking the quiz. A way to
		start the quiz in practice mode, if available.-->
						<input type="checkbox" name="practiceMode" value="true"
							<%=disabledAttr%>> Start in practice mode <input
							type="submit" value="Start Quiz">
					</form>

				</div>
				
				<div style="float: right">
					<%
						// A way to start editing the quiz, if the user is the quizowner. 
						if (userName.equals(quiz.getCreatorId())) {
					%>
					<form action="<%=quiz.getQuizEditPage()%>" method="POST">
						<input type="hidden" name="quizName"
							value="<%=quiz.getQuizName()%>"> <input type="submit"
							value="Edit Quiz">
					</form>
					<%
						}
					%>
				</div>
				</div>
				<div class="clear"></div>
				
				<%--challenge --%>
				<div style="float: left">
					<form action='ChallengeLetterSent' target='hidFrame' method='post'
						id='letter'>
						<input type='hidden' name='quizName' value=<%=quizName%>>
						<div id='msg'></div>
					</form>
					<iframe name='hidFrame' style='display: none'></iframe>
				</div>
				<%--challenge --%>

				<div class="clear br"></div>
				<!-- TODO A list of the user’s past performance on this specific quiz. -->

				<!-- A list of the highest performers of all time. -->
				<div class="leftbox">
					<h3>High Scores</h3>
					<table class="fancy">
						<tr>
							<th>User Name</th>
							<th>Score</th>
							<th>Time Used</th>
						</tr>
						<%
							List<QuizEvent> highScores = quiz.highScoreEvents(5);
							for (int i = 0; i < highScores.size(); i++) {
								QuizEvent e = highScores.get(i);
								String taker = e.getUserName();
								Account takerAccount = new Account(taker);
								List<String> friends = takerAccount.getFriendsList();
								boolean forbid = taker.equals(userName) ? false
										: (takerAccount.getInfo("privacy").equals("1") ? (friends
												.contains(userName) ? false : true) : false);
								taker = forbid ? "anonymous" : Helper.displayUser(taker);
						%>
						<tr <%= (i%2==0)?"":"class=\"alt\"" %> >
							<td><%=taker%></td>
							<td><%=e.getScore()%></td>
							<td><%=e.getTimeElapsed() / 1000.0%>s</td>
						</tr>
						<%
							}
						%>
					</table>
					<div class="clear"></div>
				</div>


				<div class="rightbox">
					<!-- A list of top performers in the last day. List -->
					<h3>High Scores in the Last Day</h3>
					<table  class="fancy">
						<tr>
							<th>User Name</th>
							<th>Score</th>
							<th>Time Used</th>
						</tr>
						<%
							List<QuizEvent> highScoresLastday = quiz.highScoreLastDayEvents(5);
						for (int i = 0; i < highScoresLastday.size(); i++) {
							QuizEvent e = highScores.get(i);
								String taker = e.getUserName();
								Account takerAccount = new Account(taker);
								List<String> friends = takerAccount.getFriendsList();
								boolean forbid = taker.equals(userName) ? false
										: (takerAccount.getInfo("privacy").equals("1") ? (friends
												.contains(userName) ? false : true) : false);
								taker = forbid ? "anonymous" : Helper.displayUser(taker);
						%>
						<tr <%= (i%2==0)?"":"class=\"alt\"" %> >
							<td><%=taker%></td>
							<td><%=e.getScore()%></td>
							<td><%=e.getTimeElapsed() / 1000.0%>s</td>
						</tr>
						<%
							}
						%>
					</table>
					<div class="clear"></div>
				</div>

				<div class="clear br"></div>


				<div class="leftbox">
					<!-- A list showing the performance of recent test takers List -->
					<h3>Recent Taken Log</h3>
					<table  class="fancy">
						<tr>
							<th>User Name</th>
							<th>Score</th>
							<th>Time Used</th>
						</tr>
						<%
							List<QuizEvent> recentEvents = quiz.recentTakenEvents(5);
						for (int i = 0; i < recentEvents.size(); i++) {
							QuizEvent e = recentEvents.get(i);
								String taker = e.getUserName();
								Account takerAccount = new Account(taker);
								List<String> friends = takerAccount.getFriendsList();
								boolean forbid = taker.equals(userName) ? false
										: (takerAccount.getInfo("privacy").equals("1") ? (friends
												.contains(userName) ? false : true) : false);
								taker = forbid ? "anonymous" : Helper.displayUser(taker);
						%>
						<tr <%= (i%2==0)?"":"class=\"alt\"" %> >
							<td><%=taker%></td>
							<td><%=e.getScore()%></td>
							<td><%=e.getTimeElapsed() / 1000.0%>s</td>
						</tr>
						<%
							}
						%>
					</table>
					<div class="clear"></div>
				</div>

				<div class="rightbox">
					<!-- TODO Summary statistics on how well all users have performed on the quiz-->
					<h3>Quiz Statistics</h3>
					<table  class="fancy">
						<tr>
							<th>Out of</th>
							<th>Avg</th>
							<th>Lowest</th>
							<th>Hightest</th>
							<th>Total #</th>
						</tr>
						<tr>
							<td><%=quiz.getMaxScore() %></td>
							<td><%=String.format("%.2f", quiz.getAvgScore()) %></td>
							<td><%=quiz.getLowestScore() %></td>
							<td><%=quiz.getBestScore() %></td>
							<td><%=quiz.getTakenTimes() %></td>		
						</tr>
					</table>

					<div class="clear"></div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
