<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div  style="width:400px;float:left;">
	<c:forEach items="${listToShow}" var="arg">
		<ryctag:argument color="${color}" title="${arg.title}" author="${arg.user.firstName} ${arg.user.lastName}" content="${arg.content}"/>
	</c:forEach>
			
			
	<form class="argumentNegForm" action="" method="post" >
		<input type="hidden" id="ispos" value="${ispos}"/>
		<input type="text" id="title" />
		<input type="hidden" id="action" value="${action.id}" />
		<textarea id="comment"rows="5" cols="15" ></textarea><br/>
		<input type="button"   value="Commenter" onclick="sendNewComment(this,comment.value,action.value,title.value,ispos.value);">
	</form>
</div>	