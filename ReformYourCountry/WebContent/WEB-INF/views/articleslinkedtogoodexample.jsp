<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
</head>
<body>
	<ryctag:pageheadertitle	title="Liste des articles relatifs à ${goodExample.title}"/>
	
	<c:set var="articleList" value="${articleList}" scope="request" />
	<form action="/articlelinkedgoodexamplesubmit" method="post">
		<ryc:articlesListMultiSelect />
		<input type="hidden" name="id" value="${goodExample.id}" /> 
		<input type="submit" value="sauver" />
	</form>
</body>
</html>