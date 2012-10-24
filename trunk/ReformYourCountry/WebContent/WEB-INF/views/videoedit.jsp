<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>    

<html>
<head>
<title>Insert title here</title>
</head>
<body>
      <ryctag:form action="/video/editsubmit" modelAttribute="video">
      
         <ryctag:input path="idOnHost" label="identifiant video"/>
             
		 <input type="hidden" name="id" value="${video.id}"/>
         <input type="submit" value="edit" />
      </ryctag:form>
      
</body>
</html>