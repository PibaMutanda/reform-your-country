﻿<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ page session="false"%>

<head>
<LINK href="css/social.css" rel="stylesheet" type="text/css">
</head>

<body>
<ryctag:pageheadertitle title="Connexion"/> 
Pour participer (voter, argumenter, etc.), vous devez vous connecter avec votre utilisateur.
<div style="padding-top:20px;">    
	<!-- SOCIAL SIGNIN - RIGHT COLUMN -->
	<div style="border-left-style: solid; border-width: 1px; float:right; padding-left:20px; width:300px;">

		<h2>Connexion via un réseau social</h2>
		<p>Vous pouvez facilement utiliser votre compte facebook, google ou autre pour vous connecter à enseignement2.be</p>
		<!-- FACEBOOK SIGNIN -->
	
		<div>
		<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST"> <!-- Goes to the spring social ProviderSigninController -->
			<input type="hidden" name="scope" value="email,publish_stream,offline_access" /> 
				<div class="container">
				se connecter avec<br> 
					<input class="image-login" type="image" alt="logo facebook" src="images/social_logo/facebook.jpg" />
				</div>
		</form>
		</div>
		<br/>

		<!-- TWITTER SIGNIN -->
		
		<div>
		<form id="tw_signin" action="<c:url value="/signin/twitter"/>" 	method="POST"> <!-- Goes to the spring social ProviderSigninController -->
			<div class="container">se connecter avec<br> 
				<input style="padding-top:5px;" type="image" alt="logo twitter" src="images/social_logo/twitter.jpg" />
			</div>
		</form>
		</div>
		<br/>
		
		<!-- LINKEDIN SIGNIN -->
		<div>
		<form name="li_signin" id="li_signin" action="<c:url value="/signin/linkedin"/>" method="POST"> <!-- Goes to the spring social ProviderSigninController -->
				<div class="container">se connecter avec<br>
			     	<input style="padding-top:5px;" type="image" alt="logo linkedin" src="images/social_logo/linkedin.jpg" />
				</div>
		</form>
		</div>
		<br/>

		<!-- GOOGLE SIGNIN -->
		<div>
		<form name="go_signin" id="go_signin"
			action="<c:url value="/signin/google"/>" method="POST"> <!-- Goes to the spring social ProviderSigninController -->
			<div class="container">se connecter avec<br> 
				<input class="image-login" type="image" alt="logo google"
				src="images/social_logo/google.jpg" />
			</div>
		</form>
		</div>
	</div>
	
	<!-- LOCAL SIGNIN - LEFT COLUMN -->
	<div style="padding-right: 20px;">
		<h2>Connexion avec votre compte enseignement2</h2>
		<p>Si vous n'avez pas de compte facebook, google ou autre <br>(ou ne désirez simplement pas les utiliser ici), <br>vous pouvez vous connecter avec un pseudonyme <br>enseignement2.be (qui nécessite que vous <a href="<c:url value="register"/>">créiez un utilisateur</a> <br>au préalable)</p> 
		<%@ include file="loginfragment.jsp"%>
	</div>
	
</div>	
</body>