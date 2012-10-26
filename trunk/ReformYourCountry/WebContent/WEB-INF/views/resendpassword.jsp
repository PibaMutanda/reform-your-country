<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<body>
	<p>Nous allons maintenant vous renvoyer un mot de passe sur votre
		adresse e-mail,vous devez avoir été enregistré au préalable sur notre site avec cette adresse e-mail, merci de la renseigner ci-dessou.</p>

	<form method="post" action="resendpasswordsubmit">
	
		<input type="email" name="email" />
		<div style="color: red;">
			<c:if test='${mailerrormessage != null}'>${mailerrormessage}</c:if>
			<c:if test='${param.mailerrormessage != null}'>${param.mailerrormessage}</c:if>
			<br />
		</div>
		
		<input type="submit"
			value="envoyez moi un nouveau mot de passe" />
	</form>

	<form method="post" action="home">
		<input type="submit" value="Annuler" />
	</form>



</body>
</html>