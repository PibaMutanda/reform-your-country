<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>

<body>
<ryctag:pageheadertitle title="Creation de votre compte ${p_website_name}"/>

<p>Vous avez choisi de dissocier votre compte ${provider} de votre compte ${p_website_name}.<br/>
  Et il ne reste plus aucune compte de réseau social (facebook, google, twitter, etc.) lié à votre utilisateur ${p_website_name}<p>
<p>Donc, la prochaine fois que vous vous connecterez à notre site, vous devrez donner un mot de passe. 
Nous vous demandons de définir à présent ce mot de passe personnel. Notez-le, nous vous le redemanderons à votre prochaine visite.</p>
<form action="socialaccountdefinepasswordsubmit" method="post">
	<table>
		<tr><td>Mot de passe:</td><td><input type="password" name="password" /></td><td>${errorEmpty}${errorDiff}${errorDiff}</td></tr>
		<tr><td>Confirmer le nouveau mot de passe:</td><td><input type="password" name="confirmpassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		
	</table>
	<input type ="hidden" name="id" value="${id}"/>
	<input type="submit" value="changer"/> <a href="socialaccountmanage">Annuler</a>
	</form>

</body>
</html>