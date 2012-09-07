<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Editer une action</title>
</head>
<body>
       
<h1>Editer une action</h1>

    <ryctag:form action="actioneditsubmit" modelAttribute="action">
        <ryctag:input path="title" label="title"/>
        <ryctag:input path="content" label="content"/>
        <ryctag:input path="url" label="url"/>
         <ryctag:input path="shortDescription" label="shortDescription"/>
          <ryctag:input path="longDescription" label="longDescription"/>
       
		<input type="hidden" name="id" value="${id}"/> 
        <tr><td><input type="submit" value="Sauver" /></td><td> <a href="action?id=${id}">Annuler</a></td></tr>
    </ryctag:form>
  
</html>




