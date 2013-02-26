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
<%
	String url = request.getRequestURL().toString();
	String userId = request.getParameter("id");
	String announce = (String) session.getAttribute("announce");
	String guest = (String) session.getAttribute("user");
	if (guest.equals("guest")) {
		response.sendRedirect("index.html");
	} else if (!guest.equals(userId)) {
		response.sendRedirect("userpage.jsp?id=" + userId);
	}
	Account user = new Account("userId");
	String status = UserManager.getAccountInfo(userId, "status");

	// generate achievements history
	List<String> achieves = user.getAchievements();
	// generate quizzes taken history
	List<String> taken = user.getQuizTaken();
	// generate quizzes created history
	List<String> created = user.getQuizCreated();
	// mail messages
	List<Message> inbox = user.getMessageInbox();
	List<Message> sent = user.getMessageSent();
	List<Message> unread = user.getUnreadMessage();
	int unreadCount = unread.size();
	// friends' activities
	List<Activity> friendsAct = user.getFriendsRecentActivity();
%>
<title>RockQuiz - <%=userId%></title>
</head>
<body>
	<p><%=announce%></p>
	<h2>
		<p><%=userId%></p>
	</h2>

	<form action="Mailbox">
		<input name="username" type="hidden" value=userId />
		<%-- input name="url" type="hidden" value=url /--%>
		<h3>
			Message(<%=unreadCount%>)
		</h3>
	</form>
	<%
		for (Message msg : unread) {
			String description = msg.title;
			if (msg.type == "n") {
				description = "msg.from" + " sends you a message";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.S");
			Date time = sdf.parse(msg.time);
			Date now = new Date();
			String timeDscr = TimeTrsf.dscr(time, now);
			out.println("<li><p>" + description + " " + timeDscr
					+ "</p></li>");
		}
	%>

	<h3>Achievements</h3>
	<%
		for (String str : achieves) {
			out.println("<li><p>" + str + "</p></li>");
		}
	%>

	<h3>
		<p>Popular Quizzes</p>
	</h3>

	<h3>
		<p>Recent Created Quizzes</p>
	</h3>

	<h3>
		<p>I Recently Took</p>
	</h3>
	<%
		for (int i = 0; i < 5; i++) {
			out.println("<li><p>" + taken.get(i) + "</p></li>");
		}
	%>
	<h3>
		<p>I Create</p>
	</h3>
	<%
		for (int i = 0; i < 5; i++) {
			out.println("<li><p>" + created.get(i) + "</p></li>");
		}
	%>

	<%--  --%>
</body>
</html>