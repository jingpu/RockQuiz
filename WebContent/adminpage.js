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

function checkTime(){
	var t1 = document.getElementById('fromTime').value;
	var t2 = document.getElementById('toTime').value;
	if(t1.search(/[\d]{4}-[\d]{2}-[\d]{2}/g) == -1 ||
			t2.search(/[\d]{4}-[\d]{2}-[\d]{2}/g) == -1){
		document.getElementById('errorNotice').innerHTML = '<p>Time format incorrect.</p>';
		return false;
	}
	return true;
}