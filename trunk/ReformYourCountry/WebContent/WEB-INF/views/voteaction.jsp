<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/int/voteaction.js"></script>
<script type="text/javascript">
   var idVote = "${vote.id}";
   var idUser = "${current.user.id}";
</script>

<%-- Data for the graph (data taken by our javascript) --%>
<div id="voteData" style="display:none">
			<div id="result-2">${resultNumbers.get(0)}</div>
			<div id="result-1">${resultNumbers.get(1)}</div>
			<div id="result0">${resultNumbers.get(2)}</div>
			<div id="result1">${resultNumbers.get(3)}</div>
			<div id="result2">${resultNumbers.get(4)}</div>
		</div>
		
<%-- vote buttons and % --%>		
<div id="voteContent" style=" width: 480px; margin-left: 165px; margin-right:165px; font-size:.85em;">
			<div id="voteButtons">
				<div id="-2" onclick="clicked(this);"
						class="vote <c:if test="${vote.value eq -2}">selected
					                </c:if> v-2">
							totalement<br/> pour
				</div>
				<div id="-1" onclick="clicked(this);"
						class="vote <c:if test="${vote.value eq -1}">selected
					                </c:if> v-1">
							partiellement pour
				</div>
				<div id="0" onclick="clicked(this);"
						class="vote <c:if test="${vote.value eq 0}">selected
					                </c:if> v0">
							<br/>indécis 
							
				</div>
				<div id="1" onclick="clicked(this);"
						class="vote <c:if test="${vote.value eq 1}">selected
					                </c:if> v1">
							partiellement contre
				</div>
				<div id="2" onclick="clicked(this);"
						class="vote <c:if test="${vote.value eq 2}">selected
					                </c:if> v2">
							totalement contre
				</div>
			</div>
			<div style="width: 50%; height: 20px; text-align: left; float: left;">pour ${positiveWeightedPercentage}% (${positiveAbsolutePercentage}% des votants)      
</div><div style="width: 50%; height: 20px; float:right ; text-align: right;">(${negativeAbsolutePercentage}% des votants) ${negativeWeightedPercentage}% contre</div>
</div>
	
