<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width:100%; font-size:0.7em;">
	<c:forEach items="${argument.commentList}" var="comment">
		<div style="margin:0px; width:100%;">
				${comment.content} - <a id="underlineUser" href="/user/${comment.user.userName}">${comment.user.userName}</a> - 
				${comment.formatedCreatedOn}
		</div>
	</c:forEach>
	
	<div id="addcom${argument.id}">
		<br>
		<div id="commentLink" onclick="commentEditStart(this,${argument.id})">commenter</div>
	</div>
	<br>
	<div id="commentArea${argument.id}" style="display: none;">
		<textarea id="comm${argument.id}" style="width: 250px; height: 60px;" onkeyup="maxlength_comment(this,${argument.id},50,10);"></textarea>
		<input style="float: right;" id="sendArgComm${argument.id}"	type="button" disabled="disabled" value="Commenter"	onclick="return sendNewComment(this, ${argument.id});">
		<div style="float: left;" id="nbrCaract${argument.id}"></div>
	</div>
</div>
