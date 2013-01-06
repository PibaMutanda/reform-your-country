<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
	<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
	 <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>

<script type="text/javascript">
    // script to display the image path in the text field. 
	function getfile() {
		document.getElementById('hiddenfile').click();
	}
	function showfile() {
		document.getElementById('selectedfile').value = document.getElementById('hiddenfile').value;
	}
</script>
</head>
<body>

<ryctag:breadcrumb>
	<ryctag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	<ryctag:breadcrumbelement label="Ajouter une image" />
</ryctag:breadcrumb>	
<ryctag:pageheadertitle title="Ajouter une image"/>

	<!-- If there is a error message, show it! -->
	<c:choose>
		<c:when test="${!empty errorMsg}">
			<div class="error">Error:${errorMsg}</div>
		</c:when>
	</c:choose>


	<form method="post" action="/user/imageadd" enctype="multipart/form-data">
	
		<p>D'ici, vous pouvez nous envoyer une image qui apparaîtra à côté de votre nom.</p>
		<ol>
			<li><input type="button" value="séléctionner le fichier" onmouseout="showfile()" onclick="getfile()" /> 	
			    <input type="text"  disabled="disabled"  id="selectedfile" style="width:495px;"  style="float:right;" />
			    <input type="file" name="file" id="hiddenfile" style="display:none;" required="required"/>
			</li>
			<li><input type="submit" class="" value="télécharger sur le serveur"/>
				<input type="hidden" name="id" value="${user.id}" />
			</li>
		</ol>    
			
		<a href="/user/${user.userName}">Annuler</a><br>
	</form>
	
</body>
</html>