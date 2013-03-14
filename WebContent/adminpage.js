function checkAnn(){
	var announce = document.forms[0].content.value;
	if(announce == "") {
		document.getElementById('errorNotice').innerHTML = '<p>Announcement cannot be empty.</p>';
		return false;
	}
	return true;
}

function checkDeletion(){
	var name = document.forms[1].id.value;
	if(name == "") {
		document.getElementById('errorNotice').innerHTML = '<p>User name cannot be empty.</p>';
		return false;
	}
	var r = confirm("Are you sure to delete " + name + "?");
	if (r) {
		return true;
	}
	return false;
}

function checkAppointment(){
	var name = document.forms[2].id.value;
	if(name == "") {
		document.getElementById('errorNotice').innerHTML = '<p>User name cannot be empty.</p>';
		return false;
	}
	var r = confirm("Are you sure to change " + name + "'s authority?");
	if (r) {
		return true;
	}
	return false;
}

function checkDeleteQuiz() {
	var name = document.forms[3].quiz.value;
	if(name == "") {
		document.getElementById('errorNotice').innerHTML = '<p>Quiz name cannot be empty.</p>';
		return false;
	}
	var e = document.getElementById("quizOp");
	var selected = e.options[e.selectedIndex].value;
	if (selected == '1') {
		return confirm("Are you sure to delete quiz " + name + "?");
	}
	if (selected == '2') {
		return confirm("Are you sure to clear quiz " + name
				+ "'s history'?");
	}
}