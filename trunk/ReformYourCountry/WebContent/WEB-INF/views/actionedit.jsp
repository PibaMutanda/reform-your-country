<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<script type="text/javascript" src="js/int/url-generate.js"></script>
</head>
<body>
<c:choose><c:when test="${action.id != null}"><ryctag:pageheadertitle title="Modifier une action"/></c:when><c:otherwise><ryctag:pageheadertitle title="Créer une action"/></c:otherwise></c:choose>
	<ryctag:form action="/action/editsubmit" modelAttribute="action">
        <ryctag:input path="title" label="Titre" id="title"/>        
        <ryctag:input path="url" label="Nom de la page de l'action" id="url"/>
        <td><input type="submit" value="Générer une url" id="generate" /></td>
        <ryctag:input path="shortDescription" label="Description brève"/>
        <ryctag:textarea path="content" label="Description étendue"/>
       
		<input type="hidden" name="id" value="${action.id}"/> 
            
        <tr><td><input type="submit" value="<c:choose><c:when test="${action.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td>
        <td> <a href="	<c:choose>
        					<c:when test="${action.id != null}">/action/${action.url}</c:when>
        					<c:otherwise>/action</c:otherwise>
        				</c:choose>"     >Annuler</a></td></tr>
    </ryctag:form>
  
</html>




