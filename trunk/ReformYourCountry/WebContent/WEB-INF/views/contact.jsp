<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contactez-nous</title>
</head>
<body>
<ryctag:pageheadertitle title="Contact"> 
    </ryctag:pageheadertitle>
    
<p>Nous sommes joignables par e-mail à l’adresse IMG“info@enseignement2.be” ou bien via le formulaire ci-dessous.  <!-- demander Cédric -->
</p>

<ryctag:form action="contactsubmit" modelAttribute="user">
    	<ryctag:input path="mail" label="Votre e-mail"/>
    	<ryctag:input path="text" label="Sujet"/>
    	<ryctag:textarea path="text" label="Votre message"/>
    	<td><input type="submit" value="Envoyer" /></td>
</ryctag:form>


</body>
</html>