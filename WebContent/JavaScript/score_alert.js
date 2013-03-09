
function checkScore() {
	val = document.forms[0].maxScore.value;
	
	if (isNaN (val-0) || val == null) {
		 alert("score should be an integer!");
		 document.forms[0].maxScore.focus( );
		 return false;
	}
	return true;
}