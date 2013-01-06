<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>   
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<script type="text/javascript" src="js/int/url-generate.js"></script>
<script type="text/javascript" src="js/int/action.js"></script>
</head>


<body>

<c:choose>
   <c:when test="${action.id != null}">
       <ryctag:pageheadertitle title="Modifier une action"/>
   </c:when>
   <c:otherwise>
       <ryctag:pageheadertitle title="Créer une action"/>
   </c:otherwise>
</c:choose>

<c:choose>
       	<c:when test="${action.id != null}">
       			<ryctag:form action="/action/delete" modelAttribute="action">
        			<input type="hidden" name="id" value="${action.id}"/> 
        			<input type="submit" value="Supprimer"/>
       			</ryctag:form>
       	</c:when>			
</c:choose>

<ryctag:form action="/action/editsubmit" modelAttribute="action">
        <ryctag:input path="title" label="Titre"/>  
        
        <tr><td style="padding: 6px;">Raccourci</td>
            	<td><form:input path="shortName" id="shortName" type="input" /></td></tr>  
               
        <tr>
       		<td><label for="url">Fragment d'URL</label></td>
			<td><form:input path="url" required="required" type="input" cssStyle="width:100%;" /></td>
			<td><input type="submit" value="Générer une url" id="generate" /></td>
        </tr>
    
    	<tr>
    		<td><label>Description courte</label></td>
    		<td><textarea name="shortDescription" cols="60" rows="4">${action.shortDescription}</textarea></td>
    	</tr>
    		
        <tr>
        <td><label>Description étendue</label></td>
        <td><textarea name="content" cols="60" rows="15">${action.content}</textarea></td>
        </tr>
       
		<input type="hidden" name="id" value="${action.id}"/> 
            
        <tr>
        	<td colspan="2" align="center" style="text-align: center;">
       	  	 <input type="submit" value="<c:choose><c:when test="${action.id != null}">Sauver</c:when><c:otherwise>Créer</c:otherwise></c:choose>" />
       		</td>
        </tr>
</ryctag:form>

</body>  
</html>




