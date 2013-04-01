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
var elemCount = 0;
function AddExistingCategory(){
	var container = document.getElementById('new_category_type');
	var newtext = document.getElementById('existing_categories_box').value;
	var addedfields = document.getElementsByName('favor');
	for(var i = 0; i < addedfields.length; i++){
		if(addedfields[i].value.search(newtext, "i") != (-1)){
			return;
		}
	}
	elemCount++;
	var checkbox = document.createElement('input');
	checkbox.type = 'checkbox';
	checkbox.name ='favor';
	checkbox.value = newtext;
	checkbox.checked = true;
	checkbox.id = 'box'+ elemCount;
	var label = document.createElement('label');
	label.htmlFor = checkbox.id;
	label.appendChild(document.createTextNode(newtext + ' '));
	var newline = document.createElement('br');
	container.appendChild(checkbox);
	container.appendChild(label);
	container.appendChild(newline);
}

function AddNewCategory(){
	var textfield = document.getElementById('new_category_box');
	if(textfield.value == null) return;
	var addedfields = document.getElementsByName('favor');
	for(var i = 0; i < addedfields.length; i++){
		if(addedfields[i].value.search(newtext, "i") != (-1)){
			return;
		}
	}
	elemCount++;
	var container = document.getElementById('new_category_type');
	var checkbox = document.createElement('input');
	checkbox.type = 'checkbox';
	checkbox.name ='favor';
	checkbox.value = textfield.value;
	checkbox.checked = true;
	checkbox.id = 'box'+ elemCount;
	var label = document.createElement('label')
	label.htmlFor = checkbox.id;
	label.appendChild(document.createTextNode(checkbox.value + ' '));
	var newline = document.createElement('br');
	container.appendChild(checkbox);
	container.appendChild(label);
	container.appendChild(newline);
}

function judge() {
	// email
	var pos1 = document.forms[0].email.value.indexOf("@");
	var pos2 = document.forms[0].email.value.indexOf(".");
	if (pos1 == -1 && pos2 == -1) {
		alert("Please fill in the correct email address!");
		document.forms[0].email.focus();
		document.forms[0].email.select();
		return false;
	}
	return true;
}
