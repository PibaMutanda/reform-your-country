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
<body>
<ryctag:pageheadertitle title="Éditer le contenu de l'article"/>
    <%-- Help handle --%>
    <div id="helphandle">
        <span id="click" style="color: #182947;"><b>?</b></span>
        <div id="helptext"></div>
        <%-- Will contain the help text--%>
    </div>
    <br />
	<h1>Editer le contenu d'un article</h1>	
	<form:form modelAttribute="article" action="article/contenteditsubmit">
		<table><tr>
				<td><input id="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span></td>
				<td><form:errors path="content" cssClass="error" /></td>
				</tr></table>
		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
		<form:textarea path="content" class="expand autosaveable" cols="60" rows="3" style="width:96%" />
		<form:hidden path="id" />
	</form:form>
</body>
</html>