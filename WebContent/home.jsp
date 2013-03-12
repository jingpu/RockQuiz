<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
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
<%
	String userId = request.getParameter("id");
	System.out.println(userId);
	String guest = (String) session.getAttribute("guest");
	System.out.println(guest);
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
	List<Activity> achieves = user.getAchievements();
	// generate quizzes taken history
	List<Activity> taken = user.getQuizTaken();
	// generate quizzes created history
	List<Activity> created = user.getQuizCreated();
	// mail messages
	List<String> inbox = user.getMessageInbox();
	List<String> unread = user.getUnreadMessage();
	int unreadCount = unread.size();
	// friends' activities
	List<Activity> friendsAct = user.getFriendsRecentActivity();
	//System.out.println("mark1");
	String mailBoxUrl = "Mailbox_frame.jsp?id=" + userId;
	//String userpageUrl  = "userpage.jsp?id=" + userId;
	String userpageUrl = "userpage.jsp?id=" + userId;
%>
<title>RockQuiz - <%=userId%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1><%=userId%></h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href=<%=userpageUrl%>>My Page</a> | <a href=<%=mailBoxUrl%>>Mailbox
						</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>

			<dl id="browse">
				<dt class="create">
					<a href="QuizCreationServlet">Create Quiz</a>
				</dt>
			
				<dt>Announcements</dt>
				<dd>
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
				</dd>
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
							out.println("<dd><a href='Mail.jsp?id=" + userId
									+ "&box=inbox&msg=" + msgCode + "'>" + description
									+ " " + timeDscr + "</a></dd>");
							i++;
						}
					}
				%>
				<dd class="readmore">
					<a href=<%=mailBoxUrl%>><b>MORE</b></a>
				</dd>
				<dt>Quick Link</dt>
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
					<a href="profile.jsp?id=<%=userId%>">Edit Profile</a>
				</dd>
				<dd>
					<a href="">444</a>
				</dd>
				<dd>
					<a href="">555</a>
				</dd>
				<dd>
					<a href="">666</a>
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
								List<Quiz> popQuizzes = man.getPopularQuiz(5);
								//System.out.println(popQuizzes);
								for (Quiz quiz : popQuizzes) {
									String quizUrl = quiz.getSummaryPage();
									String creator = quiz.getCreatorId();
									String description = quiz.getQuizName() + "\n"
											+ quiz.getQuizDescription();
							%>
							<li class="quizlist"><a href='<%=quizUrl%>'
								title='<%=description%>'><%=quiz.getQuizName()%></a> (by:<a
								href="userpage.jsp?id=<%=creator%>"><%=creator%></a>)</li>
							<%
								}
							%>
						</ul>

						<p class="readmore">
							<a href="popQuiz.jsp"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Recent Quizzes</h3>
						<ul>
							<%
								List<Quiz> recentQuizzes = man.getRecentCreateQuiz(5);
								for (Quiz quiz : recentQuizzes) {
									String quizUrl = quiz.getSummaryPage();
									String creator = quiz.getCreatorId();
									String description = quiz.getQuizName() + "\n"
											+ quiz.getQuizDescription();
							%>
							<li class="quizlist"><a href='<%=quizUrl%>'
								title="<%=description%>"><%=quiz.getQuizName()%></a> (by:<a
								href="userpage.jsp?id=<%=creator%>"><%=creator%></a>)</li>
							<%
								}
							%>
						</ul>
						<p class="readmore">
							<a href="recentQuiz.jsp"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>

					<div class="clear br"></div>

					<div class="leftbox">
						<h3>Quizzes I Took</h3>

						<%
							if (taken.isEmpty()) {
						%>
						<p>You did't take any quiz yet.</p>
						<%
							} else {
								for (int k = 0; k < 3; k++) {
									if (k == taken.size())
										break;
									out.println("<p>" + taken.get(k).toStringMe() + "</p>");
								}
							}
						%>

						<p class="readmore">
							<a href="quizTaken.jsp?id=<%=userId%>"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Quizzes I Created</h3>
						<%
							if (created.isEmpty()) {
						%>
						<p>You did't create any quiz yet.</p>
						<%
							} else {
								for (int k = 0; k < 3; k++) {
									if (k == created.size())
										break;
									out.println("<p>" + created.get(k).toStringMe() + "</p>");
								}
							}
						%>
						<p class="readmore">
							<a href="quizCreated.jsp?id=<%=userId%>"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>

					<div class="clear br"></div>

					<div class="leftbox">
						<h3>My Achievements</h3>
						<%
							if (achieves.isEmpty()) {
						%>
						<p>You don't have any achievements yet.</p>
						<%
							} else {
								for (int k = 0; k < 5; k++) {
									if (k == achieves.size())
										break;
									out.println("<p>" + achieves.get(k).content + "</p>");
								}
							}
						%>
						<p class="readmore">
							<a href="achieves.jsp?id=<%=userId%>"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Friends Activities</h3>
						<%
							if (friendsAct.isEmpty()) {
						%>
						<p>There is no news yet.</p>
						<%
							} else {
								int p = 0;
								for (Activity act : friendsAct) {
									if (p == 5)
										break;
									out.println("<p>" + act.toString() + "</p>");
									p++;
								}
							}
						%>
						<p class="readmore">
							<a href="friendsActivity.jsp?id=<%=userId%>"><b>MORE</b></a>
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
