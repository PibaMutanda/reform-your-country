<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

	<h1>
		<c:choose>
			<c:when test="${action.id != null}">Editer une action</c:when>
			<c:otherwise>Créer une action</c:otherwise>
		</c:choose>
	</h1>
	<ryctag:form action="actioneditsubmit" modelAttribute="action">
        <ryctag:input path="title" label="Titre"/>
        <ryctag:input path="content" label="Contenu"/>
        <ryctag:input path="url" label="Url"/>
        <ryctag:input path="shortDescription" label="Description brève"/>
        <ryctag:textarea path="longDescription" label="Description étendue"/>
       
		<input type="hidden" name="id" value="${action.id}"/> 
            
        <tr><td><input type="submit" value="<c:choose><c:when test="${action.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td>
        <td> <a href="	<c:choose>
        					<c:when test="${action.id != null}">action?id=${action.id}</c:when>
        					<c:otherwise>actionlist</c:otherwise>
        				</c:choose>"     >Annuler</a></td></tr>
    </ryctag:form>
  
</html>




