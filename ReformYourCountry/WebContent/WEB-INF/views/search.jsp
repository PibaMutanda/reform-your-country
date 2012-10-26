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

	<ryctag:pageheadertitle title="Résultats de la recherche">
	</ryctag:pageheadertitle>
	
	<ryctag:form action="search" modelAttribute="search" >
        <p>mot(s) de la recherche: ${searchtext}</p>
        <br/>
        <br/>
        <c:forEach items="${searchResult.publicResults}" var="result">
	        <a href="/article/${result.article.url}">${result.articleDocument.title}</a>
	        <br/>
	        <c:choose>
		        <c:when test="${result.articleDocument.content != null}">
		       		 ${result.articleDocument.content}
		        </c:when>
		        <c:otherwise>
		        	 ${result.article.description}
		        </c:otherwise>
	        </c:choose>
	        <br/>
        </c:forEach> 
        <br/>
        <ryc:conditionDisplay privilege="EDIT_ARTICLE"> 
        	<c:forEach items="${searchResult.privateResults}" var="result">
	        <a href="/article/${result.article.url}">${result.articleDocument.title}</a>
	        <br/>
	        <c:choose>
		        <c:when test="${result.articleDocument.content != null}">
		       		 ${result.articleDocument.content}
		        </c:when>
		        <c:otherwise>
		        	 ${result.article.description}
		        </c:otherwise>
	        </c:choose>
	        <br/>
	        <br/>
        </c:forEach>
        <br/>
        </ryc:conditionDisplay>
		${errorMsg}
	</ryctag:form>
	

</body>
</html>