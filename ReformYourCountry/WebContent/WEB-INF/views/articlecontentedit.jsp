<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
<title>Editer le contenu d'un article</title>
<script type="application/javascript" src="js/ext/jquery.textarea-expander.js"></script>
<script type="application/javascript" src="js/int/help.js"></script>
<script type="application/javascript" src="js/int/autosave.js"></script>
</head>
</head>
<body>
	<%-- Help handle --%>
	<div id="helphandle">
		<span style="color: #182947"><b>?</b></span>
		<div id="helptext"></div>
		<%-- Will content the help text--%>
	</div>
	<br />
	<h1>Editer le contenu d'un article</h1>	
	<form:form modelAttribute="article" action="articleeditsubmit">
		<table><tr>
				<td><input id="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span></td>
				<td><a href="article?id=${article.getId()}">Annuler</a></td>
				</tr></table>
		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
		<form:textarea path="content" id="articleContent" class="expand" cols="60" rows="3" style="width:96%" />
		<form:hidden path="id" />
	</form:form>
</body>
</html>