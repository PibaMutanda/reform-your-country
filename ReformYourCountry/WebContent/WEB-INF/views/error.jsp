<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <TITLE>Error page </TITLE>
</head>
<body>
<%@ page isErrorPage = "true"%>
<%@ page import = "java.io.*" %>



       <h2>Your application has generated an error</h2>
       <h3>Please check for the error given below</h3>
       <b>Exception:</b><br> 
       <font color="red">${exception.toString()}<br/><br/>	
       <c:forEach items="${exception.stackTrace}" var="element">
   		 <c:out value="${element}"/><br/>
	   </c:forEach>
</font>
</body>
</html>