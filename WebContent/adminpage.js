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
function checkAnn(){
	var announce = document.forms[0].content.value;
	if(announce == "") {
		document.getElementById('errorNotice').innerHTML = '<h5>Announcement cannot be empty.</h5>';
		return false;
	}
	return true;
}

function checkDeletion(){
	var name = document.forms[1].id.value;
	if(name == "") {
		document.getElementById('errorNotice').innerHTML = '<h5>User name cannot be empty.</h5>';
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
		document.getElementById('errorNotice').innerHTML = '<h5>User name cannot be empty.</h5>';
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
		document.getElementById('errorNotice').innerHTML = '<h5>Quiz name cannot be empty.</h5>';
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
		document.getElementById('errorNotice').innerHTML = '<h5>Time format incorrect.</h5>';
		return false;
	}
	return true;
}
