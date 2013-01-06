<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>

</head>
<body>
<ryctag:pageheadertitle title="Administrateur"/>
	
<form action="/admin/createIndex" method="post">
<input type="submit" value="Générer un index"/>
</form>

<form action="/admin/majArticle" method="post">
<input type="submit" value="génération de l'html de tous les articles"/>
</form>

<ul>
   <li>
		<a href="/privilegeoverview">Liste des utilisateurs ayant un rôle ou un privilège spécial</a>
   </li>
</ul>

<a href="/lastevent/listupdatedevent">Liste des derniers changements</a><br/>
<a href="/lasteventargumentcomment/listupdatedevent">Liste des derniers changements(Arguments,Commentaires, Bons examples)</a>

</body>
</html>