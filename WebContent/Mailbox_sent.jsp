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
<title>Sent - <%=userId%></title>
</head>
<body>
<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Sent</h1>	
			</div>
		</div>

	<table width="400" border="2" rules="rows">
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
	</div>	
</body>
</html>