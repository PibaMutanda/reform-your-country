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
<script>
    $(function() {
        $( "#tabs" ).tabs();
    });
</script>
</head>
<body>
    <ryctag:pageheadertitle title="Éditer le contenu de l'article"></ryctag:pageheadertitle>
    <%-- Help handle --%>
    <div id="helphandle"><span>?</span><div><%--contains the helptext--%></div></div>
    <br />
    <form:form modelAttribute="article" action="article/contenteditsubmit">
		<table><tr>
				<td><input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span></td>
				<td><form:errors path="content" cssClass="error" /></td>
				</tr></table>
		
		<div id="tabs">
		<ul>
        	<li><a href="#tabs-1">Editer le texte</a></li>
        	<li><a href="#tabs-2">Editer le résumé</a></li>
        	<li><a href="#tabs-3">Editer "A classer"</a></li>
    	</ul>
    	<div id="tabs-1">
    	<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
			<form:textarea path="content" class="expand autosaveable" cols="60" rows="3" style="width:100%" />
		</div>
		<div id="tabs-2">
		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
			<form:textarea path="summary" class="expand autosaveable" cols="60" rows="3" style="width:100%" />
		</div>
		<div id="tabs-3">
		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
			<form:textarea path="toClassify" class="expand autosaveable" cols="60" rows="3" style="width:100%" />
		</div>
			<form:hidden path="id" />
			<td><input class="save" type="submit" value="Sauver" /><span id="saving" style="font-family: tahoma; font-size: 9px;"></span></td></tr>
		</div>
	</form:form>
</body>
</html>