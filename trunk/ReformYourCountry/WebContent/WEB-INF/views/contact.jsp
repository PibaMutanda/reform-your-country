<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<body>
<ryctag:pageheadertitle title="Contactez-nous"> 
    </ryctag:pageheadertitle>
    
<p>Nous sommes joignables par e-mail à l’adresse <img style= "position:relative; top:3px;" src="/images/_global/image-mailbon.png" 
alt="L'adresse mail est insérée en image pour éviter qu'un robot ne puisse l'utiliser, pas pour vous empêcher de la copier. Veuillez-nous excuser de ce désagrément."
title="L'adresse mail est insérée en image pour éviter qu'un robot ne puisse l'utiliser, pas pour vous empêcher de la copier. Veuillez-nous excuser de ce désagrément."/> ou bien via le formulaire ci-dessous.  
</p>
<center>
<form  action="/sendmail" method="post">
<table style="width:600px; text-align: right">
	<tr>
		<td width="192px">
			Votre adresse mail
		</td>
		<td >
			<input type="email" name="sender" value="${mailsender}"style="width:100%"/>
		</td>
	</tr>
	<tr>
		<td >
			Sujet
		</td>
		<td >
			<input type="text" name="subject" style="width:100%"/>
		</td>
	</tr>
	<tr>
		<td  >
			Votre message
		</td>
		<td>
			<textarea name="content" rows="7"style="width:100%"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2"style="text-align: center;">
			<input type="submit" value="Envoyer"/>
		</td>
	</tr>
</table>
</form>
</center>

</body>
</html>