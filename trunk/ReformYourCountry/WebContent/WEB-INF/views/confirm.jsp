<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<ryctag:pageheadertitle title="Demande de confirmation"/>
	<div>Etes-vous sur de vouloir supprimer: ${info}</div>
	<form action="${url}" method="post" >
		<input type="hidden" name="id" value="${id}"/>
		<input type="submit" value="Je confirme"/>
		<input type="button" value="J'annule" onclick="window.location.href='${abortUrl}';"/>
	</form>
</body>
</html>