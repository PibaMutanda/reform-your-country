<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
</head>
<body>
	<ryctag:pageheadertitle title="Résultats de la recherche pour '${searchtext}'">
	</ryctag:pageheadertitle>

	<c:if test="${noResult == true}">
		Aucun résultat trouvé. 
	</c:if>
	<c:forEach items="${searchResult.results}" var="articleSearchUnit" >
		<c:choose>
			<c:when test="${articleSearchUnit.article.isPublished()}">
			    <c:set var="result" value="${articleSearchUnit}" scope="request"/>
	        	<ryctag:search />
	        	
	        	
	        </c:when>
	        <c:otherwise>
		        <ryc:conditionDisplay privilege="EDIT_ARTICLE">
		        	<c:set var="result" value="${articleSearchUnit}" scope="request"/>
		            <ryctag:search />
		            ${articleSearchUnit.articleDocument.id}
		        </ryc:conditionDisplay>
	        </c:otherwise>
        </c:choose>
    </c:forEach>
       
</body>
</html>
