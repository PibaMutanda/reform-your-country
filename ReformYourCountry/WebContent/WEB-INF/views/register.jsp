<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>   
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<head>
<title>Inscription</title>
</head>
<body>
    <ryctag:pageheadertitle title="Inscription"/>
    
   <p>Créer un nouvel utilisateur vous permettra de faire certaines actions telles que voter ou argumenter.
   Vous devez choisir un pseudonyme et un mot de passe qui vous permettront de vous reconnecter dans le futur.</p>  
    
    
   <ryctag:form action="/registersubmit" modelAttribute="user">
    	<ryctag:input path="userName" label="pseudo"/>
    	<ryctag:password path="password" label="mot de passe"/>
     	<tr> 
 	    	<td><label for="mail">e-mail</label><br/>
 	    	    <span style="font-size:70%">Votre adress e-mail vous permettra de vous faire renvoyer votre mot de passe en case d'oubli.<br/>
 	    	       Nous ne la revendrons pas et ne vous enverrons pas de publicité pour respecter votre vie privée.
 	    	    </span>
 	    	</td> 
     		<td><form:input path="mail" type="mail" /></td> 
     		<td><form:errors path="mail" cssClass="error" /></td>
     	</tr>
    	<tr> 
      	  <td COLSPAN="2" align="center"> 
    	  <span class="ka_button small_button small_royalblue" >
    	    <input type="submit" value="m'inscrire" />
      	  </span>
     	  </td> 
     	</tr> 
    </ryctag:form>
    
    <p>vous pouvez également vous <a href="/signin" >connecter</a> sur ${p_website_name} en utilisant votre compte Facebook,Twitter,Linked In ou Google.

</body>