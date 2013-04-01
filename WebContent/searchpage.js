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
	}
	if(type.search("a") == -1){
		var tagset = document.getElementsByName("tagset");
		var i = 0;
		while(i < tagset.length){
			var text = tagset[i].innerHTML;
			var match = new RegExp(needle, "ig"); 
			var replaced = text.replace(match, "<div style='color: red; display: inline;'>$&</div>");
			document.getElementsByName("tagset")[i].innerHTML = replaced;
			i++;
		}
	}
	if(type.search("c") == -1 && type.search("g") == -1 && type.search("a") == -1){
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
