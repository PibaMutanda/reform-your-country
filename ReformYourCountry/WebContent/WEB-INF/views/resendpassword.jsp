<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<body>
	<p>Nous allons vous renvoyer un mot de passe sur votre
		adresse e-mail. Vous devez avoir été enregistré au préalable sur notre site avec cette adresse e-mail, merci de la renseigner ci-dessous. Alternativement, vous pouvez indiquer votre nom d'utilisateur.</p>

	<form method="post" action="resendpasswordsubmit">
	
		e-mail ou nom d'utilisateur <input type="text" name="identifier" />
		<div style="color: red;">
			<c:if test='${mailerrormessage != null}'>${mailerrormessage}</c:if>
			<c:if test='${param.mailerrormessage != null}'>${param.mailerrormessage}</c:if>
		</div>
		
     	<br/><br/>
		<input type="submit" value="envoyez moi un nouveau mot de passe" />
	</form>

	<br/>

	<form method="post" action="home">
		<input type="submit" value="Annuler" />
	</form>
</body>
</html>