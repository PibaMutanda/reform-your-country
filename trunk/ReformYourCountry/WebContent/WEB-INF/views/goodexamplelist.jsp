<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<title>Liste des bons examples</title>
</head>
<body>
	<ryctag:pageheadertitle title="Liste des bons examples triés par date"></ryctag:pageheadertitle>
	
	<table>
		<c:forEach items="${goodExamples}" var="goodExample">
		
<!-- 			<tr> -->
<%-- 				<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${goodExample.createdOn}"/></td> --%>
<%-- 				<td>${goodExample.title}</td> --%>
<%-- 				<td>: <a href="/goodexample/${goodExample.url}">Détails</a></td> --%>
<!-- 			</tr> -->
			<div id="${goodExample.id}">
    			<%@include file="goodexampledisplay.jsp" %>
			</div>
		</c:forEach>
	</table>
</body>
</html>