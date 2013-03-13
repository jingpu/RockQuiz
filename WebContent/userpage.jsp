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
	if (id == null) {
		response.sendRedirect("userpage.jsp?id="+ guest);
		return;
	} else if(!UserManager.alreadyExist(id) || id.equals("guest")){
		response.sendRedirect("userinvalid.jsp?id="+ id);
		return;
	}
	String title = id + "'s Page";
	
	Account pageOwner = new Account(id);
	List<String> friends = pageOwner.getFriendsList();
	boolean forbid = pageOwner.equals(guest)?false : (pageOwner.getInfo("privacy").equals("1")?(friends.contains(guest)?false:true):false);
	// generate achievements history
	List<Activity> achieves = pageOwner.getAchievements();
	// generate quizzes taken history
	List<Activity> taken = pageOwner.getQuizTaken();
	// generate quizzes created history
	List<Activity> created = pageOwner.getQuizCreated();
	//System.out.println(pageOwner.seeFriendStatus(guest));
%>

<script language="javascript" type="text/javascript">
<!--
	function friendQuery(text) {
		var r = confirm(text);
		if (r) {
			return true;
		}
		return false;
	}
//-->
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
					<h2>
						<a href="home.jsp?id=<%=guest%>">Home</a> |
						<%
							if (!guest.equals(id)) {
						%>

						<%--if guest!=id, show friend related operation --%>
						<%
							if (pageOwner.seeFriendStatus(guest).equals("x")) {
						%>
						<a href="RequestFriend?to=<%=id%>">Add Friend</a> |
						<%
							} else if (pageOwner.seeFriendStatus(guest).equals("r")) {
									String text = "Do you want to add " + id + " as friend?";
						%>
						<a href="RespondFriend?to=<%=id%>"
							onclick="friendQuery('<%=text%>')">Respond to Friend Request</a>
						|
						<%
							} else if (pageOwner.seeFriendStatus(guest).equals("u")) {
									String text = "Do you want to cancel your friend request to "
											+ id + "?";
						%>
						<a href="RemoveFriend?to=<%=id%>"
							onclick="friendQuery('<%=text%>')">Cancel Friend Request</a> |
						<%
							} else if (pageOwner.seeFriendStatus(guest).equals("f")) {
									String text = "Are you sure to unfriend " + id + "?";
						%>
						<a href="RemoveFriend?to=<%=id%>"
							onclick="friendQuery('<%=text%>')">Unfriend</a> |
						<%
							}
						%>

						<%--if guest!=id, show message --%>
						<a href="WriteMessage.jsp?id=<%=guest%>&to=<%=id%>"
							target="_blank">Message me</a> |
						<%
							}
						%>
						<a href="Logout">Log Out</a>
					</h2>
				</div>
			</div>

			<dl id="browse">
				<%
					if (!forbid) {
				%>
				<dt>Achievements</dt>
				<%
					if (achieves.isEmpty()) {
				%>
				<p><%=id%>
					don't have any achievements yet.
				</p>
				<%
					} else {
							for (int k = 0; k < 5; k++) {
								if (k == achieves.size())
									break;
								out.println("<p>" + achieves.get(k).content + "</p>");
							}
						}
					}
				%>
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
					<%
						if (!forbid) {
					%>
					<div class="leftbox">
						<h3>Quizzes Taken</h3>
						<%
							if (taken.isEmpty()) {
						%>
						<p><%=id%>
							did't take any quiz yet.
						</p>
						<%
							} else {
									for (int k = 0; k < 5; k++) {
										if (k == taken.size())
											break;
										out.println("<p>" + taken.get(k).toString(true)
												+ "</p>");
									}
								}
						%>
						<p class="readmore">
							<a href="quizTakan.jsp?id=<%=id%>"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>
					<div class="rightbox">
						<h3>Quizzes Created</h3>
						<%
							if (created.isEmpty()) {
						%>
						<p><%=id%>
							did't create any quiz yet.
						</p>
						<%
							} else {
									for (int k = 0; k < 5; k++) {
										if (k == created.size())
											break;
										out.println("<p>" + created.get(k).toString(true)
												+ "</p>");
									}
								}
						%>
						<p class="readmore">
							<a href="quizCreated.jsp?id=<%=id%>"><b>MORE</b></a>
						</p>
						<div class="clear"></div>
					</div>

					<div class="clear br"></div>
					<%
						} else {%>
							<h1 style='font-family:serif; color:black;'><%=id%> set privacy. <br>Only friends can see this page.</h1>
					<%	}
					%>
				</div>
			</div>

		</div>
	</div>
</body>
</html>