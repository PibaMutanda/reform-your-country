//$("div#voteContainer > div").click(function(){
function clicked(item){
	if(idUser.length>0){
		$("#voted").text($(item).text());
		var voteValue =$(item).attr('id');
		var request = $.ajax({
			url: "/ajax/vote",
			type: "POST",
			data: "vote="+ voteValue+"&action="+$("#idAction").attr('value')+"&idVote="+idVote,
			dataType: "html"
		});

		request.done(function(data) {
			$("#voteContainer").html(data);
			updateGraph();
		});

		request.fail(function(jqXHR, textStatus) {
			$("#voted").text("Erreur lors du vote : "+textStatus);
		});
	}else{
		
	     
		$(item).CreateBubblePopup({ 
	           innerHtmlStyle: {  // give css property to the inner div of the popup	    	   
	               'opacity':0.9
	           },
	           tail: {align:'center', hidden: false},
	           selectable :true,				    	
	           innerHtml: 'Pour voter veuillez vous logger : '
	        	   +'<a class="login" style="cursor:pointer;" href="/login">Connexion</a>'
	       }); 	 

	}
//});
}
///////Graph code
function displayGraph() {
	
	var data = new Array();
	data[0] = $("#result-2").text();
	data[1] = $("#result-1").text();
	data[2] = $("#result0").text();
	data[3] = $("#result1").text();
	data[4] = $("#result2").text();
	var total = 0;
	for (var i=0;i<=4;i++){
		total += parseInt(data[i]);
	}
	
	var percentData =[(data[0]*100)/total,(data[1]*100)/total,(data[2]*100)/total,(data[3]*100)/total,(data[4]*100)/total];
	var colors =["#005fb1","#c9e2ff","#ffffff","#ff9999","#bb1100"];
	var rects = chart.selectAll('rect').data(data)
				    .enter().append('rect')
				    .attr("stroke", "black")
				    .attr("fill", function(d, i){return colors[i];})
				    .attr("x", function(d, i) { return 97 * i; })
				    .attr("y", function(d, i){ return 130;})
				    .attr("width",  "90.5")
				    .attr("height", function(d, i){ return 0;});
	rects.transition()
		.attr("y", function(d, i){ return 130 - percentData[i];})
		.attr("height", function(d, i){ return percentData[i];})
		.duration(1000);
	var texts = chart.selectAll('text').data(data)
		.enter().append('text')
		.attr("x", function(d, i) { return (95 * i)+47; })
		.attr("y", function(d, i){ return 130;})
		.attr("dx", ".25em")
		.attr("dy",-20)
		.attr("text-anchor","end")
		.text(String);
		
	texts.transition()
		.attr("y", function(d, i){ return (130 - percentData[i])+15;})
		.duration(1000);
}
function updateGraph(){
	var data = new Array();
	data[0] = $("#result-2").text();
	data[1] = $("#result-1").text();
	data[2] = $("#result0").text();
	data[3] = $("#result1").text();
	data[4] = $("#result2").text();
	var total = 0;
	for (var i=0;i<=4;i++){
		total += parseInt(data[i]);
	}
	var percentData =[(data[0]*100)/total,(data[1]*100)/total,(data[2]*100)/total,(data[3]*100)/total,(data[4]*100)/total];
	var rects = chart.selectAll('rect').data(data);
//				    .attr("y", function(d, i){ return 130;})
//				    .attr("height", function(d, i){ return 0;});
	rects.transition()
		.attr("y", function(d, i){ return 130 - percentData[i];})
		.attr("height", function(d, i){ return percentData[i];})
		.duration(1000);
//	var texts = chart.selectAll('text').data(data)
//					.transition()
//					.attr("y", function(d, i){ return (130 - percentData[i])+15;})
//					.duration(1000);
	var texts = chart.selectAll('text').data(data)
	.attr("text-anchor","end")
	.text(String);
	
texts.transition()
	.attr("y", function(d, i){ return (130 - percentData[i])+15;})
	.duration(1000);
}
$(document).ready(displayGraph);
//$("div#voteContainer > div").click(function(){updateGraph();});
//$("div#voteContainer > div").click(function(){console.log("click !");});
