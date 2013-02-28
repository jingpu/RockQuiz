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
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	}
	String id = request.getParameter("id");
	if (id == null || !UserManager.alreadyExist(id)) {
		response.sendRedirect("home.jsp");
		return;
	}
	String title = id + "'s Page";

	Account pageOwner = new Account(id);
	// generate achievements history
	List<String> achieves = pageOwner.getAchievements();
	// generate quizzes taken history
	List<String[]> taken = pageOwner.getQuizTaken();
	// generate quizzes created history
	List<String> created = pageOwner.getQuizCreated();
%>
<title><%=title%></title>
</head>
<body>
	<%--return to homepage --%>
	<p>
		<a href="home.jsp">Home</a>
	</p>

	<%--announce --%>

	<p><%=new Date()%></p>
	<h2>
		<%=id%>
	</h2>

	<%--see friendship and leave message--%>
	<%
		if (pageOwner.seeFriendStatus(guest).equals("x")) {
			StringBuilder sb1 = new StringBuilder();
			sb1.append("<form action=\"RequestFriend\" method=\"post\">");
			sb1.append("<input name=\"to\" type=\"hidden\" value=\"" + id
					+ "\">");
			sb1.append("<input type=\"submit\" value=\"Add Friend\">");
			sb1.append("</form>");
			out.println(sb1.toString());

		} else if (pageOwner.seeFriendStatus(guest).equals("r")) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append("<form action=\"RespondFriend\" method=\"post\">");
			sb2.append("<input name=\"to\" type=\"hidden\" value=\"" + id
					+ "\">");
			sb2.append("<input type=\"submit\" value=\"Respond to Friend Request\">");
			sb2.append("</form>");
			out.println(sb2.toString());

		} else if (pageOwner.seeFriendStatus(guest).equals("u")
				|| pageOwner.seeFriendStatus(guest).equals("i")) {
			StringBuilder sb3 = new StringBuilder();
			sb3.append("<form action=\"CancelRequest\" method=\"post\">");
			sb3.append("<input name=\"to\" type=\"hidden\" value=\"" + id
					+ "\">");
			sb3.append("<input type=\"submit\" value=\"Cancel Friend Request\">");
			sb3.append("</form>");
			out.println(sb3.toString());

		} else if (pageOwner.seeFriendStatus(guest).equals("c")) {
			StringBuilder sb4 = new StringBuilder();
			sb4.append("<form action=\"RemoveFriend\" method=\"post\">");
			sb4.append("<input name=\"to\" type=\"hidden\" value=\"" + id
					+ "\">");
			sb4.append("<input type=\"submit\" value=\"Unfriend\">");
			sb4.append("</form>");
			out.println(sb4.toString());
		}
	%>
	
	<form action="LeaveMessage" method="post">
		<input name="to" type="hidden" value=<%=id%>> <input
			type="submit" value="Message">
	</form>

	<%--achievements list --%>
	<h3>Achievements</h3>
	<ul>
		<%
			for (String str : achieves) {
				out.println("<li>" + str + "</li>");
			}
		%>
	</ul>

	<%--my quizzes taken history--%>
	<h3>I Took</h3>
	<ul>
		<%
			for (int i = 0; i < 5; i++) {
				if (i == taken.size())
					break;
				out.println("<li>" + taken.get(i)[1] + "</li>");
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
	</ul>

	<%--log out--%>
	<form action="LogoutServlet" method="post">
		<input type="submit" value="Log Out">
	</form>

</body>
</html>