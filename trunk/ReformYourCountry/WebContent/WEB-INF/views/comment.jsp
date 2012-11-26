<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width:100%; font-size:0.7em;">
	<c:forEach items="${currentItem.commentList}" var="comment">
		<div style="margin:0px; width:100%;">
				<c:if test="${comment.editable}">
					<div onclick="return deleteComment(this,${comment.id},'item${currentItem.id}')" class="divButton" style="color:red; font-weight: bold;float:right;" title="Supprimer">&nbsp;X</div>
					<div onclick="commentEditStart(this,${currentItem.id},${comment.id},'${comment.content}')" class="divButton" style="float:right;" ><img alt="Editer" title="Editer" src="/images/_global/edit.png" class="icon-11" /></div>
					
				</c:if>
				<div>
				${comment.content} - <a id="underlineUser" href="/user/${comment.user.userName}">${comment.user.userName}</a> - 
				${comment.formatedCreatedOn}
				</div>	
		</div>
	</c:forEach>
	<div  style="font-size:1.3em;">
		<%@include file="help.jsp"%>
	</div>
	<div id="addcom${currentItem.id}">
		<br>
		<div id="commentLink" class="commentLink divButton" onclick="commentAddStart(this,${currentItem.id})">commenter</div>
	</div>
	<div id="commentArea${currentItem.id}" style="display: none;">
		<input type="hidden" id="idComm${currentItem.id}" name="id" value=""/>
		<textarea id="comm${currentItem.id}" style="font-size:1.3em;width: 250px; height: 60px;" onfocus="maxlength_comment(this,${currentItem.id},50,10);" onkeyup="maxlength_comment(this,${currentItem.id},50,10);"></textarea>
		<input style="float: right; display:hidden;" id="sendEditComm${currentItem.id}"	type="button" disabled="disabled" value="Commenter"	onclick="return sendEditComment(this,${currentItem.id});">
		<input style="float: right;" id="sendArgComm${currentItem.id}"	type="button" disabled="disabled" value="Commenter"	onclick="return sendNewComment(this,'item${currentItem.id}',${currentItem.id});">
		<div style="float: left;" id="nbrCaract${currentItem.id}"></div>
		<div class="commentLink divButton" style="float: right;text-align:center;" id="cancel"	onclick="return cancelComment('${currentItem.id}');">Annuler</div>
	</div>
</div>
