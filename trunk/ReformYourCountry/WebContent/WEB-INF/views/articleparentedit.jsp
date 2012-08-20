<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<h1>Créer un article</h1>
	<form:form modelAttribute="article" method="Post" action="articleparenteditsubmit">
		Title: <form:input type="text" name="title" path="title"/>

		<form:hidden path="id"/>
		
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