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
$(document).ready(function() {
	$('#quizName').change(function() {
		$.ajax({
			type: "GET",
			url: "CheckQuizNameServlet",
			data: {quizName: $('#quizName').val()},
			dataType: "text",
			success: function(data)
			{
				if(data.indexOf("ok") > -1){
					$('#quizNameStatus').css("color", "green");
					$('#quizNameStatus').html("ok");
				}else{
					$('#quizNameStatus').css("color", "red");
					$('#quizNameStatus').html("<b>name already exists</b>");
					$('#quizName').focus();
				}
			}
		});
	});
});


function addQuestion() {
		var questionType = document.getElementById("questionTypeList").value;
		var questions = document.getElementById("questions");
		var qr = document.getElementById(questionType + "_template");
		var question = qr.cloneNode(true);
		question.removeAttribute("hidden");
		question.id = questionType;
		addSuffix(question);
		questions.appendChild(question);
		incMaxNum();
	}
	
	function addSuffix(question) {
		var maxNum = document.getElementById("max_num");
		var curIndex = parseInt(maxNum.value);
		question.id = question.id + "_" + curIndex;
		var elements = question.querySelectorAll("input, textarea, div");
		for ( var i = 0; i < elements.length; i++) {
			var e = elements[i];
			// add suffix to name
			if (e.hasAttribute("name")) {
				var name = e.name + "_" + curIndex;
				e.name = name;
			}
		}
	}

	function incMaxNum() {
		var maxNum = document.getElementById("max_num");
		var curIndex = parseInt(maxNum.value);
		maxNum.value = curIndex + 1;
	}

	function deleteQuestion(button) {
		var question = button.parentNode;
		var questions = document.getElementById("questions");
		questions.removeChild(question);
	}


	function enterNewCategory(){
	var new_category_box = document.getElementById("new_category_box");
	var existing_categories_box = document.getElementById("existing_categories_box");
	existing_categories_box.disabled = true;
	new_category_box.disabled = false;
}


	function chooseExistingCategory() {
		var new_category_box = document.getElementById("new_category_box");
		var existing_categories_box = document
				.getElementById("existing_categories_box");
		new_category_box.disabled = true;
		existing_categories_box.disabled = false;
	}


	function enterNewCategory() {
		var new_category_box = document.getElementById("new_category_box");
		var existing_categories_box = document
				.getElementById("existing_categories_box");
		existing_categories_box.disabled = true;
		new_category_box.disabled = false;
	}
	
	function changeOnePage(){
		if(document.getElementById("OnePage").checked == true){
			document.getElementById("isImmCorrection").disabled = true;
			document.getElementById("isImmCorrection").checked = false;
			//Hide class time_limit_div, disable and reset time_limit
			var elements = document.getElementsByClassName("time_limit_div");
			for ( var i = 0; i < elements.length; i++) 
				elements[i].hidden = true;
			var elements = document.getElementsByClassName("time_limit");
			for ( var i = 0; i < elements.length; i++){
				elements[i].disable = true;
				elements[i].value = "0";
			}
		} else {
			document.getElementById("isImmCorrection").disabled = false;
			//show class time_limit_div, enable time_limit
			var elements = document.getElementsByClassName("time_limit_div");
			for ( var i = 0; i < elements.length; i++) 
				elements[i].hidden = false;
			var elements = document.getElementsByClassName("time_limit");
			for ( var i = 0; i < elements.length; i++)
				elements[i].disable = false;
		}
	}
	function changeImmCorr(){
		if(document.getElementById("isImmCorrection").checked == true){
			document.getElementById("OnePage").disabled = true;
		}else{
			document.getElementById("OnePage").disabled = false;
		}
	}
