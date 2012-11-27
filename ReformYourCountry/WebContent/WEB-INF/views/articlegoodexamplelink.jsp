<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
<head>

</head>
<body>
<ryctag:pageheadertitle title="Ajouter un bon exemple pour l'article ${article.title}" />

Liste des bons exemples 
<form action="articlegoodexamplelinkeditsubmit" method="post">
	<c:forEach items="${goodExamplesList}" var="goodexample" >
	<div>
		<input type="checkbox" name="goodexample" <c:if test="${article.goodExamples.contains(goodexample)}">checked="checked" </c:if> value="${goodexample.id}" /> ${goodexample.title}
	
	</div>
	</c:forEach>
	
	<input type="hidden" name="id" value="${article.id}" />
	<input type="submit" value="Sauver"/> 
</form>


</body>
</html>