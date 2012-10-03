<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
  <title>Insert title here</title>
</head>
  <body>
	<p>Ce compte ${socialnetworkname} est nouveau sur enseignement2.be, ce qui est normal si vous vous connectez pour la première fois à enseignement2.be avec votre compte ${socialnetworkname}.</p>
    <p>Nous allons maintenant vous créer automatiquement un utilisateur sur enseignement2.be. 
    
    <c:choose>
    <c:when test="${socialnetworkname == 'facebook'}">
  
    
     Votre email (:${email}) sera utilisée pour l'enregistrement.
     <form method="post" action="confirmaccountsubmit">
		<input type="submit" value="Confirmer et créer un compte sur enseignement2.be" />
	</form>
    
    </c:when>
    <c:when test="${socialnetworkname == 'twitter'}">
    <p>Merci de bien vouloir compléter votre adresse email:</p>
    <form method="post" action="confirmaccountsubmit"> 
        <input type="text" name="email"/>
		<input type="submit" value="Confirmer et créer un compte sur enseignement2.be" />
	</form>
    
    </c:when>
    </c:choose>
    </p>
	
	<form method="post" action="home">
		<input type="submit" value="Annuler" />
	</form>
  </body>
</html>