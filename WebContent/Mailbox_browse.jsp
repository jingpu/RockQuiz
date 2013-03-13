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
	Account user = new Account(userId);
%>
<title>Mailbox - <%=userId%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1><%=userId%>'s Mailbox
				</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp?id=<%=guest%>" target="_top">Home</a>
					</h2>
				</div>
			</div>

			<dl id="browse">
				<dt>
					<a href="WriteMessage.jsp?id=<%=userId%>" target="_blank">Compose</a>
				</dt>
				<dd></dd>
				<dd>
					<a href="Mailbox_inbox.jsp?id=<%=userId%>" target="another">Inbox</a>
				</dd>
				<dd>
					<a href="Mailbox_sent.jsp?id=<%=userId%>" target="another">Sent</a>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>