<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<style type="text/css">

table
{
width:100%;
}
td{
width:50%;

}

</style>
<title>Liste des livres</title>
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