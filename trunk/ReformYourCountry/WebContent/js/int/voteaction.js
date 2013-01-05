// Voting on an action.
function clickVoteButton(item){
	
	if(idUser.length>0) {  // A user is logged in
		$("#voted").text($(item).text());
		var voteValue =$(item).attr('id');
		var request = $.ajax({
			url: "/ajax/vote",
			type: "POST",
			data: "vote="+ voteValue+"&actionId="+$("#idAction").attr('value'),
			dataType: "html"
		});

		request.done(function(data) {
			$("#voteContainer").html(data);
			updateGraph();
		});

		request.fail(function(jqXHR, textStatus) {
			$("#voted").text("Erreur lors du vote : "+textStatus);
		});
		
	} else { //Anonymous visitor
		$(item).CreateBubblePopup({ 
	           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
	               'opacity':0.9
	           },
	           tail: {align:'center', hidden: false},
	           selectable :true,				    	
	           innerHtml: 'Pour voter, veuillez '
	        	   +'<a class="login" style="cursor:pointer;" href="/login">vous connecter</a>'
	       }); 	 
	}
}



//////////////////////////////////// Graph code ///////////////////////////////


function displayGraph() {
	var data = getData();
	var percentData = getPercentData(data);
	var colors =["#6585b3","#6585b3","#3a567e","#111c32","#111c32",];  // Blue ... Black
	// Call the D3.JS code to create the chart
	var rects = chart.selectAll('rect').data(data)
				    .enter().append('rect')
				    .attr("stroke", "black")
				    .attr("fill", function(d, i){return colors[i];})
				    .attr("x", function(d, i) { return 18+(106 * i); })
				    .attr("y", function(d, i){ return 130;})
				    .attr("width",  "48")
				    .attr("height", function(d, i){ return 0;});
	// Progressive bar rise
	rects.transition()
		.attr("y", function(d, i){ return 130 - percentData[i];})
		.attr("height", function(d, i){ return percentData[i];})
		.duration(1000);
	// Numbers on top of bars.
	var texts = chart.selectAll('text').data(data)
		.enter().append('text')
		.attr("x", function(d, i) { return (106 * i)+42; })
		.attr("y", function(d, i){ return 130;})
		.attr("dx", ".25em")
		.attr("dy",-20)
		.attr("text-anchor","end")
		.text(String);
    // Numbers raise up together with the bars.
	texts.transition()
		.attr("y", function(d, i){ return (130 - percentData[i])+15;})
		.duration(1000);
}

// Some data changed (the user just votes) and we update the graph with new data. It will move from it's current position.
function updateGraph(){
	var data = getData();
	var percentData = getPercentData(data);
	var rects = chart.selectAll('rect').data(data);
	rects.transition()
		.attr("y", function(d, i){ return 130 - percentData[i];})
		.attr("height", function(d, i){ return percentData[i];})
		.duration(1000);
	var texts = chart.selectAll('text').data(data)
	    .attr("text-anchor","end")
	    .text(String);
    texts.transition()
	    .attr("y", function(d, i){ return (130 - percentData[i])+15;})
	    .duration(1000);
}

// Transforms absolute vote numbers into % (percentage of the max height of the chart).
function getPercentData(data){
	var total = 0;
	for (var i=0;i<=4;i++){
		total += parseInt(data[i]);
	}
	var percentData =[(data[0]*100)/total,(data[1]*100)/total,(data[2]*100)/total,(data[3]*100)/total,(data[4]*100)/total];
	return percentData;
}

function getData() {
	//// The date is put by the JSP into hidden divs
	var data = new Array();
	data[0] = $("#result-2").text();
	data[1] = $("#result-1").text();
	data[2] = $("#result0").text();
	data[3] = $("#result1").text();
	data[4] = $("#result2").text();
	return data;
}
var chart;
$(document).ready(function(){
	chart = d3.select("#voteGraph").append("svg").attr("width", "500").attr("height", "130");
	displayGraph();
});

