<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1>Editer un utilisateur</h1>

	<form:form modelAttribute="user" action="usereditsubmit">
		<form:label path="lastName">Nom</form:label>        <form:input path="lastName" />&nbsp;&nbsp;&nbsp;<form:errors  path="lastName"  cssClass="error" /><br>
		<form:label path="firstName">Prénom</form:label>    <form:input path="firstName" />&nbsp;&nbsp;&nbsp;<form:errors path="firstName" cssClass="error"/><br>
	    <form:label path="userName">Pseudonyme</form:label> <form:input path="userName"/>&nbsp;&nbsp;&nbsp;<form:errors path="userName" cssClass="error" /><br>
<%--  		Date de Naissance : <form:input path="birthDate"/>&nbsp;&nbsp;&nbsp;<form:errors path="birthDate" cssClass="error"/><br> --%>
		<form:label path="gender">Genre</form:label>
		   <form:radiobutton path="gender" value="MALE"/>MALE
		   <form:radiobutton path="gender" value="FEMALE"/>FEMALE
		   <form:errors path="gender"  cssClass="error"/><br>
		<form:label path="mail">Mail</form:label>           <form:input path="mail"/>&nbsp;&nbsp;&nbsp;<form:errors path="mail" cssClass="error"/><br>
		<form:label path="nlSubscriber">Newsletters</form:label> <form:checkbox path="nlSubscriber"/><br>
		<form:hidden path="id" value="${user.id}"/> <%-- We need to add 'value=...' because of bug of Spring 3.1.2: the custom tag will render no value attribute if re redisplay the form after an validation error message --%>
		<input type="submit" value="Sauver" /> <a href="user?id=${user.id }">Annuler</a>
	</form:form>
	
</body>
</html>