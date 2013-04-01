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
    $('#match').match({
        radius: 7,
        height:200,
        
       options: [
           {id: 0,text: $("#cs0").text()},
           {id: 1,text: $("#cs1").text()},
           {id: 2,text: $("#cs2").text()},
           {id: 3,text: $("#cs3").text()},],
        
        responses: [
           {id: 0,text: $("#as0").text()},
           {id: 1,text: $("#as1").text()},
           {id: 2,text: $("#as2").text()},
           {id: 3,text: $("#as3").text()},]
    
    });


    $("#result").click(function(){
        var res = $("#match").match("getValues");
        for (var i = 0; i < res.length; i++) {
            var item = res[i];
            choiceId = "#choice" + i;
            $(choiceId).val(item.response.text);
        }
        for (var i = res.length; i < 4; i++) {
        	  choiceId = "#choice" + i;
        	  $(choiceId).val("");
        }
    });
    
    $("#clear").click(function(){
         $("#match").match("clear");
    });
});
