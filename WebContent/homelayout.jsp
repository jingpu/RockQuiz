<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.UserManager"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="homestyle.css" rel="stylesheet" type="text/css" />
<%
	String userId = request.getParameter("id");

	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}

	Account user = new Account(userId);
	String status = UserManager.getAccountInfo(userId, "status");

	// generate achievements history
	List<String> achieves = user.getAchievements();
	// generate quizzes taken history
	List<String[]> taken = user.getQuizTaken();
	// generate quizzes created history
	List<String> created = user.getQuizCreated();
	// mail messages
	List<String> inbox = user.getMessageInbox();
	List<String> sent = user.getMessageSent();
	List<String> unread = user.getUnreadMessage();
	int unreadCount = unread.size();
	// friends' activities
	List<Activity> friendsAct = user.getFriendsRecentActivity();
	System.out.println("mark1");
	String mailBoxUrl = "Mailbox.jsp?id=" + userId;
	String userpageUrl  = "userpage.jsp?id=" + userId;
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
				<h2><a href=<%=userpageUrl%>>My Page</a> | <a href=<%=mailBoxUrl%>>Message(<%=unreadCount%>)</a> | <a href="">Log out</a></h2>
				</div>
			</div>
			
			<dl id="browse">
				<dt>Announcements</dt>
				<dd class="readmore"><a href=""><b>MORE</b></a></dd>
				<dt>New Message</dt>
				<%
				if (unread.isEmpty()) {
					out.println("<dd>No New Message</dd>");
				} else {
				for (String msgCode : unread) {
					Message msg = user.getMessage("inbox", msgCode);
					String description = msg.title;
					if (msg.type == "n") {
						description = "msg.from" + " sends you a message";
					}
					SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.S");
					Date time = sdf.parse(msg.getTime());
					Date now = new Date();
					String timeDscr = TimeTrsf.dscr(time, now);
					out.println("<dd>" + description + " " + timeDscr + "</dd>");
				}
				}
				%>
				<dd class="readmore"><a href=<%=mailBoxUrl%>><b>MORE</b></a></dd>
				<dt>Common Link</dt>
				<dd><a href="">111</a></dd>
				<dd><a href="">222</a></dd>
				<dd><a href="">333</a></dd>
				<dd><a href="">444</a></dd>
				<dd><a href="">555</a></dd>
				<dd><a href="">666</a></dd>
				<dt>Search Quizzers Or Users</dt>
				<dd class="searchform">
					<form action="SearchServlet" method="post">
						<div><input type="text" name="query" class="text"
						placeholder="Search quizzes OR users here" /></div>
						<div class="readmore"><input type="image" src="images/search.gif" /></div>
					</form>
				</dd>
			</dl>
			
			<div id="body">
				<div class="inner">									
					<div class="leftbox">
						<h3>Popular Quizzes</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Recent Quizzes</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					
					<div class="clear br"></div>
					
					<div class="leftbox">
						<h3>Quizzes I Took</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Quizzes I Create</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					
					<div class="clear br"></div>
					
					<div class="leftbox">
						<h3>My Achievements</h3>
						<ul>
						<%
							for (String str : achieves) {
								out.println("<li>" + str + "</li>");
							}
						%>
						</ul>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Friends Activities</h3>
						<ul>
						<%
							for (Activity act : friendsAct) {
								out.println("<li>" + act.toString() + "</li>");
							}
						%>
					</ul>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					
					<div class="clear br"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>