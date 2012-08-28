<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Affichage liste livres</title>
</head>
<body>
<c:set var="bookList" value="${bookListTop}" scope ="request"/>
<c:set var="tablename" value="Top Book" scope ="request"/>
 <%@include file="booktable.jsp" %>	
 
<c:set var="bookList" value="${bookListOther}" scope ="request"/> 
<c:set var="tablename" value="Other Book" scope ="request"/>
 <%@include file="booktable.jsp" %>	
   	
   	
  

</body>
</html>