<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a href="${urlParent}">Retour</a>
<div>${parentContent}</div>
<c:forEach items="${commentList}" var="comment">
			<div style="margin:0px; width:100%;" id="${comment.id}">
					<c:if test="${comment.hidden}">
						<a href="javascript:sendSimpleValue(this,${comment.id},'content',${comment.id},'ajax/argument/commentunhide')">unhide</a>
					</c:if>
					<div>
					${comment.content} - <a id="underlineUser" href="/user/${comment.createdBy.userName}">${comment.createdBy.userName}</a> - 
					${comment.formatedCreatedOn}
					</div>	
			</div>
</c:forEach>