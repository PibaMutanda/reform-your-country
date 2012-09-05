<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>

<h1>Editer un utilisateur</h1>

	<ryctag:form action="usereditsubmit" modelAttribute="user">
		<ryctag:input path="lastName" label="Nom"/>
		<ryctag:input path="firstName" label="Prénom"/>
		<ryctag:input path="userName" label="Pseudonyme"/>
<%--  		Date de Naissance : <form:input path="birthDate"/>&nbsp;&nbsp;&nbsp;<form:errors path="birthDate" cssClass="error"/><br> --%>
		<tr><td><form:label path="gender">Genre</form:label></td>
		   <td><form:radiobutton path="gender" value="MALE"/>MALE
		   <form:radiobutton path="gender" value="FEMALE"/>FEMALE</td>
		   <form:errors path="gender"  cssClass="error"/></tr>
		<ryctag:input path="mail" label="Mail"/>
		<ryctag:checkbox path="nlSubscriber" label="Newsletters"/>
		<form:hidden path="id" value="${id}"/> <%-- We need to add 'value=...' because of bug of Spring 3.1.2: the custom tag will render no value attribute if re redisplay the form after an validation error message --%>
		<tr><td><input type="submit" value="Sauver" /></td><td> <a href="user?username=${user.userName}">Annuler</a></td></tr>
	</ryctag:form>
</body>
</html>