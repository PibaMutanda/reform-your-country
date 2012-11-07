<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/int/voteaction.js">
</script>
<script type="text/javascript">
   var idVote = "${vote.id}";
   var idUser = "${current.user.id}";
</script>

<div id="voteContent" style=" width: 400px; margin-left: 150px;">
		<c:forTokens  items="-2,-1,0,1,2" delims="," var="i">
			<div id="${i}" onmouseout="unfocused(this);" onmouseover="focused(this);"
			onclick="clicked(this);"
					class="vote <c:if test="${vote.value eq i}">selected
				                </c:if> v${i}">
						${i}
			</div>
		</c:forTokens>
</div>
	
<div id="voted"	style="text-align: center; width: 400px; height: 20px;">
		<c:if test="${ vote != null}"> <%-- User has voted => can see the result. --%>
			<div id ="total">Total:${resultVote}</div>
		</c:if>
		<div id="log"  style = "display:none;">
			Pour voter veuillez vous logger : <a class="login" style="cursor:pointer;">Connexion</a>
		</div>
</div>