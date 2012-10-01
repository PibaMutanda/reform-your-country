<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ page session="false"%>

<LINK href="css/social.css" rel="stylesheet" type="text/css">
<ryctag:pageheadertitle title="Connexion" breadcrumb="false">

</ryctag:pageheadertitle>
<!-- LOCAL SIGNIN -->
<div style="float: left; border-right-style: solid; border-width: 1px; padding-right: 5px;">
	<h5>Connexion avec votre compte enseignement2</h5>
	<%@ include file="login.jsp"%>
</div>

<div style="float: left; padding-left: 5px;">
	
	<h5>Connexion avec un autre compte</h5>
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
	
		<br />Or you can <a href="<c:url value="/signup"/>">signup</a> with a
		new account.

</div>