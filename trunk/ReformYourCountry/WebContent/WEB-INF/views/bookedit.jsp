<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<script type="text/javascript" src="js/int/url-generate.js"></script>
</head>
<body>

<c:choose>
	<c:when test="${book.id != null}">
		 <ryctag:breadcrumb>
		    <ryctag:breadcrumbelement label="bibliographie"  link="/book"/>
		    <ryctag:breadcrumbelement label="ce livre" link="book/${book.url}" />
		    <ryctag:breadcrumbelement label="edit" />
		 </ryctag:breadcrumb>
		 <ryctag:pageheadertitle title="${book.title }"/>
	</c:when>
	<c:otherwise> 
		<ryctag:pageheadertitle title="Créer un livre"/>
	</c:otherwise>
</c:choose>


	<ryctag:form action="/book/editsubmit" modelAttribute="book" width="800px">
		<tr>
		    <td>Abréviation du livre</td><td><form:input path="abrev" /> </td><td><form:errors path="abrev" cssClass="error" /></td>
		 </tr>
		<ryctag:input path="title" label="Titre du livre:"/>
		<ryctag:input path="subtitle" label="Sous-titre du livre"/>
		<tr></tr>
		<tr title="identifiant pour le livre dans les URLs">
			<td><label for="url">Fragment d'URL</label></td>
			<td><form:input path="url" required="required" type="input" cssStyle="width:400px;"  /></td>
			<td><input type="submit" value="Générer une url" id="generate" /></td>
		</tr>
		 <tr>
		   <td>Description du livre</td><td><form:textarea path="description" cssStyle="width:500px;" rows="6"/></td>
		 </tr>
		 <tr>
		    <td>Auteur(s) du livre</td><td><form:input path="author" cssStyle="width:500px;"/> </td>
		 </tr>
		 <tr>
		     <td>Année de publication</td><td><form:input path="pubYear" /></td>
		 </tr>
		<tr>
		     <td>Lien de référence</td>    <td><form:input path="externalUrl" cssStyle="width:500px;"/>  </td>
		</tr>
		<tr>
			<td>Top book?</td>
			<td><form:radiobutton path="top" value="true" />Top
				<form:radiobutton path="top" value="false" />Other</td>
		<tr/>
		<form:hidden path="id" value="${book.id}"/>
		<tr><td colspan="2" align="center" style="text-align: center;"><input type="submit" value="<c:choose><c:when test="${book.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" /></td></tr>
	</ryctag:form>
</body>
</html>

