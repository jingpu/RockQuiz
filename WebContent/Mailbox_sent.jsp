<!--
  The MIT License (MIT)
  Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to 
  deal in the Software without restriction, including without limitation the
  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
  sell copies of the Software, and to permit persons to whom the Software is 
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in 
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
  THE SOFTWARE.
-->
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
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="Mailbox_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#wrapper {
	width: 500px;
}

</style>
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

		<table border="2" rules="rows" id="box">
			<col class="user" />
			<col class="title" />
			<col class="time" />
			<tr>
				<th>To</th>
				<th>Title</th>

				<th>Date</th>
			</tr>

			<%
				List<String> msgsSent = user.getMessageSent();
				if (msgsSent == null || msgsSent.isEmpty()) {
					out.println("<tr><td></td><td>(empty)</td><td></td></tr>");
				} else {
					for (String msgCode : msgsSent) {
						Message msg = user.getMessage("sent", msgCode);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.S");
						Date time = sdf.parse(msg.getTime());
						String timeDscr = TimeTrsf.dscr(time, new Date());
			%>
			<tr bgcolor="#f0f0f0"
				onMouseOver="this.style.backgroundColor='#ffffcc'"
				onMouseOut="this.style.backgroundColor='#f0f0f0'"
				onclick="window.location.href='Mail.jsp?id=<%=userId%>&box=sent&msg=<%=msgCode%>';">
				<td><a href="userpage.jsp?id=<%=msg.to%>" target="_top"
					style="color: black; text-decoration: none"><%=msg.to%></a></td>
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
