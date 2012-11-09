<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <link href="css/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<!-- <script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script> -->
<script type="text/javascript" src="js/int/voteaction.js"></script>
<script src="http://d3js.org/d3.v2.js"></script>
<script type="text/javascript">
   var idVote = "${vote.id}";
   var idUser = "${current.user.id}";
</script>

<div id="voteGraph" style=" width: 500px; margin-left: 150px;">
</div>
<div id="voteContent" style=" width: 500px; margin-left: 150px;">
		
		<c:forTokens  items="-2,-1,0,1,2" delims="," var="i">
			<div id="${i}" onmouseout="unfocused(this);" onmouseover="focused(this);"
			onclick="clicked(this);"
					class="vote <c:if test="${vote.value eq i}">selected
				                </c:if> v${i}">
						${i}
			</div>
		</c:forTokens>
</div>
	
<div id="voted"	style="text-align: center; width: 500px; height: 20px;">
		<c:if test="${ vote != null}"> <%-- User has voted => can see the result. --%>
			<div id ="total">Total:${resultVote}</div>
		</c:if>
		<div id="log"  style = "display:none;">
			Pour voter veuillez vous logger : <a class="login" style="cursor:pointer;">Connexion</a>
		</div>
</div>
<script type="text/javascript">
var chart = d3.select("#voteGraph").append("svg").attr("width", "500").attr("height", "100");
var data = new Array();
data[0] = ${resultNumbers.get(0)};
data[1] = ${resultNumbers.get(1)};
data[2] = ${resultNumbers.get(2)};
data[3] = ${resultNumbers.get(3)};
data[4] = ${resultNumbers.get(4)};
var total = 0;
for (i=0;i<=4;i++){
	total += data[i];
}
var percentData =[(data[0]*100)/total,(data[1]*100)/total,(data[2]*100)/total,(data[3]*100)/total,(data[4]*100)/total];
var colors =["#005fb1","#c9e2ff","#ffffff","#ff9999","#bb1100"];
var rects = chart.selectAll('rect').data(data)
			    .enter().append('rect')
			    .attr("stroke", "black")
			    .attr("fill", function(d, i){return colors[i]})
			    .attr("x", function(d, i) { return 95 * i; })
			    .attr("y", function(d, i){ return 100 - percentData[i];})
			    .attr("width",  "90")
			    .attr("height", function(d, i){ return percentData[i];});
	chart.selectAll('text').data(data)
		.enter().append('text')
		.attr("x", function(d, i) { return (95 * i)+47; })
		.attr("y", function(d, i){ return (100 - percentData[i])+15;})
		.attr("dx", ".25em")
		.attr("dy",5)
		.attr("text-anchor","end")
		.text(String);
		
</script>