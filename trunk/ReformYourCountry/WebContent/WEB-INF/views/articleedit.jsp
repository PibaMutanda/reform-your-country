<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
     <%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
     <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<head>
	
    <script src="<c:url value="js/jquery.textarea-expander.js" />"></script>


<title>Page pour l'édition d'article</title>
</head>

<body>   
	<h1>Edit an article</h1>	
	
	 ${error}
	 
	 <form:form modelAttribute="article" action="articleeditsubmit">
		<table>
	 		<tr><td><input type="submit" value="Sauver"/></td>
	 		<td><a href="article?id=${article.getId()}">Annuler</a></td></tr>
	 		<ryctag:input path="title" label="Titre" required="required"/>
	 		<ryctag:date path="publishDate" label="Date de publication"/>
	 		<ryctag:checkbox path="publicView" label="Public ?"/>
	 		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
			</table>
			<form:textarea path="content" class="expand" cols="60" rows="3" style="width:100%" />
			<form:hidden path="id" /><br/>
	</form:form>
</body>
</html>