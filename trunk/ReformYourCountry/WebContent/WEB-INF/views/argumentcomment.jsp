<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="width:100%;">
	<c:forEach items="${arg.commentList}" var="lst">
	<p style="margin:0px; width:100%; font-size:12px;	">
			${lst.content} - <a id="underlineUser" href="/user/${lst.user.userName}">${lst.user.userName}</a> - 
			${lst.getFormatedCreatedOn()}
	</p>
	</c:forEach>
	<form action="" method="post">
		<input type="hidden" id="idArg" value="${arg.id}" />
		<div>
			<div id="addcom${arg.id}">
				<input type="button" value="ajout de com" onclick="showText(this,${arg.id})">
			</div>
			<div id="commentArea${arg.id}" style="display: none;">
				<textarea id="comm" rows="1" cols="20"></textarea>
				Il vous reste <span id="carac_reste_textarea_1"></span> caractères.
        			<script type="text/javascript">
                        maxlength_textarea('comm','carac_reste_textarea_1',50);
        			</script>
				<input type="button" value="commenter" onclick="sendNewComment(this, comm.value,${arg.id});">
			</div>
		</div>
	</form>
</div>