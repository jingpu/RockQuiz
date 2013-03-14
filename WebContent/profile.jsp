<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<%@ page import="user.*"%>
<%@ page import="quiz.*"%>
<%@ page import="util.Helper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<title>Edit Profile</title>
<%
	String userId = request.getParameter("id");
	String guest = (String) session.getAttribute("guest");
	if (guest == null || guest.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	} else if(userId == null && UserManager.alreadyExist(guest)){
		response.sendRedirect("profile.jsp?id=" + guest);
	} else if (!guest.equals(userId)) {
		response.sendRedirect("home.jsp?id=" + guest);
		return;
	}

	Account user = new Account(userId);
	String regTime = user.getInfo("registrationTime");
	String status = user.getInfo("status").equals("s")
	? "admin"
	: "user";
	boolean male = user.getInfo("gender").equals("m");
	String email = user.getInfo("email");
	String password = user.getInfo("password");
	String[] categories = Helper.string2Array(user.getInfo("category"));
	String privacy = user.getInfo("privacy");
	String checkStatus = privacy.equals("1")? "checked" : null;
	String antiCheckStatus = privacy.equals("0")? "checked" : null;
%>
</head>

<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
					<h1><%=userId%></h1>
	<h3>
		Registration Time:
		<%=regTime%></h3>
	<h3>
		Status:
		<%=status%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	<%
		String save = request.getParameter("p");
		if (save != null && save.equals("s")) {
	%>
	Profile Saved
	<%
		}
	%>
	<a href="password.jsp"><h4>Change Password</h4></a>
	<br>
	<form action="SaveProfile?id=<%=userId%>" method="post" id="profileform"
		onsubmit="return judge()">
		
			<h4>Gender: <input type="radio" name="gender" value="m" <%if (male) {%>
				checked="checked" <%}%>>Male <input type="radio"
				name="gender" value="f" <%if (!male) {%> checked="checked" <%}%>>Female</h4>
		<br>
		<h4>Email: 
		<input type="text" name="email" value="<%=email%>"></h4>
		<br>
		<h4>Interesting Fields: </h4>
		<center>
		<table>
			<tr>
				<td style='width:150px; text-align:left; vertical-align:top;'>
					<%
					if(categories != null){
						for (int i = 0; i < categories.length; i++) {
					%> <input type="checkbox" name="favor" value="<%=categories[i]%>"
					checked><%=categories[i]%><br><%
 	}}
 %>
					<div id="new_category_type"></div>
				</td>
				<td style='vertical-align:top;'><select name="existing_categories"
					id="existing_categories_box">
						<%
							QuizManager quizManager = new MyQuizManager();
							Set<String> categorySet = quizManager.getCategories();
							for (String c : categorySet) {
						%>
						<option value="<%=c%>"><%=c%></option>
						<%
							}
						%>
				</select>
					<button type='button' onclick='AddExistingCategory()'>Add Existing Field</button>
					<br> <input type="text" name="new_category"
					id="new_category_box">
					<button type='button' onclick='AddNewCategory()'>Create New Field</button>
				<td>
			</tr>
		</table>
		</center>
		<br>
		<h4>Set Privacy: </h4>
		<input type="radio" name="privacy" value="1" 
			<%=checkStatus%>>Only my friends can see my information<br><input type="radio" name="privacy" 
			value="0" <%=antiCheckStatus%>>Every one can see my information
		<p>
			<input type="submit" value="Save">
		</p>
	</form>
			</div>
	</div>
	</div>
	</div>
</body>
<script type="text/javascript" src="addnewcategory.js"></script>
</html>