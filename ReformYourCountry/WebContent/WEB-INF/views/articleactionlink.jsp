<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>

<body>

<ryctag:breadcrumb>
   <ryctag:breadcrumbelement label="${action.title}" link="/article/${action.url}" />
</ryctag:breadcrumb>

<ryctag:pageheadertitle title="Ajouter une action pour l'article ${article.title}"/>

Liste des actions 
<form action="articleactionlinkeditsubmit" method="post">
	<c:forEach items="${actionList}" var="action" >
	<div>
		<input type="checkbox" name="action" <c:if test="${article.actions.contains(action)}">checked="checked" </c:if> value="${action.id}" /> ${action.title}
	
	</div>
	</c:forEach>
	
	<input type="hidden" name="id" value="${article.id}" />
	<input type="submit" value="Sauver"/> 
</form>


</body>
</html>