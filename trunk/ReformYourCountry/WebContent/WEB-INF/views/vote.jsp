<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style=" margin-bottom:5px;width: 45px;text-align:center;font-weight: bold;font-size: 25px;" 
    <c:if test="${canNegativeVote}">title="${currentItem.voteCountPro} / -${currentItem.voteCountAgainst}" </c:if> >
	<c:choose>
		<c:when test="${currentItem.getVoteValueByUser(current.user)>0}">
			<div class="upvote selected"  onclick="unVoteItem(${currentItem.id});"></div>		
		</c:when>
		<c:otherwise>
			<div class="upvote"           onclick="voteOnItem(this,${currentItem.id},1);"></div>
		</c:otherwise>
	</c:choose>

    <div style="font-family:cursive; 
                 <c:choose>
                    <c:when test="${currentItem.getVoteValueByUser(current.user)==0}">color:rgb(196, 196, 196);</c:when>
                    <c:otherwise>color:#111c32;</c:otherwise>
                 </c:choose>
               ">${currentItem.getTotal()}</div>
    
	<c:if test="${canNegativeVote}">
	<c:choose>
		<c:when test="${currentItem.getVoteValueByUser(current.user)<0}">
			<div class="downvote selected" onclick="unVoteItem(${currentItem.id});"></div>		
		</c:when>
		<c:otherwise>
			<div class="downvote"          onclick="voteOnItem(this,${currentItem.id},-1);"></div>
		</c:otherwise>
	</c:choose>
	</c:if>
</div>
