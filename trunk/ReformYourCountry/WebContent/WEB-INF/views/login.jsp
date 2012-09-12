<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@page import ="reformyourcountry.web.ContextUtil" %>
<html>
<head>
<title>Login</title>
</head>
<body>
	<form action="loginsubmit" method="post">
		<label for="identifier">Pseudo/Adresse e-mail </label>
        <input type="text" name="identifier" required="required"/><br />
        <label for="password">Votre mot de passe </label>
			<c:choose>
			<c:when test="<%= !ContextUtil.devMode %>">				
				<input type="password" name="password" required="required"/>
				<br />
			</c:when>
			<c:otherwise>
			
			<input type="password" name="password" required="required" value="secret"/>
			<label>Your are in dev mode : default password should be "secret"</label><br>
			</c:otherwise>
		</c:choose>
		
		J'ai oublié mon <a href="">mot de passe</a><br /> <input
			type="checkbox" name="keepLoggedIn" /><label for="keepLoggedIn">Je
			souhaite rester connecté</label>
	</form>

</body>
</html>