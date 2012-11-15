<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div style="width:100%; font-size:0.7em;">
	<c:forEach items="${arg.commentList}" var="lst">
		<div style="margin:0px; width:100%;">
				${lst.content} - <a id="underlineUser" href="/user/${lst.user.userName}">${lst.user.userName}</a> - 
				${lst.getFormatedCreatedOn()}
		</div>
	</c:forEach>
		<form action="" method="post">
			<input type="hidden" id="idArg" value="${arg.id}" />
			<div>
				<div id="addcom${arg.id}">
					<br><div id="commentLink" onclick="showText(this,${arg.id})">commenter</div>
				</div>
				<br><div id="commentArea${arg.id}" style="display: none;">
					<textarea id="comm${arg.id}" style="width:250px; height: 60px;"  rows="1" cols="20"  onkeyup="maxlength_textarea(this,${arg.id},50,10);"></textarea>
					<input style="float:right;" id = "sendArgComm${arg.id}" type="button" disabled="disabled" value="Commenter" onclick="sendNewComment(this, ${arg.id});return false;">
					<div style="float:left;" id="nbrCaract${arg.id}"></div>				
				</div>
			</div>
		</form>
</div>
