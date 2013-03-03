<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Account Name In Use</title>
</head>
<body>
    <h1>The Name <%=request.getParameter("name") %> is Already In Use</h1>
    <h3>Please enter another name and password.</h3>
    <p><a href="createAccount.html">Try again.</a></p>
</body>
</html>