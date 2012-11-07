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
	<c:forEach items="${searchResult.results}" var="articleSearchUnit">
		<c:if test="${articleSearchUnit.visible}">
			<div class=listToSearchTitle>
			     <a  href="/article/${articleSearchUnit.article.url}">${articleSearchUnit.articleDocument.title}</a>
			</div>
			<span class="datepublication"> <ryc:publishDate id="${articleSearchUnit.article.id}"/> </span>
			<div class=listToSearch>
			    <c:choose>
			      <c:when test="${articleSearchUnit.articleDocument.content != null}">
			     		${articleSearchUnit.articleDocument.content}
			      </c:when>
			      <c:otherwise>
			      	 	${articleSearchUnit.article.description}
			      </c:otherwise>
			    </c:choose>
			    <br/> <br/>
			</div>
		</c:if>
	</c:forEach>

</body>
</html>
