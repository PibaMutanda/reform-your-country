<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>{$article.title}</title>
</head>

<body>
<h2><c:forEach items = "${parentsTree}" var ="article">
    <a href ="Display?id=${article.id}">${article.title}></a>
</c:forEach></h2>
<h2>${articleTitle}</h2>
<h3>${releaseDate}</h3>

<ryc:security privilege="EDIT_ARTICLE">
<form action=articleedit method ="POST">
<input type="hidden" name ="id" value="${id}">
<input type = "submit" value ="Editer" />
</form>
</ryc:security>

<p>${articleContent}</p>

</body>
</html>