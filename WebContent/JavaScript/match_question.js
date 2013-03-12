$(document).ready(function() {
	//var answer0 = $("#answer0").val();
	
    $('#match').match({
        radius: 7,
        height:200,
        responses: [
            {id: 0,text: $("#choice0").text()},
            {id: 1,text: $("#choice1").text()},
            {id: 2,text: $("#choice2").text()},
            {id: 3,text: $("#choice3").text()},],
        
        options: [
            {id: 0,text: $("#answer0").text()},
            {id: 1,text: $("#answer1").text()},
            {id: 2,text: $("#answer2").text()},
            {id: 3,text: $("#answer3").text()},]
    });

    //match2 is for preload condition
//     $("#match2").match({
//        radius: 7,
//        height:200,
//        responses: [{id: 0,text: "jQuery"},
//            {id: 1,text: "REST"},
//            {id: 2,text: "Knockout.js"},
//            {id: 3,text: "RavenDB"},
//            {id: 4,text: "Backbone.js"},
//            {id: 5,text: "Asp.mvc",}],
//        options: [
//            {id: 1,text: "Server",},
//            {id: 2,text: "Client",},
//            {id: 3,text: "Database"}],
//          joins: [
//            {question:{id:1},response:{id:5}},
//            {question:{id:1},response:{id:1}},
//            {question:{id:2},response:{id:0}},
//            {question:{id:2},response:{id:4}},
//            {question:{id:3},response:{id:3}}
//        ] 
//    });

    $("#vals").click(function(){
        var res = $("#match").match("getValues");
        $("#results").html("");
        for (var a = 0; a < res.length; a++) {
            var item = res[a];
            $("#results").append("<div>'" + item.question.text + "' matched with '" + item.response.text + "'</div>");
        }
    });
    $("#clear").click(function(){
         $("#match").match("clear");
    });
});
