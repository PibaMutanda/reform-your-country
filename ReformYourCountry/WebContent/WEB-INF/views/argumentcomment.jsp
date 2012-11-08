<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	<c:forEach items="${arg.commentList}" var="lst">
		<div>
			${lst.content}<br />
		</div>
	</c:forEach>
	<form action="" method="post">
				<input type="hidden" id="idArg" value="${arg.id}" />
				<textarea id="comm" rows="1" cols="20"></textarea>
				<input type="button" value="commenter"
					onclick="sendNewComment(this, comm.value,${arg.id});">
   </form>