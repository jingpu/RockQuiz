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
	<h1>Mailbox</h1>
	
	<form action="WriteMessageServlet" method="post">
		<input name="username" type="hidden" value=<%=userId%>> <input
			type="submit" value="Compose">
	</form>
	<h2>Inbox</h2>
	<table width="300" border="1" rules="rows">
		<tr>
			<th>From</th>
			<th>Title</th>
			<th>Date</th>
		</tr>
		<%
			List<String> msgsInbox = user.getMessageInbox();
			for (String msgCode : msgsInbox) {
				Message msg = user.getMessage("inbox", msgCode);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.S");
				Date time = sdf.parse(msg.getTime());
				String timeDscr = TimeTrsf.dscr(time, new Date());
				
				if (!msg.getRead())  out.println("<b>");
				out.println("<tr><td>" + msg.from + "</td><td>" + "<a href=\"Mail.jsp?id=" 
					+ userId + "&box=inbox&msg=" + msgCode + "\">" + msg.title + "</a></td><td>"
					+ timeDscr + "</td></tr>");
				if (!msg.getRead())  out.println("</b>");
			}
		%>
	</table>
	<h2>Sent</h2>
	<table width="300" border="1" rules="rows">
		<tr>
			<th>To</th>
			<th>Title</th>
			
			<th>Date</th>
		</tr>

		<%
			List<String> msgsSent = user.getMessageSent();
			for (String msgCode : msgsSent) {
				//System.out.println(msgCode);
				Message msg = user.getMessage("sent", msgCode);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.S");
				Date time = sdf.parse(msg.getTime());
				String timeDscr = TimeTrsf.dscr(time, new Date());
				
				out.println("<tr><td>" + msg.to + "</td><td>"
						+ "<a href=\"Mail.jsp?id=" + userId + "&box=sent&msg="
						+ msgCode + "\">" + msg.title + "</a></td><td>" + timeDscr
						+ "</td></tr>");
			}
		%>
	</table>
</body>
</html>