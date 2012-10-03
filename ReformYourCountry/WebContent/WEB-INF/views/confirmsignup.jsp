<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
  <title>Insert title here</title>
</head>
  <body>
	
<p> Vous êtes maintenant enregistré sur enseignement2 avec un compte ${typecompte}</p>
	<c:choose>
		<c:when test="${typecompte == 'Facebook'}">
			<p>
				Vous pourrez dorénavant vous connecter depuis <a href="signin">cette page</a>.
			</p>
		</c:when>
			<c:when test="${typecompte == 'Twitter'}">
			<p>
			   Un email d'activation de vote compte vous a été envoyé sur ${email}; vous pourrez vous connecter une fois votre email vérifié.
			</p>
		</c:when>
	</c:choose>


</body>
</html>