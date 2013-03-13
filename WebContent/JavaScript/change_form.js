$(document).ready(function() { //unload in javascript
	var timeLimit = $(".time_limit").val();
	if (timeLimit != "" && timeLimit != null && timeLimit != 0) {
		setTimeout(function(){
			alert("Your time is out, please move forward to next question!");
			submitForm();
			},timeLimit * 1000);	
	}

//TODO: this part is still problematic
	$('#submit').click(function () {
	    $(':input:not(:button)').each(function(index, element) {
	        if (element.value === '') {
	            alert("please fill up all fields");
	        }
	    });
	    
	});
    
	
	$('#questionRead').submit(function() {
		$.ajax({
			type: "GET",
			url: "GetScoreServlet",
			data: $("#questionRead").serialize(), 
			success: function(data)
			{
				alert(data);
				submitForm();
			}
		});
		return false;
	});
});

function validateForm() {
	if (!checkScore()) return false;
	if (!checkBlank()) return false;
	return true;
}

//used for multi-page read mode
function submitForm() {
	document.forms[0].submit();
}

//For creation check
function checkScore() {
	var elements = document.getElementByClass("max_score");
	
	for (var i = 0; i < elements.length; i++){
		var val = elements[i];
		if (isNaN (val-0) || val == null || val =="") {
			 alert("score should be an integer!");
			 val.focus();  //val is an element type
			 return false;
		}
	}
	return true;
}


function checkBlank() {
	var elements = document.getElementByClass("FIB");

	for (var i = 0; i < elements.length; i++){
		var elem = elements[i];
		if (!containsBlank(elem)) {
			alert("The question description must contains a #blank#");
			elem.focus();  
			return false;
		}
	}
	return true;
}

/**
 * Called by either addBlank() or checkBlank
 * @param elem
 * @returns {Boolean}
 */
function containsBlank(elem) {
	var str = elem.value;
	if (str == null || str == "" || str.indexOf("#blank#") == -1) {
		elem.focus( );
		return false;
	}
	return true;
}


/**
 * Function exclusively used in Fill-In-Blank Question type
 * @param button
 */
function addBlank(button) {
	var parent = button.parentNode;
	if (containsBlank(parent.firstChild)) {
		alert("One question can contain only one blank");
		return;
	}
	var questionDescription = button.parentNode.firstChild;
	questionDescription.value += "#blank#";
}

/**
 * Get element from a node's children nodes
 * @param parent
 * @param classname
 * @returns
 */
function getElem(parent, classname) {
	var elements = parent.childNodes;
	for (var i = 0; i < elements.length; i++){
		if (elements[i].className == classname)
			return elements[i];
	}
	return null;
}


function addChoiceSuffix(newChoiceDiv, numMC) {
	var elements = newChoiceDiv.querySelectorAll("input");
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		// add suffix to name
		if (e.hasAttribute("name")) {
			if(e.name == "choice")
				e.name += numMC;
		}
		if (e.hasAttribute("value")) {
			if (e.value == "choice")
				e.value += numMC;
		}
	}
}
/**
 * function for multi-choice
 * Too hacky..here
 * lastChild, div, hidden, suffix, numMC, string to num
 */
function addChoice(button) {
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMC
	var numMC = parentDiv.lastChild.value - 0 + 1;
	parentDiv.lastChild.value = numMC;
	
	var choice = getElem(parentDiv, "choice_template");
    
	var newChoice = choice.cloneNode(true);
    newChoice.removeAttribute("hidden");
  
    newChoice.firstChild.innerHTML = "Choice" + numMC + ": ";
    addChoiceSuffix(newChoice, numMC);
    choiceDiv.appendChild(newChoice);
	
}

function deleteChoice(button) {
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMC
	var numMC = parentDiv.lastChild.value;
	if (numMC > 3) {
		choiceDiv.removeChild(choiceDiv.lastChild);
		parentDiv.lastChild.value = numMC - 0 - 1;
	} else {
		alert("There must be at least THREE choice options");
	}
}



/**
 * Function for multi-answer questions
 */
function addAnswer(button) {
	var tempDiv = button.parentNode;
    var newChoice = qr.cloneNode(true);
    newChoice.removeAttribute("hidden");
    newChoice.innerHTML = "Answer" + numAnswer;
	tempDiv.appendChild(newChoice);
	++numAnswer;
	tmpDiv.lastChild.value = numAnswer;
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
	newInput.name = "choice" + numMCMA;
	//<chkbox>
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
	if (numMCMA > 3) {
		parent.removeChild(parent.lastChild);
		--numMCMA;
	} else {
		alert("There must be at least THREE choice options");
	}
	document.getElementById('numChoices').value = numMCMA;
}


/**
 * Matching
 */
var numOption = 4; //num of options of Matching

function addMatchOption() {
	var tempOption = document.getElementById("options");
	var tempResult = document.getElementById("results");
	//<p>
	var paraOption = document.createElement("p");
	var paraResult = document.createElement("p");
	//<input>
	var inputOption = document.createElement("input");
	inputOption.type = "text";
	inputOption.name = "choice" + numOption;
	
	var inputResult = document.createElement("input");
	inputResult.type = "text";
	inputResult.name = "answer" + numOption;
	
	paraOption.innerHTML = "Choice" + numOption + ": ";
	paraResult.innerHTML = "Answer" + numOption + ": ";
	paraOption.appendChild(inputOption);
	paraResult.appendChild(inputResult);
	tempOption.appendChild(paraOption);
	tempResult.appendChild(paraResult);

	++numOption;
	document.getElementById('numOptions').value = numOption;
}


function deleteMatchOption() {
	var tempOption = document.getElementById("options");
	var tempResult = document.getElementById("results");
	
	if (numOption > 3) {
		tempOption.removeChild(tempOption.lastChild);
		tempResult.removeChild(tempResult.lastChild);
		--numOption;
	} else {
		alert("There must be at least THREE choice options");
	}
	document.getElementById('numOptions').value = numOption;
}



