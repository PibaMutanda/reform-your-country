<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>

</head>
<body>
<ryctag:pageheadertitle title="Administrateur">
	</ryctag:pageheadertitle>
	
<form action="/admin/createIndex" method="post">
<input type="submit" value="Générer un index"/>
</form>

</body>
</html>