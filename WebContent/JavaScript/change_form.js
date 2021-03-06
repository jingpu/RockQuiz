/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
$(document).ready(function() { //unload in javascript
	var timeLimit = $("#time_limit").val();
	if (timeLimit != "" && timeLimit != null && timeLimit != 0) {
		setTimeout(function(){
			alert("Your time is out, please move forward to next question!");
			submitForm();
			},timeLimit * 1000);	
	}
	
});


function validateForm() {
	if (!checkScore()) return false;
	if (!checkBlank()) return false;
	if (!checkTimeLimit()) return false;
	if (!checkRadioChkBox()) return false;
	return true;
}


function checkRadioChkBox() {
	var maxNum = document.getElementById("max_num").value;
	for (var i = 0; i < maxNum; i++) {
		var answer = document.getElementsByName("answer" + "_" + i);
		var flag = false;
		for (var j = 0; j < answer.length; j++) {
			if((answer[j].type != "radio" && answer[j].type != "checkbox") || answer[j].checked) {
				flag = true;
				break;
			}
		}
		if (answer.length == 0) 
			flag = true;
		if (!flag) {
			alert("You should at least assign one choice as answer!");
			return false;
		}
	}
	return true;
}



//used for multi-page read mode
function submitForm() {
	document.forms[0].submit();
}

function checkTimeLimit() {
	var elements = document.forms[0].getElementsByClassName("time_limit");
	for (var i = 0; i < elements.length; i++){
		var val = elements[i].value;
		if (isNaN (val-0) || val == null || val =="") {
			 alert("Time should be an integer!");
			 elements[i].focus();  //val is an element type
			 return false;
		}
	}
	return true;
}



//For creation check
function checkScore() {
	var elements = document.forms[0].getElementsByClassName("max_score");
	for (var i = 0; i < elements.length; i++){
		var val = elements[i].value;
		if (isNaN (val-0) || val == null || val =="") {
			 alert("score should be an integer!");
			 elements[i].focus();  //val is an element type
			 return false;
		}
	}
	return true;
}


function checkBlank() {
	var elements = document.forms[0].getElementsByClassName("FIB");

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
	questionDescription.value += " #blank# ";
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


function addOptionSuffix(newChoiceDiv, suffix) {
	var elements = newChoiceDiv.querySelectorAll("input");
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		
		// add suffix to name
		if (e.hasAttribute("name")) {
			var name = e.name;
			if(name.indexOf("choice") > -1){
				e.name = name.replace("choice", "choice" + (suffix-1));
				if (e.className == "requiredField") {
					e.required=true;
				}
			}			
		}
		// add suffix to value
		if (e.hasAttribute("value")) {
			var val = e.value;
			if(val.indexOf("choice") > -1){
				e.value = val.replace("choice", "choice" + (suffix-1));
			}			
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
  
    //-1 because starts from 0
    newChoice.firstChild.innerHTML = "Choice" + (numMC - 1) + ": ";
    addOptionSuffix(newChoice, numMC);
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


function addAnswerSuffix(newAnswer, suffix) {
	var elements = newAnswer.querySelectorAll("input");
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		// add suffix to name
		if (e.hasAttribute("name")) {
			var name = e.name;
			if(name.indexOf("answer") > -1){
				e.name = name.replace("answer", "answer" + (suffix-1));
				if (e.className == "requiredField") {
					e.required=true;
				}
			}			
		}
	}
}


/**
 * Function for multi-answer questions
 */
function addAnswer(button) {
	var parentDiv = button.parentNode;
	var answerDiv = getElem(parentDiv, "answers");
	//update numMA
	var numMA = parentDiv.lastChild.value - 0 + 1;
	parentDiv.lastChild.value = numMA;
	
	var answer = getElem(parentDiv, "answer_template");
    
	var newAnswer = answer.cloneNode(true);
    newAnswer.removeAttribute("hidden");
  
    newAnswer.firstChild.innerHTML = "Answer" + (numMA -1) + ": ";
    addAnswerSuffix(newAnswer, numMA);
    answerDiv.appendChild(newAnswer);
}


function deleteAnswer(button) {
	var parentDiv = button.parentNode;
	var answerDiv = getElem(parentDiv, "answers");
	//update numMA
	var numMA = parentDiv.lastChild.value;
	if (numMA > 1) {
		answerDiv.removeChild(answerDiv.lastChild);
		parentDiv.lastChild.value = numMA - 0 - 1;
	} else {
		alert("There must be at least ONE answer");
	}
}


/**
 * Function for multi-choice-multi-answer
 */
function addMCMAChoice(button) {
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMCMA
	var numMCMA = parentDiv.lastChild.value - 0 + 1;
	parentDiv.lastChild.value = numMCMA;
	
	var choice = getElem(parentDiv, "choice_template");
    
	var newChoice = choice.cloneNode(true);
    newChoice.removeAttribute("hidden");
  
    newChoice.firstChild.innerHTML = "Choice" + (numMCMA -1) + ": ";
    addOptionSuffix(newChoice, numMCMA);
    choiceDiv.appendChild(newChoice);

}

function deleteMCMAChoice(button) {
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMC
	var numMCMA = parentDiv.lastChild.value;
	if (numMCMA > 3) {
		choiceDiv.removeChild(choiceDiv.lastChild);
		parentDiv.lastChild.value = numMCMA - 0 - 1;
	} else {
		alert("There must be at least THREE choice options");
	}
}

function addMatchOptionSuffix(newOption, suffix) {
	var elements = newOption.querySelectorAll("input");
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		
		if (e.hasAttribute("name")) {
			var name = e.name;
			if(name.indexOf("choice") > -1){
				e.name = name.replace("choice", "choice" + (suffix-1));
			}
			if(name.indexOf("answer") > -1){
				e.name = name.replace("answer", "answer" + (suffix-1));
			}
		}
	}
}

/**
 * Matching
 */

function addMatchOption(button) {
	
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMatch
	var numMatch = parentDiv.lastChild.value - 0 + 1;
	parentDiv.lastChild.value = numMatch;
	
	var choice = getElem(parentDiv, "choice_template");
    
	var newChoice = choice.cloneNode(true);
    newChoice.removeAttribute("hidden");
  
    newChoice.firstChild.innerHTML = "Choice" + (numMatch - 1) + " & Answer" + (numMatch - 1) +": ";
    addMatchOptionSuffix(newChoice, numMatch);
    choiceDiv.appendChild(newChoice);
	
}


function deleteMatchOption(button) {
	var parentDiv = button.parentNode;
	var choiceDiv = getElem(parentDiv, "choices");
	//update numMC
	var numMatch = parentDiv.lastChild.value;
	if (numMatch > 3) {
		choiceDiv.removeChild(choiceDiv.lastChild);
		parentDiv.lastChild.value = numMatch - 0 - 1;
	} else {
		alert("There must be at least THREE choice options");
	}
}



