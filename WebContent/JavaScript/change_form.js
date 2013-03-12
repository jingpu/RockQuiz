$(document).ready(function() {
	var timeLimit = $("#time_limit").val();
	if (timeLimit != "" && timeLimit != null && timeLimit != 0) {
		setTimeout(function(){
			alert("Your time is out, please move forward to next question!");
			submitForm();
			},timeLimit * 1000);	
	}
//	setTimeout(document.write(timeLimit), 5000);
});

function submitForm() {
	document.forms[0].submit();
}

function checkScore() {
	var val = document.forms[0].maxScore.value;
	
	if (isNaN (val-0) || val == null || val =="") {
		 alert("score should be an integer!");
		 document.forms[0].maxScore.focus( );
		 return false;
	}
	return true;
}

function containsBlank() {
	var str = document.FIB_form.questionDescription.value;
	if (str == null || str == "" || str.indexOf("#blank#") == -1) {
		document.FIB_form.questionDescription.focus( );
		 return false;
	}
	return true;
}

function checkBlank() {
	if (!checkScore()) return false;
	if (!containsBlank()) {
		 alert("The question description must contains a #blank#");
		return false;
	}
	return true;
}

function addBlank() {
	if (containsBlank()) {
		alert("One question can contain only one blank");
		return;
	}
	document.FIB_form.questionDescription.value += "#blank#";
}


var numAnswer = 1;
function addAnswer() {
	var tempDiv = document.getElementById("form_input");
	var newPara = document.createElement("p");
	var newInput = document.createElement("input");
	newInput.type = "text";
	newInput.name = "answer" + numAnswer;

	newPara.innerHTML = "Answer" + numAnswer;
	tempDiv.appendChild(newPara);
	newPara.appendChild(newInput);
	++numAnswer;
	document.getElementById('numAnswers').value = numAnswer;
}

function deleteAnswer() {
	var parent = document.getElementById("form_input");
	if (numAnswer > 1) {
		parent.removeChild(parent.lastChild);
		--numAnswer;
	} else {
		alert("There must be at least ONE answer");
	}
	document.getElementById('numAnswers').value = numAnswer;
}

/**
 * function for multi-choice
 */
var numMC = 4; //num multi-choice 

function addChoice() {
	var tempDiv = document.getElementById("multi_choice");
	//<p>
	var newPara = document.createElement("p");
	//<input>
	var newInput = document.createElement("input");
	newInput.type = "text";
	newInput.name = "choice" + numMC;
	//<radio>
	var newRadio = document.createElement("input");
	newRadio.type = "radio";
	newRadio.name = "answer";
	newRadio.value = "choice" + numMC;

	newPara.innerHTML = "Choice" + numMC + ": ";
	newPara.appendChild(newInput);
	newPara.appendChild(newRadio);
	tempDiv.appendChild(newPara);

	++numMC;
	document.getElementById('numChoices').value = numMC;
}

function deleteChoice() {
	var parent = document.getElementById("multi_choice");
	document.getElementById('numChoices').value = numMC;
	if (numMC > 3) {
		parent.removeChild(parent.lastChild);
		--numMC;
	} else {
		alert("There must be at least THREE choice options");
	}
	document.getElementById('numChoices').value = numMC;
}



/**
 * Function for multi-choice-multi-answer
 */
var numMCMA = 4; //num multi-choice-multi-answer
var numAnswerMCMA = 0;

function addMCMAChoice() {
	var tempDiv = document.getElementById("MCMA");
	//<p>
	var newPara = document.createElement("p");
	//<input>
	var newInput = document.createElement("input");
	newInput.type = "text";
	newInput.name = "choice" + numMC;
	//<radio>
	var newCkbox = document.createElement("input");
	newCkbox.type = "checkbox";
	newCkbox.name = "answer";
	newCkbox.value = "choice" + numMCMA;

	newPara.innerHTML = "Choice" + numMCMA + ": ";
	newPara.appendChild(newInput);
	newPara.appendChild(newCkbox);
	tempDiv.appendChild(newPara);

	++numMCMA;
	document.getElementById('numChoices').value = numMCMA;
}

function deleteMCMAChoice() {
	var parent = document.getElementById("MCMA");
	document.getElementById('numChoices').value = numMCMA;
	if (numMC > 3) {
		parent.removeChild(parent.lastChild);
		--numMCMA;
	} else {
		alert("There must be at least THREE choice options");
	}
	document.getElementById('numChoices').value = numMCMA;
}