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
	String to = msg.to == userId? msg.from : msg.to;
	SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.S");
	Date time = sdf.parse(msg.getTime());
	String timeDscr = TimeTrsf.dscr(time, new Date());
%>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Message</h1>	
			</div>
		</div>

	<table border="2" width="300" rules="rows">
		<tr><th><%=msg.title%>	<%=timeDscr %></th></tr>
		<tr><td><%=msg.from%></td></tr>
		<tr><td><%=msg.to%></td></tr>
		<tr><td><%=msg.content%></td></tr>
	</table>
	</div>
	
	<form action="ReplyMessage" method="post">
	<input name="to" type="hidden" value="<%=to%>">
	<input type="submit" value="Reply">
	</form>
	
	<form action="DeleteMessage" method="post">
	<input name="code" type="hidden" value="<%=msgCode%>">
	<input name="box" type="hidden" value="<%=box%>">
	<input type="submit" value="Delete">
	</form>
</body>
</html>

