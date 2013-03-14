<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Change Password</title>
</head>
<%
	String error = request.getParameter("err");
	System.out.println(error);
	String id = (String) session.getAttribute("guest");
	if (id == null || id.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	}
%>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
			<h1>Change Password</h1>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	
	<%
		if (error != null && error.equals("0")) {
			System.out.println("valid");
	%>
	New Password Saved
	<%
		}
	%>
	<form action="ChangePassword" method="post">
		<p>
			Current Password: <input type="password" name="currPwd">
			<%
				if (error != null && error.equals("1")) {
					System.out.println("error1");
			%>
			Wrong Password
			<%
				}
			%>
		</p>
		<p>
			New Password: <input type="password" name="newPwd">
		</p>
		<p>
			Confirm Password: <input type="password" name="cnfPwd">
			<%
				if (error != null && error.equals("2")) {
					System.out.println("error2");
			%>
			Unmatched New Password
			<%
				}
			%>
		</p>
		<p>
			<input type="submit" value="Save"> <a
				href="profile.jsp?id=<%=id%>"><input type="button"
				value="Return"></a>
		</p>
	</form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>