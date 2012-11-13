<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div style="<c:if test='${ispos}'> width:390px; float:right;</c:if><c:if test='${!ispos}'>width:390px; float:left;border-right:3px solid grey; padding-right:8px;</c:if>">
	<c:forEach items="${listToShow}" var="arg">
		
		<div id="arg${arg.id}" style="width:100%; display: inline-block;">
			<%@include file="argumentdetail.jsp"%>
		</div>

	</c:forEach>
   
	<c:if test="${current.user.id != null }">
		<div style="text-align: center;">
		<hr/>
		<form class="argumentNegForm" action="" method="post">
			<input type="hidden" id="ispos${ispos}" value="${ispos}" /> 
			<input type="hidden" id="action${ispos}" value="${action.id}" />
			<textarea id="title${ispos}" rows="1" cols="15"></textarea>
			<br />
			<textarea id="comment${ispos}" rows="5" cols="15"></textarea>
			<br /> 
			<input type="button" value="Ajout argument" onclick="sendNewArg(this,comment${ispos}.value,action${ispos}.value,title${ispos}.value,ispos${ispos}.value);">
		</form>
		</div>
	</c:if>
</div>
