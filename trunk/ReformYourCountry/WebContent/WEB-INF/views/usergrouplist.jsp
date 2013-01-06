<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<html>
<body> 

<ryctag:breadcrumb>
	<ryctag:breadcrumbelement label="${user.userName}" link="/user/${user.userName}"/>
	<ryctag:breadcrumbelement label="groupes"/>
</ryctag:breadcrumb>
<ryctag:pageheadertitle title="Liste des groupes de ${user.fullName}"/>

<p>Pour un usage statistique (les enseignants ont voté que..., les élèves votent plutôt que...), pouvez-vous indiquer à quelle(s) catégorie(s) vous appartenez?</p>
	<form action="usergrouplistsubmit" method="post">
		<table style="width:100%">
			<c:forEach items="${allGroups}" var="currentGroup">
				<tr>
					<td><input type="checkbox"   name="groupIds" 
                        <c:if test="${myGroups.contains(currentGroup)}"  >
					     	checked="checked"  
			            </c:if> value="${currentGroup.id}" />
					</td>
					<td title="${currentGroup.description}">${currentGroup.name}
					</td>
					
				</tr>
			</c:forEach>
		<tr><td><input type="hidden" name="id" value="${id}" /></td></tr>	
		</table>
		<input type="submit" name="sauver" value="Sauver" />
	</form>
</body>
</html>