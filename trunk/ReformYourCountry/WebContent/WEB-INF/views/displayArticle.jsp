<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='/WEB-INF/tags/security.tld' prefix='ct'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>{$article.title}</title>
</head>
<h2><c:forEach items = "${parentsTree}" var ="article">
    <a href ="Display?id=${article.id}">${article.title}></a>
</c:forEach></h2>
<body>

<h2>${articleTitle}</h2>
<h3>${releaseDate}</h3>

<ct:security privilege="EDIT_ARTICLE">
<form action=articleedit method ="POST">
<input type = "submit" value ="Editer" />
</form>
</ct:security>

<p>${articleContent}</p>

</body>
</html>