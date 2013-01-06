<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<html>
<head>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<meta name="description" content="Utilisateurs de ${p_website_name}">
<meta name="Keywords" content="utilisateur, informations" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="noarchive"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<script>
    <c:set var="search" value="${search}"/>
    $(function() {
        if(${!search}){
        	$("#tabs").tabs();
        }else{
        	$("#tabs").tabs({selected: 2});
        }
    });
</script>
</head>

<body>
<ryctag:pageheadertitle title="Utilisateurs"/>
<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Top contributeurs</a></li>
			<li><a href="#tabs-2">Derniers inscrits</a></li>
			<li><a href="#tabs-3">Recherche</a></li>
			<c:if test="${infoUsersHavingSpecialPrivileges != null}">
				<li><a href="#tabs-4">Privilèges utilisateurs</a></li>
			</c:if>
		</ul>
		<div id="tabs-1">
			<ryctag:usersgrid userList="${topUserList}"></ryctag:usersgrid>
		</div>
		<div id="tabs-2">
			<ryctag:usersgrid userList="${lastUsersRegistered}"></ryctag:usersgrid>
		</div>
		<div id="tabs-3">
			<form action="/user" method="GET">
				prénom, nom ou pseudo <input type="text" name="name"/> 
				<input type="submit" value="rechercher" />
			</form>
			<ryctag:usersgrid userList="${usersList}"></ryctag:usersgrid>
		</div>
		
		<c:if test="${infoUsersHavingSpecialPrivileges != null}">
		<div id="tabs-4">
			 <table border="1">
				<tr>
					<th>Pseudo</th>
					<th>Role</th>
					<th>Privileges</th>
					<th>Editer privileges</th>
				</tr>
				<c:forEach items="${infoUsersHavingSpecialPrivileges}" var="info">
					<tr>
						<td><a href= "/user/${info.user.userName}">${info.user.userName}</a></td>
						<td>${info.user.role}</td>
						<td>${info.privileges}</td>
						<td><a href= "/user/privilegeedit?id=${info.user.id}">Editer</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		</c:if>
	</div>
</body>
</html>