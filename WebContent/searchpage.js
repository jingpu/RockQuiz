function highlight(needle,type) {
	if(needle.length == 0) return;
	if(type.search("g") == -1){
		var creatorset = document.getElementsByName("creatorset");
		var i = 0;
		while(i < creatorset.length){
			var text = creatorset[i].innerHTML;
			var match = new RegExp(needle, "ig"); 
			var replaced = text.replace(match, "<div style='color: red; display: inline;'>$&</div>");
			document.getElementsByName("creatorset")[i].innerHTML = replaced;
			i++;
		}
	}
	if(type.search("c") == -1){
		var categoryset = document.getElementsByName("categoryset");
		var i = 0;
		while(i < categoryset.length){
			var text = categoryset[i].innerHTML;
			var match = new RegExp(needle, "ig"); 
			var replaced = text.replace(match, "<div style='color: red; display: inline;'>$&</div>");
			document.getElementsByName("categoryset")[i].innerHTML = replaced;
			i++;
		}
		return;
	}
	if(type.search("c") == -1 && type.search("g") == -1){
		var set = document.getElementsByName("resultset");
		var i = 0;
		while(i < set.length){
			var text = set[i].innerHTML;
			var match = new RegExp(needle, "ig"); 
			var replaced = text.replace(match, "<div style='color: red; display: inline;'>$&</div>");
			document.getElementsByName("resultset")[i].innerHTML = replaced;
			i++;
		}
	}
}