<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="Mailbox_style.css" rel="stylesheet" type="text/css" />
<title>Reading Message</title>
</head>
<%
	String userId = request.getParameter("id");
	session = request.getSession();
	String guest = (String) session.getAttribute("guest");
	if (userId == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}
	String box = request.getParameter("box");
	String msgCode = request.getParameter("msg");
	Account user = new Account(userId);
	Message msg = user.readMessage(box, msgCode);
	String to = msg.to == userId ? msg.from : msg.to;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	Date time = sdf.parse(msg.getTime());
	String timeDscr = TimeTrsf.dscr(time, new Date());
	String fromDisplay = msg.to == userId ? ("<a href='userpage.jsp?id="
			+ msg.from + "' target=\"_top\">" + msg.from + "</a>")
			: msg.from;
	String toDisplay = msg.to == userId ? msg.to
			: ("<a href='userpage.jsp?id=" + msg.to
					+ "' target=\"_top\">" + msg.to + "</a>");
	String retUrl = "";
	if (box.equals("inbox")){
		retUrl = "Mailbox_inbox.jsp";
	} else if (box.equals("sent")){
		retUrl = "Mailbox_sent.jsp";
	}
	System.out.println(retUrl);
%>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Message</h1>
			</div>
		</div>

		<table border="2" width="300" rules="rows">
			<tr>
				<th><%=msg.getTitle()%> | <%=timeDscr%></th>
			</tr>
			<tr>
				<td>From: <%=fromDisplay%></td>
			</tr>
			<tr>
				<td>To:<%=toDisplay%></td>
			</tr>
			<tr>
				<td><%=msg.getContent()%></td>
			</tr>
		</table>
	</div>
	<p>
	<form action="<%=retUrl%>">
	<%
		System.out.println(retUrl);
	%>
		<input type="hidden" name="id" value="<%=userId%>">
		<input type="submit" value="Back">
	</form>
	<form action="WriteMessage.jsp?id=<%=guest%>&to=<%=to%>">
		<input type="submit" value="Reply">
	</form>
	<form action="DeleteMessage" method="post">
		<input name="code" type="hidden" value="<%=msgCode%>"> <input
			name="box" type="hidden" value="<%=box%>"> <input
			type="submit" value="Delete">
	</form>
	</p>
</body>
</html>

