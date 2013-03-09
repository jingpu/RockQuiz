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
	<h1>Create Quiz</h1>
	<form action="QuestionCreationServlet" method="post">
		<h2>Quiz Information</h2>
		<p>
			Quiz Name: <input type="text" name="quizName" required><br>
			Tags: <input type="text" name="tagString"><br> Quiz
			Description:<br>
			<textarea name="quizDescription" rows="10" cols="50" required></textarea>
			<br> <input type="checkbox" name="canPractice" value="true">Allow
			practice mode<br> <input type="checkbox" name="isRandom"
				value="true">Automatically randomized question order<br>
			<input type="checkbox" name="isOnePage" value="true">Displays
			in one page<br> <input type="checkbox" name="isImmCorrection"
				value="true">Allow immediate Correction if Multi-Page mode
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