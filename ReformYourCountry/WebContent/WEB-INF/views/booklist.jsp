<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<meta name="description" lang="fr" content="Bibliographie, livres cités dans les articles sur l'enseignement. "/>
<meta name="robots" content="index, follow"/>	
<meta name="googlebot" content="noarchive" />
<style type="text/css">

table
{
width:100%;
}
td{
width:50%;

}

</style>
</head>

<body>
<ryctag:pageheadertitle title="Bibliographie"/>

Nos articles référencient abondamment les ouvrages listés ici. Certains nous ont particulièrement marqués et nous les listons en premier, dans la liste "favoris".
Les autres peuvent être très bons aussi et sont listés juste après.
D'autres ouvrages qui nous ont parus moins significatifs ne sont pas repris sur cette page.


<c:set var="bookList" value="${bookListTop}" scope ="request"/>
<c:set var="tablename" value="Favoris" scope ="request"/>
 <%@include file="booktable.jsp" %>	
 
<div style="width:100%;background: url(/images/_global/separator3.gif) 0 0 repeat-x; min-height: 10px;"></div>
<c:set var="bookList" value="${bookListOther}" scope ="request"/> 
<c:set var="tablename" value="Autres ouvrages" scope ="request"/>
 <%@include file="booktable.jsp" %>	

</body>
</html>