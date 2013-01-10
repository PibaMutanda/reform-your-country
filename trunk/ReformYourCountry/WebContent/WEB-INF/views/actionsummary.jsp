<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="js/int/votelistaction.js"></script>

<c:forEach items="${actionItems}" var="actionItem">
    <div class="action-list-item" style="position:relative;">  <%-- make the voting div float right bottom... http://stackoverflow.com/questions/311990/how-do-i-get-a-div-to-float-to-the-bottom-of-its-container --%>
		<div style="margin:10px;width: 103px;  position: absolute; bottom:0; right:30px;">
  					<c:set var="vote" value="${actionItem.voteAction}" scope="request"/>
				    <c:set var="id" value="${actionItem.action.id}" scope="request" />
		            <%@ include file="/WEB-INF/views/voteactionwidget.jsp" %>
    	</div>
		<a href="/action/${actionItem.action.url}">
			 <c:choose>
			     <c:when test="${actionItem.action.shortDescription != null}">
					<span title="${actionItem.action.shortDescription}">${actionItem.action.title}</span>
			 	 </c:when>
			 	 <c:otherwise>
			 		${actionItem.action.title}
			 	 </c:otherwise>
			 </c:choose>
		</a>
     </div>
</c:forEach> 