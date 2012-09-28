<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
  <title>Insert title here</title>
</head>
  <body>
	Ce login est nouveau sur enseignement2
<p> votre email:${email} sera utilisée pour l'enregistrement<p>
	<form method="post" action="confirmaccountsubmit">
		<input type="submit" value="Confirmer et créer un nouveau compte" />
		<input name ="usermail" type ="hidden" value="${email}"/>
	</form>
	<form method="post" action="home">
		<input type="submit" value="Annuler" />
	</form>
  </body>
</html>