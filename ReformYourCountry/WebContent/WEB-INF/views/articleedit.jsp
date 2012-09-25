<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

<head>
	<title>Page pour l'édition d'article</title>
	<script type="text/javascript" src="js/ext/jquery.textarea-expander.js"></script>
	<script type="text/javascript" src="js/int/help.js"></script>
	<script type="text/javascript" src="js/int/autosave.js" charset="UTF-8"></script>
</head>

<body>   
	 <%-- Help handle --%>
	 <div id="helphandle">
	   <span style="color:#182947"><b>?</b></span>
	   <div id="helptext"></div>  <%-- Will content the help text--%>
	 </div>
	 <br/>
	<h1>Editer un article</h1>	
	 <form:form modelAttribute="article" action="articleeditsubmit">
		<table>
	 		<tr><td><input id="save" type="submit" value="Sauver"/><span id ="saving" style="font-family:tahoma;font-size:9px;"></span></td>
	 		<td><a href="article?id=${article.getId()}">Annuler</a></td></tr>
	 		<ryctag:input path="title" label="Titre" required="required"/>
	 		<ryctag:date path="publishDate" label="Date de publication"/>
	 		<ryctag:checkbox path="publicView" label="Public ?"/>
	 		<!-- do not erase class , cols and rows attribute of the textarea , these values are used by textarea-expander.js -->
			</table>
			<form:textarea path="content" id="articleContent" class="expand" cols="60" rows="3" style="width:96%" />
			<form:hidden path="id" /><br/>
	</form:form>
	
</body>
</html>