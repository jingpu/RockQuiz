function AddElement() {
	var blank = document.getElementById('msg');
	if(document.all('toUser') != null) 
		return;
	var newline1 = document.createElement('br');
	newline1.id = 'br1';
	blank.appendChild(newline1);

	var newInput1 = document.createElement('input');
	newInput1.type = 'text';
	newInput1.name = 'toUser';
	newInput1.form = 'letter';
	newInput1.placeholder = 'User name';
	blank.appendChild(newInput1);

	var newline2 = document.createElement('br');
	newline2.id = 'br2';
	blank.appendChild(newline2);

	var newInput2 = document.createElement('textarea');
	newInput2.rows = '8';
	newInput2.cols = '30';
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
		var e3 = document.getElementById('br1');
		e3.parentNode.removeChild(e3);
		var e4 = document.getElementById('br2');
		e4.parentNode.removeChild(e4);
		var e5 = document.forms['letter'].elements['send'];
		e5.parentNode.removeChild(e5);
    }, 50);
	alert('Challenge Message Sent');
	return false;
}    

function NewAchievement(){
	
}