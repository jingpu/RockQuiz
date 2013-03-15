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
    
//    $("#clear").click(function(){
//         $("#match").match("clear");
//    });
});
