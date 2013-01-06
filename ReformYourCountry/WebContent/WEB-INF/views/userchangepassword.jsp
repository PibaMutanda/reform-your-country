<%@ page import="reformyourcountry.security.SecurityContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="reformyourcountry.security.Privilege"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>


<html>
<body>
<!-- <h1>Formulaire de changement de password</h1> -->

<ryctag:breadcrumb>
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	<ryctag:breadcrumbelement label="changement de mot de passe" />
</ryctag:breadcrumb>
<ryctag:pageheadertitle title="changement de mot de passe"/>
	<form:form modelAttribute="user" action="/user/changepasswordsubmit">
	<table>
			<c:if test="${verifyOldPassword}">
		 		 <tr><td>Password actuel: </td><td><input type="password" name="oldPassword" /></td>
		   		 <td>${errorNoOld}</td></tr>
		    </c:if>
		<tr><td>Nouveau mot de passe: </td><td><input type="password" name="newPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<tr><td>Confirmer le nouveau password: </td><td><input type="password" name="confirmPassword" /></td><td>${errorEmpty}${errorDiff}</td></tr>
		<form:hidden path="id" />
		<tr><td><input type="submit" value="changer"/></td><td><a href="/user/${user.userName}">Annuler</a></td></tr>
	</table>
	</form:form>
</body>
</html>