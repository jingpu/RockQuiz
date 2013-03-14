$(document).ready(function() {
	//var answer0 = $("#answer0").val();
	
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
       // $("#results").html("");
        for (var i = 0; i < res.length; i++) {
            var item = res[i];
            choiceId = "#choice" + i;
            //answerId = "#answer" + i;
//            $("\"#" + choiceId + "\"").val(item.question.text);
            $(choiceId).val(item.response.text);
           // $(answerId).val(item.response.text);
          //  $("#results").append("<div>'" + item.question.text + "' matched with '" + item.response.text + "'</div>");
        }
    });
    $("#clear").click(function(){
         $("#match").match("clear");
    });
});
