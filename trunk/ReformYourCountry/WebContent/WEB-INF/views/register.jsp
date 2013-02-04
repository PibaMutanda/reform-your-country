<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form" %>   
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
</head>
<body>
   <ryctag:pageheadertitle title="Inscription"/>
    
   <p>Créer un nouvel utilisateur vous permettra de faire certaines actions telles que voter ou argumenter.
   Vous devez choisir un pseudonyme et un mot de passe qui vous permettront de vous reconnecter dans le futur.</p>  
   
    <!-- you can set variables starting with "p_" in the file named website_content.properties -->
    
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
    	    <input type="submit" value="m'inscrire" />
     	  </td> 
     	</tr> 
    </ryctag:form>
    
    <p>vous pouvez également vous <a href="/signin" >connecter</a> sur ${p_website_name} en utilisant votre compte Facebook,Twitter ou Google.

<!-- FACEBOOK SIGNIN -->
	<div style="vertical-align:top; width:100%; text-align:center;">
		<div style="display:inline-block;">
		<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
			<input type="hidden" name="scope" value="email,publish_stream,offline_access" /> 
				<div class="container">
				se connecter avec<br> 
					<input class="image-login" type="image" alt="logo facebook" src="images/social_logo/facebook.jpg" />
				</div>
		</form>
		</div>
		

		<!-- TWITTER SIGNIN -->
		
		<div style="display:inline-block;">
		<form id="tw_signin" action="<c:url value="/signin/twitter"/>"
			method="POST">
			<div class="container">se connecter avec<br> 
				<input style="padding-top:5px;" type="image" alt="logo twitter" src="images/social_logo/twitter.jpg" />
			</div>
		</form>
		</div>
		

		<!-- GOOGLE SIGNIN -->
		<div style="display:inline-block;">
		<form name="go_signin" id="go_signin"
			action="<c:url value="/signin/google"/>" method="POST">
			<input type ="hidden" name ="scope" value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"/>
			<div class="container">se connecter avec<br> 
				<input class="image-login" type="image" alt="logo google"
				src="images/social_logo/google.jpg" />
			</div>
		</form>
		</div>
</div>
</body>