<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div  style="width:400px;<c:if test='${ispos}'>float:right;</c:if><c:if test='${!ispos}'>float:left;</c:if>">
	<c:forEach items="${listToShow}" var="arg">
		<div  id="arg${arg.id}">
			<%@include file="argumentdetail.jsp"%>
		</div>
		<div  id="comment${arg.id}">
			<%@include file="argumentcomment.jsp"  %>
		</div>
			
	</c:forEach>
			
	
	<c:if test="${current.user.id != null }">		
		<form class="argumentNegForm" action="" method="post" >
			<input type="hidden" id="ispos${ispos}" value="${ispos}"/>
			<input type="hidden" id="action${ispos}" value="${action.id}" />
			<textarea id="title${ispos}" rows="1" cols="15"></textarea><br/>
			<textarea id="comment${ispos}"rows="5" cols="15" ></textarea><br/>
			<input type="button"   value="Commenter" onclick="sendNewArg(this,comment${ispos}.value,action${ispos}.value,title${ispos}.value,ispos${ispos}.value);">
		</form>
	</c:if>
</div>	                     