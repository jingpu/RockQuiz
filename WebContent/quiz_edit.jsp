<%@page import="util.Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<%
	String userName = (String) session.getAttribute("guest");
	String quizName = request.getParameter("quizName");
	MyQuiz quiz = new MyQuiz(quizName);

	if (userName == null || userName.equals("guest")) {
		response.sendRedirect("index.html");
		return;
	}
	if (!userName.equals(quiz.getCreatorId())) {
		response.sendRedirect(quiz.getSummaryPage());
		return;
	}

	/*
	 * prepare the page for edite quiz
	 */
	// set default values for form inputs
	String canPractice = quiz.isCanPractice() ? "checked" : "";
	String isRandom = quiz.isRandom() ? "checked" : "";
	String isOnePage = quiz.isOnePage() ? "checked" : "";
	String isImmCorrection = quiz.isImmCorrection() ? "checked" : "";
	String category = quiz.getCategory();
	List<QuestionBase> questionList = quiz.getQuestionList();
%>
<html>
<head>
<%=QuestionFactory.printReference()%>
<script src="JavaScript/quiz_create.js"></script>
<meta charset="UTF-8">
<title>Edit Quiz - <%=quiz.getQuizName()%></title>
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<link href="CSS/quiz_display.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>

<body onload="changeOnePage(); changeImmCorr();">
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>Edit Quiz</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
					<form action="QuizEditServlet" method="post" onsubmit="return validateForm()">
						<h2><%=quiz.getQuizName()%></h2>
						<input type="hidden" name="quizName" value="<%=quiz.getQuizName()%>">
						<div>
							Quiz Description:<br>
							<textarea name="quizDescription" rows="10" cols="50" required><%=quiz.getQuizDescription()%></textarea>
							<div id="category_type">
								Category:
								<table>
									<tr id="existing_categories">
										<td><input type="radio" name="category_type"
											value="existing_categories" checked
											onclick="chooseExistingCategory();">Choose Category:</td>
										<td><select name="existing_categories"
											id="existing_categories_box">
												<%
													QuizManager quizManager = new MyQuizManager();
													Set<String> categorySet = quizManager.getCategories();
													for (String c : categorySet) {
												%>
												<option value="<%=c%>"
													<%=c.equals(category) ? "selected" : ""%>><%=c%></option>
												<%
													}
												%>
										</select></td>
									</tr>
									<tr id="new_category">
										<td><input type="radio" name="category_type"
											value="new_category" onclick="enterNewCategory();">New
											Category:</td>
										<td><input type="text" name="new_category"
											id="new_category_box" disabled="disabled" value=""></td>
									</tr>
								</table>
							</div>

							Tags: <input type="text" name="tagString"
								value="<%=Helper.generateTags(quiz.getTags())%>"><br>
							<input type="checkbox" name="canPractice" value="true"
								<%=canPractice%>>Allow practice mode<br> 
							<input type="checkbox" name="isRandom" value="true" <%=isRandom%>>Automatically
							randomized question order<br> 
							<input type="checkbox" name="isOnePage" value="true"
								onchange="changeOnePage();" id="OnePage" <%=isOnePage%>>Displays in one page<br>
							<input type="checkbox" name="isImmCorrection" value="true"
								onchange="changeImmCorr();" id="isImmCorrection"
								<%=isImmCorrection%>>Allow immediate Correction if
							Multi-Page mode enabled<br>
						</div>

						<h2>Questions:</h2>
						<p class='notice'> Notice: changing scores and the number of correct answers in multi-answer question may result in a change of total score.</p>
						<div id="questions">
							<%
								for (int i = 0; i < questionList.size(); i++) {
									QuestionBase q = questionList.get(i);
							%>
							<div id="question_<%=i %>" class="question">
							<%= q.printEditHtml(i) %>
							</div>
							<%
								}
							%>
						</div>
						<div id = "submit_btn">
						<input type="submit" value="Save">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
