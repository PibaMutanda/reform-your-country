<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${book.title}</title>
</head>
<body><!-- 

	<h5>Titre: ${book.title}</h5>
	<h5>Auteur: ${book.author} Année ${book.pubYear}</h5>
	<img alt=""
		src="http://site.enseignement2.be/bibliographie/McKinsey2007.png"
		height="150" width="150">
	<p>${book.description}</p>
	<ryc:conditionDisplay privilege="EDIT_BOOK">
		<form action="bookedit" method="GET">
			<input type="hidden" name="id" value="${book.id}" /> <input
				type="submit" value="Editer" />
		</form>
	</ryc:conditionDisplay>-->
	
	<ryctag:book book= "${book}"/>

</body>
</html>