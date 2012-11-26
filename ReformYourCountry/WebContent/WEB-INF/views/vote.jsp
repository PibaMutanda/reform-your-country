
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style=" margin-bottom:5px;width: 45px;text-align:center;font-weight: bold;font-size: 25px;">
	<c:choose>
		<c:when test="${currentItem.getVoteValueByUser(current.user)>0}">
			<img class="div-align-center" align="middle" src="\images\_global\up_selected.png"  onclick="unVoteItem(${currentItem.id});"/>		
		</c:when>
		<c:otherwise>
			<img class="div-align-center" align="middle" src="\images\_global\up.png" onclick="voteOnItem(this,${currentItem.id},1);"/>
		</c:otherwise>
	</c:choose>

    <div style="padding-top:5px; margin-bottom:-8px;">${currentItem.getTotal()}</div>
	<c:if test="${canNegativeVote}">
	<c:choose>
		<c:when test="${currentItem.getVoteValueByUser(current.user)<0}">
			<img class="div-align-center" align="middle" src="\images\_global\down_selected.png"  onclick="unVoteItem(${currentItem.id});"/>		
		</c:when>
		<c:otherwise>
			<img class="div-align-center" align="middle" src="\images\_global\down.png" onclick="voteOnItem(this,${currentItem.id},-1);"/>
		</c:otherwise>
	</c:choose>
	</c:if>
 </div>
