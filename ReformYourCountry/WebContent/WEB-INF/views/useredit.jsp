<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1>Editer un utilisateur</h1>

	<form:form modelAttribute="user" action="usereditsubmit">
		<form:label path="lastName" /> Nom : <form:input path="lastName" />&nbsp;&nbsp;&nbsp;<form:errors  path="lastName"  cssClass="error" /><br>
		<form:label path="firstName" />Prénom : <form:input path="firstName" />&nbsp;&nbsp;&nbsp;<form:errors path="firstName" cssClass="error"/><br>
	    <form:label path="userName" />Pseudonyme <form:input path="userName"/>&nbsp;&nbsp;&nbsp;<form:errors path="userName" /><br>
<!--  		Date de Naissance : <form:input path="birthDate"/>&nbsp;&nbsp;&nbsp;<form:errors path="birthDate" cssClass="error"/><br>-->
		<form:label path="gender" />Genre :
		   <form:radiobutton path="gender" value="MALE"/>MALE&nbsp;&nbsp;<form:radiobutton path="gender" value="FEMALE"/>FEMALE&nbsp;&nbsp;&nbsp;<form:errors path="gender"  cssClass="error"/><br>
		<form:label path="mail"></form:label>Mail<form:input path="mail"/>&nbsp;&nbsp;&nbsp;<form:errors path="mail" cssClass="error"/><br>
		<form:label path="nlSubscriber"></form:label>Newsletters:<form:checkbox path="nlSubscriber"/><br>
		<form:hidden path="id"/>
		<input type="submit" value="Sauver" /> <a href="user?id=${user.id }">annuler</a>
	</form:form>
	
	   
		

</body>
</html>