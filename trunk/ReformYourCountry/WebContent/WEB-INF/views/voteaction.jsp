<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Big voting elements for the action page. --%>

<script type="text/javascript">
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
<div style=" width: 480px; margin-left: 165px; margin-right:165px; font-size:.85em;">
			<div id="voteButtons" >
				<div id="2" onclick="clickVoteButton(this);"
						title="totalement pour" class="vote <c:if test="${vote.value eq 2}">selected</c:if> v2">
				</div>
				<div id="1" onclick="clickVoteButton(this);"
						title="partiellement pour" class="vote <c:if test="${vote.value eq 1}">selected</c:if> v1">
				</div>
				<div id="0" onclick="clickVoteButton(this);"
						title="indécis" class="vote <c:if test="${vote.value eq 0}">selected</c:if> v0">
							
				</div>
				<div id="-1" onclick="clickVoteButton(this);"
						title="partiellement contre" class="vote <c:if test="${vote.value eq -1}">selected</c:if> v-1">
				</div>
				<div id="-2" onclick="clickVoteButton(this);"
						title="totalement contre" class="vote <c:if test="${vote.value eq -2}">selected</c:if> v-2" 
					                style="margin-right:0;">  <%-- To override the "vote" class right margin which should not apply to the rightmost button. --%>
				</div>
			</div>
			<div title="Si un vote 'complètement pour' compte deux fois plus qu'un vote 'plutôt pour', alors ${positiveWeightedPercentage}% des votes sont pour. Si on compte le nombre de votants sans pondération, alors ${positiveAbsolutePercentage}% des votants sont pour.">
			   <div style="width: 50%; height: 20px; text-align: left; float: left;">
			      pour ${positiveWeightedPercentage}% (${positiveAbsolutePercentage}% des votants)      
               </div>
               <div style="width: 50%; height: 20px; float:right ; text-align: right;">(${negativeAbsolutePercentage}% des votants) ${negativeWeightedPercentage}% contre</div>
            </div>
</div>
	
