<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<head>
    <script type="text/javascript" src="js/int/url-generate.js"></script>
    
</head>
<body>
	<c:choose>
		<c:when test="${article.id != null}">
		     <ryctag:breadcrumb>
		         <c:forEach items="${parentsPath}" var="subarticle">
		             <ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
		         </c:forEach>
		         <ryctag:breadcrumbelement label="Edition" />
		     </ryctag:breadcrumb>
		     <ryctag:pageheadertitle title="${article.title}"/>
		</c:when>
		<c:otherwise>
		     <ryctag:pageheadertitle title="Créer un article"/>
		</c:otherwise>
	</c:choose>
	<ryctag:form modelAttribute="article" action="/article/editsubmit">
        <tr>
            <ryctag:input path="title" label="Titre" required="required" />

            
            <tr><td style="padding: 6px;">Raccourci</td>
            	<td><form:input path="shortName" id="shortName" type="input" maxlength="20"/></td></tr>   
            	       	
            <tr title="identifiant pour l'article dans les URLs">
				<td><label for="url">Fragment d'URL</label></td>
				<td><form:input path="url" required="required" type="input" cssStyle="width:100%;" /></td>
				<td><input type="submit" value="Générer une url" id="generate" /></td>
			</tr>
			
            <ryctag:dateinput path="publishDate" label="Date de publication" />
           
			<tr><td>Description (dans listes et pour SEO)</td><td><textarea name="description" cols="60" rows="4">${article.description}</textarea></td></tr>
            <ryctag:checkbox path="publicView" label="Public ?" />
            <form:hidden path="id" />
            <tr>
            	<td colspan="2" align="center" style="text-align: center;">
            		<input type="submit" value="<c:choose><c:when test="${article.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" />            		
            	</td>
            </tr>
            
    </ryctag:form>
</body>
</html>