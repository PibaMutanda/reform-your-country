<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.li{
list-style-type:none; 
}

</style>

</head>
<body>
	<h1>Créer/Editer un article</h1>
	<form:form modelAttribute="article" method="post" action="articleparenteditsubmit">
		Title: <input type="text" name="title" value="${article.title}"/>

		<input type="hidden" value="${article.getId()}"/>
		
		<input type="submit" value="
		  <c:choose>
		  <c:when test="${empty article}">Créer Article</c:when>
		  <c:otherwise>Sauver</c:otherwise>
		  </c:choose>
		"/>
		
		<br/><hr/>
		Parent: <br />
		<ryc:articlesTree radio="true" />
	</form:form>

</body>
</html>