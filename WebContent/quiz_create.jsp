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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<%= QuestionFactory.printReference() %>
<script src="JavaScript/quiz_create.js"></script>
<meta charset="UTF-8">
<title>Create Quiz</title>
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<link href="CSS/quiz_display.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
<script type="text/javascript">
</script>
</head>
<%
	/*
	 * prepare the page for creating quiz
	 */
	// set default values for form inputs
	String quizName = "";
	String tagString = "";
	String quizDescription = "";
	String canPractice = "checked";
	String isRandom = "checked";
	String isOnePage = "";
	String isImmCorrection = "";
	String category = "";
	String existingRadio = "checked";
	String newRadio = "";

	// get form input values from session
	String tmp;
	tmp = (String) session.getAttribute("quizName");
	if (tmp != null)
		quizName = tmp;
	tmp = (String) session.getAttribute("tagString");
	if (tmp != null)
		tagString = tmp;
	tmp = (String) session.getAttribute("quizDescription");
	if (tmp != null)
		quizDescription = tmp;
	tmp = (String) session.getAttribute("canPractice");
	if (tmp != null && tmp.equals("false"))
		canPractice = "";
	tmp = (String) session.getAttribute("isRandom");
	if (tmp != null && tmp.equals("false"))
		isRandom = "";
	tmp = (String) session.getAttribute("isOnePage");
	if (tmp != null && tmp.equals("true"))
		isOnePage = "checked";
	tmp = (String) session.getAttribute("isImmCorrection");
	if (tmp != null && tmp.equals("true"))
		isImmCorrection = "checked";
	tmp = (String) session.getAttribute("categoryType");
	if (tmp != null) {
		category = (String) session.getAttribute("category");
		if (tmp.equals("new_category")) {
			existingRadio = "";
			newRadio = "checked";
		} else {
			existingRadio = "checked";
			newRadio = "";
		}
	}
%>
<body onload="changeOnePage(); changeImmCorr();">
	<div id="wrapper">
		<div id="inner">
			<div id="header">
			<h1>Create Quiz</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
	<form action="QuizCreateAndSaveServlet" method="post" onsubmit="return validateForm()">
		<h2>Quiz Information</h2>
		<div>
			Quiz Name: <input type="text" name="quizName" required
				value="<%=quizName%>" id="quizName"><span id="quizNameStatus"></span><br> 
			Quiz Description:<br>
			<textarea name="quizDescription" rows="10" cols="50" required><%=quizDescription%></textarea>
			<br>
		
		<div id="category_type">
			Category:
			<table>
				<tr id="existing_categories">
					<td><input type="radio" name="category_type"
						value="existing_categories" <%= existingRadio %> onclick="chooseExistingCategory();">Choose Category:</td>
					<td><select name="existing_categories" id="existing_categories_box" <%= (existingRadio.equals("checked")?"":"disabled=\"disabled\"")%>>
							<%
								QuizManager quizManager = new MyQuizManager();
								Set<String> categorySet = quizManager.getCategories();
								for (String c : categorySet) {
							%>
							<option value="<%=c%>"  <%= (existingRadio.equals("checked") && c.equals(category)?"selected":"")%> ><%=c%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr id="new_category">
					<td><input type="radio" name="category_type" value="new_category" <%= newRadio %> onclick="enterNewCategory();">New Category:</td>
					<td><input type="text" name="new_category" id="new_category_box" <%= (newRadio.equals("checked")?"":"disabled=\"disabled\"")%>  value="<%= (newRadio.equals("checked")?category:"")%>"></td>

				</tr>
			</table>
		</div>

		Tags: <input type="text" name="tagString" value="<%=tagString%>"><br> 
		<span class="notice">Tags input format: #tagName1#tagName2 (requires a pound before each name, no other separators)<br></span>
			<input type="checkbox" name="canPractice" value="true" <%=canPractice%>>Allow practice mode<br> 
			<input type="checkbox" name="isRandom" value="true" <%=isRandom%>>Automatically randomized question order<br> 
			<input type="checkbox" name="isOnePage" value="true" onchange="changeOnePage();" id="OnePage" <%=isOnePage%>>Displays in one page<br> 
			<input type="checkbox" name="isImmCorrection" value="true" onchange="changeImmCorr();" id="isImmCorrection" <%=isImmCorrection%> >Allow immediate Correction if Multi-Page mode
			enabled<br>
		</div>

		<h2>Questions:</h2>
		<div class="notice">Note: all "Time Limit" field is measured by second, and could only be an integer. The default value '0' stands for no time limit.<br></div>
		<div id="questions"></div>
		<select name="questionType" id="questionTypeList">
			<%
				String[] questionTypes = QuestionFactory.getQuestionTypes();
				for (int i = 0; i < questionTypes.length; i++) {
					String typeName = questionTypes[i];
			%>
			<option value="<%=typeName%>"><%=typeName%></option>
			<%
				}
			%>
		</select> 
		<input type="button" value="Add New Question" onclick="addQuestion();"><br> 
		<input type="hidden" name="max_num" value="0" id="max_num">
		<div id = "submit_btn">
		<input type="submit" value="Submit Quiz">
		</div>
	</form>
	<!-- question templates of all supported question -->
	<%
		for (int i = 0; i < questionTypes.length; i++) {
			String questionType = questionTypes[i];
	%>
	<div id="<%=questionType%>_template" hidden="hidden" class="question">
		<%
			out.println(QuestionFactory.printCreateHtmlSinglePage(questionType));
		%>
		<input type="button" value="Delete Question" onclick="deleteQuestion(this);">
	</div>
	
	<%
		}
	%>
	</div>
	</div>
	</div>
	</div>
	 
</body>
</html>
