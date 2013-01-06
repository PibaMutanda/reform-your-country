<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<title>${group.name}</title>
</head>
<body>

<ryctag:breadcrumb>
	<ryctag:breadcrumbelement label="groupes" link="/grouplist" />
	<ryctag:breadcrumbelement label="${group.name}" />
</ryctag:breadcrumb>
<ryctag:pageheadertitle title="${group.name}"/>

<div style="font-size:12px">
	<ryc:conditionDisplay privilege="MANAGE_GROUP">
		 <a href="/groupedit?id=${group.id}">edit</a>&nbsp-&nbsp
		 <a href="/groupremove?id=${group.id}">supprimer</a>	
	</ryc:conditionDisplay>
</div>
<br/>
${group.description} <br/><br/>    

<div>
	<ryctag:usersgrid userList="${userbygrouplist}"></ryctag:usersgrid>
</div>

</body>
</html>