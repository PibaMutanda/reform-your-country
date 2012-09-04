<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="reformyourcountry.web.ContextUtil"%>
<%@ page import="reformyourcountry.exception.*"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Error page</title>
</head>
<body>




	<h2>The application has generated an error</h2>
		
	<%
			if(exception instanceof UnauthorizedAccessException){
			    UnauthorizedAccessException e = (UnauthorizedAccessException) exception;
			    out.print(e.getErrorMessage());
			} 
	%>

	<c:choose>
		<c:when test="<%=ContextUtil.devMode%>">
			<h3>Please check for the error given below</h3>
			<b>Exception:</b>
			<br>
			<font size="2" color="red"> <%=ExceptionUtil.getStringBatchUpdateExceptionStackTrace(exception, true)%>

			</font>
		</c:when>
	</c:choose>
	
	<center>
		<img src="images/404.jpg" />
	</center>
	
</body>
</html>