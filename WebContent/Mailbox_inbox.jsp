<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<%@ page import="user.User"%>
<%@ page import="user.Administrator"%>
<%@ page import="user.Message"%>
<%@ page import="user.Activity"%>
<%@ page import="user.TimeTrsf"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link href="Mailbox_style.css" rel="stylesheet" type="text/css" />
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
<title>Inbox - <%=userId%></title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Inbox</h1>
			</div>
		</div>
		
		<table border="2" rules="rows" id="box">
			<col class="user" />
			<col class="title" />
			<col class="time" />
			<tr>
				<th>From</th>
				<th>Title</th>
				<th>Date</th>
			</tr>
			<%
				List<String> msgsInbox = user.getMessageInbox();
				if (msgsInbox == null || msgsInbox.isEmpty()) {
					out.println("<tr><td></td><td>(empty)</td><td></td></tr>");
				} else {
					for (String msgCode : msgsInbox) {
						Message msg = user.getMessage("inbox", msgCode);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.S");
						Date time = sdf.parse(msg.getTime());
						String timeDscr = TimeTrsf.dscr(time, new Date());
						String style = "normal";
						if (!msg.getRead())
							style = "bold";
			%>
			<tr class="<%=style%>" bgcolor="#f0f0f0"
				onMouseOver="this.style.backgroundColor='#ffffcc'"
				onMouseOut="this.style.backgroundColor='#f0f0f0'"
				onclick="window.location.href='Mail.jsp?id=<%=userId%>&box=inbox&msg=<%=msgCode%>';">
				<td><a href="userpage.jsp?id=<%=msg.from%>"
					target="_top" style="color: black; text-decoration: underline"><%=msg.from%>
				</a></td>
				<td><%=msg.getTitle()%></td>
				<td><%=timeDscr%></td>
			</tr>
			<%
				}
				}
			%>
		</table>
	</div>

</body>
</html>