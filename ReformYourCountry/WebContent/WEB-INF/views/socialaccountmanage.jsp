<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
</head>
<body>
<ryctag:pageheadertitle title="Mes comptes sociaux"/> 

Associé à l'email : ${user.mail} 
<table>
<tr><th>Comptes</th><th>Nom affiché</th><th>Action</th></tr>
<c:forEach items ="${connections}" var = "connection">

  <tr><td>${connection.getKey().getProviderId()}</td><td>${connection.getDisplayName()}</td><td><a href ="socialaccountdissociate?id=${user.id}&providerUserId=${connection.getKey().getProviderUserId()}&providerId=${connection.getKey().getProviderId()}">Désasocier le compte</a></td></tr>

</c:forEach>

</table>
<c:if test="${fb}">
<form action="addconnection/facebook" method ="post">
<input type ="submit" value="Ajouter une conection facebook">
</form>
</c:if>
<c:if test="${tw}">
<form action="addconnection/twitter" method ="post">
<input type ="submit" value="Ajouter une conection twitter">
</form>
</c:if>
<c:if test="${go}">
<form action="addconnection/google" method ="post">
<input type ="submit" value="Ajouter une conection google">
</form>
</c:if>
<c:if test="${li}">
<form action="addconnection/linkedin" method ="post">
<input type ="submit" value="Ajouter une conection linkedin">
</form>
</c:if>


</body>
</html>