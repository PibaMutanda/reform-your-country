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
		Nom : <form:input path="lastName" /><br>
		Prénom : <form:input path="firstName" /><br>
		Pseudonyme <form:input path="userName"/><br>
		Date de Naissance : <form:input path="birthDate"/><br>
		Genre : <form:input path="gender"/><br>
		Mail : <form:input path="mail"/><br>
		Newsletters : <form:input path="nlSubscriber"/><br>
		<input type="submit" value="Sauver"/>
	</form:form>
	
	<Form action="userdisplay">
		<input type="submit" value="annuler">
	</Form>

</body>
</html>