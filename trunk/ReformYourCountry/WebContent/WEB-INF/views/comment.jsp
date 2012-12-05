<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width:100%; font-size:0.7em;">
	<c:forEach items="${currentItem.commentList}" var="comment">
		<c:if test="${!comment.hidden}">
			<div style="margin:0px; width:100%;">
					<c:if test="${comment.editable}">
						<div onclick="deleteComment(this,${comment.id},${currentItem.id})" class="divButton" style="vertical-align:text-top; color:red;font-size:1em; font-weight: bold;float:right;" title="Supprimer">&nbsp;X</div>
						<div onclick="commentEditStart(this,${currentItem.id},${comment.id},'${comment.content}')" class="divButton" style="float:right;" ><img alt="Editer" title="Editer" src="/images/_global/edit.png" class="icon-11" /></div>
					</c:if>
					<c:if test="${comment.hidable}">
						<div onclick="commentHide(this,${currentItem.id},${comment.id})" class="divButton" style="float:right;" ><img alt="Cacher" title="Cacher" src="/images/_global/edit.png" class="icon-11" /></div>
					</c:if>
					<div>
					${comment.content} - <a id="underlineUser" href="/user/${comment.user.userName}">${comment.user.userName}</a> - 
					${comment.formatedCreatedOn}
					</div>	
			</div>
		</c:if>
	</c:forEach>
	<div  style="font-size:1.3em;">
		<%@include file="help.jsp"%>
	</div>
	<div id="addcomForItem${currentItem.id}">
		<br>
		<span class="commentLink" class="commentLink divButton" onclick="commentCreateStart(this,${currentItem.id})">commenter</span>
	</div>
	<div id="commentAreaForItem${currentItem.id}" style="display: none;">
		<div id="errors" style="color:red;"><%--Error messages inserted by JavaScript --%></div>
		<input type="hidden" id="idComm${currentItem.id}" name="id" value=""/>
		<textarea id="comm${currentItem.id}" style="width: 250px; height: 60px;" onfocus="maxlength_comment(this,${currentItem.id},50,10);" onkeyup="maxlength_comment(this,${currentItem.id},50,10);"></textarea>
		<input style="float: right; display:hidden;" id="sendEditComm${currentItem.id}"	type="button" disabled="disabled" value="Modifier"	onclick="return sendEditComment(this,${currentItem.id});">
		<input style="float: right;" id="sendArgComm${currentItem.id}"	type="button" disabled="disabled" value="Commenter"	onclick="return sendNewComment(this,'item${currentItem.id}',${currentItem.id});">
		<div style="float: left;" id="nbrCaract${currentItem.id}"></div>
		<div class="commentLink divButton" style="float: right;text-align:center;" id="cancel"	onclick="return cancelComment('${currentItem.id}');">Annuler</div>
	</div>
</div>
