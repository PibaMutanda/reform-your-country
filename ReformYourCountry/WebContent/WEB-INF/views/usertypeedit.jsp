<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>

<ryctag:pageheadertitle title="Modifier le type de ${user.userName}"/>

Cette page permet de marquer un utilisateur comme étant un parti politique, un syndicat ou une association.<br/>
Il sera alors repris dans la liste des partis et associations.<br/><br/>

<form action="user/usertypeeditsubmit">

<c:forEach items="${ListSpecialType}" var="type">
		<input type="radio" name="specialType" value="${type.name}">${type.name}<br/><br/>
</c:forEach>
		
		<input type="submit" name="Sauver"><br/>
	    <input type="hidden" value="${user.id}" name="id"><br/>
</form>


</body>
</html>