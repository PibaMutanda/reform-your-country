<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<meta name="description" lang="${p_lang}" content="${p_booklist_description}"/>
<meta name="robots" content="index, follow"/>	
<meta name="googlebot" content="noarchive" />

<style type="text/css">
table {
  width:100%;
}
td {
  width:50%;
}
</style>

</head>



<body>
<ryctag:pageheadertitle title="Bibliographie"/>

<div style="display:inline-block;">

	<div class="small-text">
		<ryc:conditionDisplay privilege="MANAGE_BOOK">
			 <a href="/book/create">Créer livre</a>
		</ryc:conditionDisplay>
	</div>
	<p>
		Nos articles référencient abondamment les ouvrages listés ici. Certains nous ont particulièrement marqués et nous les listons en premier, dans la liste "favoris".
		Les autres peuvent être très bons aussi et sont listés juste après.
		D'autres ouvrages qui nous ont parus moins significatifs ne sont pas repris sur cette page.
	</p>
</div>

<ryctag:separator/>
<c:set var="bookList" value="${bookListTop}" scope ="request"/>
<c:set var="tablename" value="Favoris" scope ="request"/>
 <%@include file="booktable.jsp" %>	
 
<ryctag:separator/>
<c:set var="bookList" value="${bookListOther}" scope ="request"/> 
<c:set var="tablename" value="Autres ouvrages" scope ="request"/>
 <%@include file="booktable.jsp" %>	

</body>
</html>