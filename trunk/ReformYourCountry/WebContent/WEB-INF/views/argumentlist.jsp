<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div style="
        <c:if test='${ispos}'> width:390px; float:right;</c:if>  <%-- Right column --%>
        <c:if test='${!ispos}'>width:385px; float:left;background: url(images/_global/bg-sidebar.gif) 100% 0 repeat-y; padding-right:15px;</c:if> <%-- Left column + vertical separator line --%>
        ">
	<c:forEach items="${listToShow}" var="arg">
		
		<div id="arg${arg.id}" style="width:100%; display: inline-block;background:url(/images/_global/separator3.gif) 0 0 repeat-x ;">
			<%@include file="argumentdetail.jsp"%>
		</div>

	</c:forEach>
   
	<c:if test="${current.user.id != null }">
		<div style="text-align: center; background:url(/images/_global/separator3.gif) 0 0 repeat-x ;">
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
