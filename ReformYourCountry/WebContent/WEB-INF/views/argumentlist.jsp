<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div  style="width:400px;<c:if test='${ispos}'>float:right;</c:if><c:if test='${!ispos}'>float:left;</c:if>">
	<c:forEach items="${listToShow}" var="arg">
		<%@include file="argumentdetail.jsp"%>
		<%@include file="argumentcomment.jsp"  %>
			<form action="" method="post">
				<input type="hidden" id="idArg" value="${arg.id}" />
				<textarea id="comm" rows="1" cols="20"></textarea>
				<input type="button" value="commenter"
					onclick="sendNewComment(this, comm.value, idArg.value);">
			</form>
			
	</c:forEach>
				
	<c:if test="${current.user.id != null }">		
		<form class="argumentNegForm" action="" method="post" >
			<input type="hidden" id="ispos${ispos}" value="${ispos}"/>
			<input type="hidden" id="action${ispos}" value="${action.id}" />
			<textarea id="title${ispos}" rows="1" cols="15"></textarea><br/>
			<textarea id="comment${ispos}"rows="5" cols="15" ></textarea><br/>
			<input type="button"   value="Commenter" onclick="sendNewComment(this,comment${ispos}.value,action${ispos}.value,title${ispos}.value,ispos${ispos}.value);">
		</form>
	</c:if>
</div>	                     