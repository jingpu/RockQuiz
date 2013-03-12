function hightlight(needle) {
	var text = document.getElementById("search_result").innerHTML;
	var match = new RegExp(needle, "ig");     
	var highlightText = "<div style=\"color: red; display: inline; font-weight: bold;\">" + needle + "</div>";
	var replaced = text.replace(match, highlightText);
	document.getElementById("search_result").innerHTML = replaced;
}