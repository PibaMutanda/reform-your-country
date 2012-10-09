<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>


<head>
<LINK href="css/social.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
$(document).ready(function() {		
	
	
	// ajax request wich will call loginController wich will place a flag autologin in the session
	// later this value will be used in the spring-social SigninAdapter to determine if we want create a cookie for the user login
	
	$('input[name="keepLoggedIn"]').click(function(){
		var checkbox = $(this).is(':checked');
		
		var request = $.ajax({
			url: "ajax/autologin",
			type: "GET", 	
			data : {autologin:checkbox},
			dataType: "html"
		});
		// if success
		request.done(function(data) {
			// console.log(data);
			//location.reload(); Maybe will make the server throw an UnauthorizedException if anonymous users cannot see that page. 
		});
								
	});
	
	
});

</script>

</head>

<body>
<ryctag:pageheadertitle title="Connexion"/> 
Pour participer (voter, argumenter, etc.), vous devez vous connecter avec votre utilisateur.
<div style="padding-top:20px;">    
	<!-- SOCIAL SIGNIN - RIGHT COLUMN -->
	<div style="border-left-style: solid; border-width: 1px; float:right; padding-left:20px; width:300px;">

		<h1>Connexion via un réseau social</h1>
		<p>Vous pouvez facilement utiliser votre compte facebook, google ou autre pour vous connecter à enseignement2.be</p>
		<!-- FACEBOOK SIGNIN -->
	
		<div>
		<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
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
		<form id="tw_signin" action="<c:url value="/signin/twitter"/>"
			method="POST">
			<div class="container">se connecter avec<br> 
				<input style="padding-top:5px;" type="image" alt="logo twitter" src="images/social_logo/twitter.jpg" />
			</div>
		</form>
		</div>
		<br/>
		
		<!-- LINKEDIN SIGNIN -->
		<div>
		<form name="li_signin" id="li_signin"
			action="<c:url value="/signin/linkedin"/>" method="POST">
				<div class="container">se connecter avec<br>
			     	<input style="padding-top:5px;" type="image" alt="logo linkedin" src="images/social_logo/linkedin.jpg" />
				</div>
		</form>
		</div>
		<br/>

		<!-- GOOGLE SIGNIN -->
		<div>
		<form name="go_signin" id="go_signin"
			action="<c:url value="/signin/google"/>" method="POST">
			<div class="container">se connecter avec<br> 
				<input class="image-login" type="image" alt="logo google"
				src="images/social_logo/google.jpg" />
			</div>
		</form>
		</div>
	</div>
	
	<!-- LOCAL SIGNIN - LEFT COLUMN -->
	<div style="padding-right: 20px;">
		<h1>Connexion avec votre compte enseignement2</h1>
		<p>Si vous n'avez pas de compte facebook, google ou autre <br>(ou ne désirez simplement pas les utiliser ici), <br>vous pouvez vous connecter avec un pseudonyme <br>enseignement2.be (qui nécessite que vous <a href="<c:url value="register"/>">créiez un utilisateur</a> <br>au préalable)</p> 
		<%@ include file="loginfragment.jsp"%>
	</div>
	
</div>	

<c:choose>
<c:when test='${autologin}'>    
<input type="checkbox" name="keepLoggedIn" checked="checked" />
</c:when>
<c:otherwise>
<input type="checkbox" name="keepLoggedIn" />
</c:otherwise>
</c:choose>
<label for="keepLoggedIn"><span class="tooltip" data-tooltip="Si vous cochez cette case, lors de votre prochaine visite vous serez connectés automatiquement">Je souhaite rester connecté</span></label>
</body>