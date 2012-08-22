<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute = "bk" method="post" action="sendnewbook">
	
	Abréviation: <form:input path="abrev"/>
	<form:errors path="abrev" cssClass="error"/><br/>  
	Titre: <form:input path="title"/><br/>
	Description:  <form:textarea path="description"/><br/>
	Auteur(s):  <form:input path="author"/><br/>
	Année de publication: <form:input path="pubYear"/><br/>
	Lien de référence: <form:input path="externalUrl"/><br/>
	
	<input type="submit" value="Sauver">
	  
</form:form><br/>

</body>
</html>