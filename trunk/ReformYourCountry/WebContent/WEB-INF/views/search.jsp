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
       	   <c:if test="${userList != null}">
		        <table>
					<c:forEach items="${userList}" var="user">
						<tr>
							<td>${user.userName}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td><a href="/user/${user.userName}">Afficher les détails</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			<c:if test="${groupList != null }">
				<table>
					<c:forEach items="${groupList}" var="group">
						<tr>
							<td>${group.name}</td>				
							<td><a href="/group/${group.name}">Afficher les détails</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${articleList != null }">
				<table>
					<c:forEach items="${articleList}" var="article">
						<tr>
							<td>${article.title}</td>
							<td>${article.description}</td>
				
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${actionList != null }">
				<table>
					<c:forEach items="${actionList}" var="action">
						<tr>
							<td>${action.title}</td>
							<td>${action.shortDescription}</td>
				
						</tr>
					</c:forEach>
				</table>
			</c:if>
	</ryctag:form>
	
<div>
	<c:choose>
		<c:when test= "">
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
</div>
</body>
</html>