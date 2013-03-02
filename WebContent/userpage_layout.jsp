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
<link href="userpage_style.css" rel="stylesheet" type="text/css" />

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

<script language="javascript" type="text/javascript">
	function friendQuery(text) {
		var r = confirm(text);
		if (r) {
			return true;
		}
		return false;
	}
</script>

<title><%=title%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1><%=id%></h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
				<h2><a href="home.jsp">Home</a> | <a href="">Log Out</a></h2>
				</div>
			</div>
			
			<dl id="browse">
				<dt>Achievements</dt>
				<%
				if (achieves.isEmpty()) {
					out.println("<dd>No Achievements</dd>");
				} else {
					for (String str : achieves) {
						out.println("<dd>" + str + "</dd>");
					}
				}				
				%>
				
				<%
				if(!guest.equals(id)) {
				%>
				
				<%--if guest!=id, show friend related operation --%>
				<%
				if (pageOwner.seeFriendStatus(guest).equals("x")) {
				%>
					<dt>
					<form action="RequestFriend" method="post">
						<input name="to" type="hidden" value=<%=id%>> <input
						type="submit" value="Add Friend">
					</form>
					</dt>
				<%
					} else if (pageOwner.seeFriendStatus(guest).equals("r")) {
						String text = "Do you want to add " + id + " as friend?";
				%>
				<dt>
				<form action="RespondFriend" method="post">
					<input type="hidden" name="to" value=<%=id%>> <input
					type="submit" value="Respond to Friend Request"
					onclick="friendQuery('<%=text%>')">
				</form>
				</dt>
				<%
				} else if (pageOwner.seeFriendStatus(guest).equals("u")) {
					String text = "Do you want to cancel your friend request to "
					+ id + "?";
				%>
				<dt>
				<form action="RemoveFriend" method="post">
					<input name="to" type="hidden" value=<%=id%>> <input
					type="submit" value="Cancel Friend Request"
					onclick="friendQuery('<%=text%>')">
				</form>
				</dt>
				<%
				} else if (pageOwner.seeFriendStatus(guest).equals("f")) {
					String text = "Are you sure to unfriend " + id + "?";
				%>
				<dt>
				<form action="RemoveFriend" method="post">
					<input name="to" type="hidden" value=<%=id%>> <input
					type="submit" value="Unfriend" onclick="friendQuery('<%=text%>')">
				</form>
				</dt>
				<%
				}
				%>

				<%--if guest!=id, show message --%>
				<dt>
				<form action="LeaveMessage" method="post">
					<input name="to" type="hidden" value=<%=id%>> <input
					type="submit" value="Message">
				</form>
				</dt>
				<% 
				}
				%>			
			</dl>
			
			<div id="body">
				<div class="inner">									
					<div class="leftbox">
						<h3>Quizzes Taken</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Quizzes Created</h3>
						<p class="readmore"><a href=""><b>MORE</b></a></p>
						<div class="clear"></div>
					</div>
					
					<div class="clear br"></div>
				</div>
			</div>

</body>
</html>