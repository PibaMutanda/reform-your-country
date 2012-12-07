<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<body>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
	<p>Ce compte ${socialnetworkname} est nouveau sur ${p_website_name}, ce qui est normal si vous vous connectez pour la première fois à ${p_website_name} avec votre compte ${socialnetworkname}.</p>
    <p>Nous allons maintenant vous créer automatiquement un utilisateur sur ${p_website_name}.</p> 
   
    <c:choose>
      <c:when test="${socialnetworkname == 'facebook'}">
          <p>Votre email (${email}) sera utilisée pour l'enregistrement.</p>
          <form method="post" action="/confirmaccountsubmit">
  		     <input type="submit" value="Confirmer et créer un compte sur ${p_website_name}" />
	      </form>
      </c:when>
      
      <c:when test="${socialnetworkname == 'twitter'}">
           <p>Merci de bien vouloir compléter votre adresse email (Twitter ne nous le transmet pas):</p>
          <div style="color:red;"><c:if test='${message != null}'>${message}</c:if><c:if test='${param.message != null}'>${param.message}</c:if><br/></div>
           <form method="post" action="/confirmaccountsubmit"> 
          
              <input type="email" name="email"/>
              
		      <input type="submit" value="Confirmer et créer un compte sur ${p_website_name}" />
           </form>
       </c:when>
    </c:choose>
    
	<form method="post" action="/home">
		<input type="submit" value="Annuler" />
	</form>
</body>
</html>