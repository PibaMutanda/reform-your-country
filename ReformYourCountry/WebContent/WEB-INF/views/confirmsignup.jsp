<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>

<body>
	<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<p> Vous êtes maintenant enregistré sur ${p_website_name} avec un compte ${accounttype}</p>
	
		<c:if test="${email != null}">
			<p>
			   Un email d'activation de vote compte vous a été envoyé sur ${email}; vous pourrez vous connecter une fois votre email vérifié.
			</p>
		</c:if>



</body>
</html>