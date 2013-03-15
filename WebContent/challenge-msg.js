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
