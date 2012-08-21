<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<title>${article.title}</title>
</head>

<body>
	<h2>${article.title}</h2>
	release date: ${article.releaseDate}<br/>

	<ryc:conditionDisplay privilege="EDIT_ARTICLE">
		<form action=articleedit method="GET">
			<input type="hidden" name="id" value="${article.id}"> <input
				type="submit" value="Editer" />
		</form>
	</ryc:conditionDisplay>

	<form action="articleparentedit" method="GET">
		<input type="hidden" name="id" value="${article.id}" /> <input type="submit"
			value="Editer parent" />
	</form>

	<hr/>
	
	${articleContent}

</body>
</html>