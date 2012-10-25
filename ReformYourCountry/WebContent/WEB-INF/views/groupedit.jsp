<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<c:when test="${group.id != null}">Editer un groupe</c:when>
			<c:otherwise>Créer un groupe</c:otherwise>
		</c:choose>
	</h1>
	<ryctag:form action="/groupeditsubmit" modelAttribute="group">
        <ryctag:input path="name" label="name" required="required"/>
        <ryctag:input path="description" label="Description"/>
        <ryctag:input path="url" label="Url"/>
     	
       	<input type="hidden" name="id" value="${group.id}"/> 
            
        <tr><td><input type="submit" value="<c:choose><c:when test="${group.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td>
        <td> <a href="	<c:choose>
        					<c:when test="${group.id != null}">/group?id=${group.id}</c:when>
        					<c:otherwise>/grouplist</c:otherwise>
        				</c:choose>"     >Annuler</a></td></tr>
    </ryctag:form>
  
</html>




