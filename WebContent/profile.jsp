<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit Profile</title>
<%
	String userId = request.getParameter("id");

	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}

	Account user = new Account(userId);
	String regTime = user.getInfo("registrationTime");
	String status = user.getInfo("status").equals("s")
			? "admin"
			: "user";
	System.out.println("new gender = " + user.getInfo("gender"));
	boolean male = user.getInfo("gender").equals("m");
	System.out.println("male = " + male);
	String email = user.getInfo("email");
	String password = user.getInfo("password");
%>

<script language="javascript" type="text/javascript">
	function judge() {
		// email
		var pos1 = document.forms[0].email.value.indexOf("@");
		var pos2 = document.forms[0].email.value.indexOf(".");
		if (pos1 == -1 && pos2 == -1) {
			alert("Please fill in the correct email address!");
			document.forms[0].email.focus();
			document.forms[0].email.select();
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<h2>
		<a href="home.jsp">Home</a>
	</h2>
	<%
		String save = request.getParameter("p");
		System.out.println("save = " + save);
		if (save != null && save.equals("s")) {
			System.out.println("valid");
	%>
	Profile Saved
	<%
		}
	%>
	<h1><%=userId%></h1>
	<p>
		Registration Time:
		<%=regTime%></p>
	<p>
		Status:
		<%=status%></p>
	<h3>Edit Profile</h3>
	<a href="password.jsp">Change Password</a>
	<form action="SaveProfile?id=<%=userId%>" method="post"
		onsubmit="return judge()">
		<p>
			Gender: <input type="radio" name="gender" value="m" <%if (male) {%>
				checked="checked" <%}%>>Male <input type="radio"
				name="gender" value="f" <%if (!male) {%> checked="checked" <%}%>>Female
		</p>
		<p>
			Email: <input type="text" name="email" value="<%=email%>">
		</p>
		<p>
			<input type="submit" value="Save">
		</p>
	</form>
</body>
</html>