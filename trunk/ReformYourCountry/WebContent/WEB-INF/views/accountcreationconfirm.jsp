<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<body>
<p>Ce compte ${socialnetworkname} est nouveau sur ${p_website_name}, ce qui est normal si vous vous connectez pour la première fois à ${p_website_name} avec votre compte ${socialnetworkname}.</p>

 <p>Nous allons maintenant vous créer automatiquement un utilisateur sur ${p_website_name}.</p>  
      
   <form method="post" action="confirmaccountsubmit"> 
     <c:choose>
      <c:when test="${socialnetworkname == 'twitter'}">
           <p>Merci de bien vouloir compléter votre adresse email (Twitter ne nous le transmet pas):</p>
           <div style="color:red;"><c:if test='${mailerrormessage != null}'>${mailerrormessage}</c:if><c:if test='${param.mailerrormessage != null}'>${param.mailerrormessage}</c:if><br/></div>
           <input type="email" name="email"/>
      </c:when>              
      <c:otherwise>
          <p>Votre email (:${email}) sera utilisée pour l'enregistrement.</p>
      </c:otherwise>
     </c:choose> 
	 <input type="submit" value="Confirmer et créer un compte sur${p_website_name}" />
  </form>
    
  <form method="post" action="home">
		<input type="submit" value="Annuler" />
  </form>
   
</body>
</html>