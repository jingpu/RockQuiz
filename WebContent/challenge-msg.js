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
function AddElement() {
	var blank = document.getElementById('msg');
	if(document.all('toUser') != null) 
		return;
	
	var newInput1 = document.createElement('input');
	newInput1.type = 'text';
	newInput1.name = 'toUser';
	newInput1.form = 'letter';
	newInput1.placeholder = 'User name';
	newInput1.size = '17';
	blank.appendChild(newInput1);

	var newline2 = document.createElement('br');
	newline2.id = 'br2';
	blank.appendChild(newline2);

	var newInput2 = document.createElement('textarea');
	newInput2.rows = '5';
	newInput2.cols = '20';
	newInput2.name = 'content';
	newInput2.form = 'letter';
	newInput2.placeholder = 'Composing message here';
	blank.appendChild(newInput2);

	var newInput3 = document.createElement('input');
	newInput3.type = 'button';
	newInput3.name = 'send';
	newInput3.value = 'Send';
	newInput3.form = 'letter';
	newInput3.addEventListener('click', DelElement, false);
	blank.appendChild(newInput3);
}

function DelElement(){  
	document.forms['letter'].submit();
	window.setTimeout(function() {
		var e1 = document.forms['letter'].elements['toUser'];
		e1.parentNode.removeChild(e1);
		var e2 = document.forms['letter'].elements['content'];
		e2.parentNode.removeChild(e2);
		var e4 = document.getElementById('br2');
		e4.parentNode.removeChild(e4);
		var e5 = document.forms['letter'].elements['send'];
		e5.parentNode.removeChild(e5);
	}, 50);
	return false;
}

function Auto_both(){
	alert('New Achievements - <Quiz Machine> and <I am the Greatest>');
}

function Auto_1(){
	alert('New achievement - <Quiz Machine>');
}

function Auto_2(){
	alert('New achievement - <I am the Greatest>');
}
