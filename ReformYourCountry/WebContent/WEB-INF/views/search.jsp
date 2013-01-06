<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<body>
	<ryctag:pageheadertitle title="Résultats de la recherche pour '${searchtext}'"/>

	<c:if test="${noResult == true}">
		Aucun résultat trouvé. 
	</c:if>
	<c:forEach items="${searchResult}" var="result">
		<c:if test="${result.visible}">
			<div class=listToSearchTitle>
			     <a  href="${result.url}">${result.title}</a>
			</div>
			<c:if test="${result.entityClassName == 'Article'}">
			<span class="datepublication"> <ryc:publishDate id="${result.id}"/> </span>
			</c:if>
			<div class=listToSearch>
			    
			    ${result.text}
			    <br/> <br/>
			</div>
		</c:if>
	</c:forEach>

</body>
</html>
