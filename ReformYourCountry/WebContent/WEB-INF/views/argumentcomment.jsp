<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="float: right;">
	<c:forEach items="${arg.commentList}" var="lst">
			${lst.content} - <a id="underlineUser" href="/user/${lst.user.userName}">${lst.user.userName}</a> - 
			${lst.getFormatedCreatedOn()}
	<br />
	</c:forEach>
	<form action="" method="post">
		<input type="hidden" id="idArg" value="${arg.id}" />
		<div>
			<div id="addcom${arg.id}">
				<input type="button" value="ajout de com"
					onclick="showText(this,${arg.id})">
			</div>
			<div id="commentArea${arg.id}" style="display: none;">
				<textarea id="comm" rows="1" cols="20"></textarea>
				<input type="button" value="commenter"
					onclick="sendNewComment(this, comm.value,${arg.id});">
			</div>
		</div>
	</form>
</div>