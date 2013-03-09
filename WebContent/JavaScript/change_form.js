
function checkScore() {
	val = document.forms[0].maxScore.value;
	
	if (isNaN (val-0) || val == null || val =="") {
		 alert("score should be an integer!");
		 document.forms[0].maxScore.focus( );
		 return false;
	}
	return true;
}

var numAnswer = 1;
var numChoice = 1;

function addAnswer() {
	var tempDiv = document.getElementById("form_input");
	var newPara = document.createElement("p");
	var newInput = document.createElement("input");
	newInput.type == "text";
	newInput.name = "Answer" + numAnswer;

	++numAnswer;
	newPara.innerHTML = newInput.name;
	tempDiv.appendChild(newPara);
	newPara.appendChild(newInput);
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
}