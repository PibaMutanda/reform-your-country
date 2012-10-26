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
	       	  	<c:if test="${resultListPublic != null }">
					<table>
						<c:forEach items="${resultListPublic}" var="ArticleSearchResult">
							<tr>
								<td>${ArticleSearchResult.title}</td>
								<td>${ArticleSearchResult.content}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
		<ryc:conditionDisplay privilege="EDIT_ARTICLE">
	       	  	<c:if test="${resultListPrivate != null }">
					<table>
						<c:forEach items="${resultListPrivate}" var="ArticleSearchResult">
							<tr>
								<td>${ArticleSearchResult.title}</td>
								<td>${ArticleSearchResult.content}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
					
<%-- 			<c:if test="${actionList != null }"> --%>
<!-- 				<table> -->
<%-- 					<c:forEach items="${actionList}" var="action"> --%>
<!-- 						<tr> -->
<%-- 							<td>${action.title}</td> --%>
<%-- 							<td>${action.shortDescription}</td> --%>
				
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<!-- 				</table> -->
<%-- 			</c:if> --%>
				</ryc:conditionDisplay>
				${errorMsg}
	</ryctag:form>
	

</body>
</html>