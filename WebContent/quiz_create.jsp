<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quiz.*"%>
<!DOCTYPE html>
<html>
<head>
<script>
function addQuestion() {
	var questionType = document.getElementById("questionTypeList").value;
    var questions = document.getElementById("questions");
    var qr = document.getElementById(questionType+"_template");
    var question = qr.cloneNode(true);
    question.removeAttribute("hidden");
    question.id = questionType;
    addSuffix(question);
    questions.appendChild(question);
    incMaxNum();
}

function addSuffix(question){
	var maxNum = document.getElementById("max_num");
	var curIndex = parseInt(maxNum.value);
	question.id = question.id + "_" + curIndex;
	var elements = question.querySelectorAll("input, textarea");
	for (var i = 0; i < elements.length; i++){
		var e = elements[i];
		var name = e.name + "_" + curIndex;
		e.name = name;
	}
}

function incMaxNum(){
	var maxNum = document.getElementById("max_num");
	var curIndex = parseInt(maxNum.value);
	maxNum.value = curIndex + 1;
}

function deleteQuestion(button) {
	var question = button.parentNode;
    var questions = document.getElementById("questions");
    questions.removeChild(question);
}

</script>
<meta charset="UTF-8">
<title>Create Quiz</title>
</head>
<body>
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
	%>
	<h1>Create Quiz</h1>
	<form action="QuizCreateAndSaveServlet" method="post">
		<h2>Quiz Information</h2>
		<p>
			Quiz Name: <input type="text" name="quizName" required value="<%=quizName%>"><br> 
			Tags: <input type="text" name="tagString" value="<%=tagString%>"><br> 
			Quiz Description:<br>
			<textarea name="quizDescription" rows="10" cols="50" required><%=quizDescription%></textarea>
			<br> 
			<input type="checkbox" name="canPractice" value="true" <%=canPractice%>>Allow practice mode<br> 
			<input type="checkbox" name="isRandom" value="true" <%=isRandom%>>Automatically randomized question order<br> 
			<input type="checkbox" name="isOnePage" value="true" <%=isOnePage%>>Displays in one page<br> 
			<input type="checkbox" name="isImmCorrection" value="true" <%=isImmCorrection%>>Allow immediate Correction if Multi-Page mode
			enabled<br>
		</p>

		<h2>Questions:</h2>
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
		</select> <input type="button" value="Add New Question" onclick="addQuestion();"><br> 
		<input type="hidden" name="max_num" value="0" id="max_num">
		<input type="submit" value="Submit Quiz">
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
		<input type="button" value="Delete" onclick="deleteQuestion(this);">
	</div>
	<%
		}
	%>
</body>
</html>