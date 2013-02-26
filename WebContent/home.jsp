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
	String guest = (String) session.getAttribute("guest");
	if (userId == null || guest.equals("guest")) {
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
	List<String> taken = user.getQuizTaken();
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
	String announce = (String) session.getAttribute("announce");
	String mailBoxUrl = "Mailbox.jsp?id=" + userId;
%>
<title>RockQuiz - <%=userId%></title>
</head>
<body>
	<%--announce --%>
	<p><%=announce%></p>
	<p><%=new Date()%></p>
	<h2>
		<%=userId%>
	</h2>

	<%--new message --%>
	<h3>
		<a href=<%=mailBoxUrl%>>Message(<%=unreadCount%>)
		</a>
	</h3>

	<ul>
		<%
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
				out.println("<li>" + description + " " + timeDscr + "/li>");
			}
		%>
	</ul>
	
	<%--my friends list--%>
	<h3>Friends List</h3>
	
	<%--achievements list --%>
	<h3>Achievements</h3>
	<ul>
		<%
			for (String str : achieves) {
				out.println("<li>" + str + "</li>");
			}
		%>
	</ul>

	<%--quizzes search box--%>
	<form action="QuizSearchServelet">
		<p><input type="text" name="quizSearch" placeholder="Search quizzes here">
		<input type="submit" value="Search"></p>
	</form>
	
	<%--users search box--%>
	<form action="UserSearchServelet">
		<p><input type="text" name="userSearch" placeholder="Search user here">
		<input type="submit" value="Search"></p>
	</form>
	
	<%--popular quizzes --%>
	<h3>Popular Quizzes</h3>
	
	<%--recent created quizzes --%>
	<h3>Recent Created Quizzes</h3>

	<%--my quizzes taken history--%>
	<h3>I Took</h3>
	<ul>
		<%
			for (int i = 0; i < 5; i++) {
				if (i == taken.size())
					break;
				out.println("<li>" + taken.get(i) + "</li>");
			}
		%>
	</ul>
	
	<%--my friends activity --%>
	<h3>Friends Activities</h3>
	<ul>
		<%
			for(Activity act : friendsAct){
				out.println("<li>" + act.toString() + "</li>");
			}
		%>
	</ul>
	
	<%--my quizzes created history--%>
	<h3>I Create</h3>
	<ul>
		<%
			for (int i = 0; i < 5; i++) {
				if (i == taken.size())
					break;
				out.println("<li>" + created.get(i) + "</li>");
			}
		%>
	<ul>
</body>
</html>