<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<!-- LOCAL SIGNIN - LEFT COLUMN -->
<div style="float: left; border-right-style: solid; border-width: 1px; padding-right: 20px;">
	<h5>Connexion avec votre compte enseignement2</h5>
	<p>Si vous n'avez pas de compte facebook, google ou autre <br>(ou ne désirez simplement pas les utiliser ici), <br>vous pouvez vous connecter avec un pseudonyme enseignement2.be <br>(qui nécessite que vous créiez un utilisateur au préalable)</p>
	<%@ include file="login.jsp"%>
</div>


<!-- SOCIAL SIGNIN - RIGHT COLUMN -->
<div style="float: left; padding-left: 20px;">
	
	<h5>Connexion via un réseau social</h5>
	<p>Vous pouvez facilement utiliser votre compte facebook,<br>google ou autre pour vous connecter à enseignement2.be</p>
	<!-- FACEBOOK SIGNIN -->
	<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
		<input type="hidden" name="scope"
			value="email,publish_stream,offline_access" />
		<input class="img" type="image" alt="logo facebook" src="images/social_logos/facebook.jpg"/>
	</form>
	
	<!-- TWITTER SIGNIN -->
	<form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
			<input class="img" type="image"  alt="logo twitter" src="images/social_logos/twitter.jpg"/>
	</form>

	<!-- LINKEDIN SIGNIN -->
	<form name="li_signin" id="li_signin" action="<c:url value="/signin/linkedin"/>" method="POST">
	    <input class="img" type="image"  alt="logo linkedin" src="images/social_logos/linkedin.jpg"/>
	</form>
	
	<!-- GOOGLE SIGNIN -->
	<form name="go_signin" id="go_signin" action="<c:url value="/signin/google"/>" method="POST">
	    <input class="img" type="image"  alt="logo google" src="images/social_logos/google.jpg"/>
	</form>
	<p>Vous pouvez vous connecter sur enseignement2 avec <br>
	un compte social ci-dessus ou bien <a href="<c:url value="register"/>">vous enregistrer</a> <br> 
	avec un nouveau compte enseignement2.be.</p>
	
</div>
</body>