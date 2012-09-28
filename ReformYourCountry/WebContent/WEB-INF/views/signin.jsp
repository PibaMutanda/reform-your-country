<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ page session="false" %>

<ryctag:pageheadertitle title="Connexion" breadcrumb="false">
 
</ryctag:pageheadertitle>
	
    <!-- LOCAL SIGNIN -->
 <div style="float:left;border-right-style:solid;border-width:1px;padding-right:5px;">
   <h5>Connexion avec votre compte enseignement2</h5> 
    <%@ include file="login.jsp"%>
 </div>
 
<div style="float:left;padding-left:5px;">
 <h5>Connexion avec un autre compte</h5> 
	<!-- TWITTER SIGNIN -->
	<form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
		<button type="submit" >twitter</button>
	</form>

	<!-- FACEBOOK SIGNIN -->
	<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
      <input type="hidden" name="scope" value="email,publish_stream,offline_access" />
		<button type="submit" >facebook</button>
	</form>

	<!-- LINKEDIN SIGNIN -->
	<form name="li_signin" id="li_signin" action="<c:url value="/signin/linkedin"/>" method="POST">
		<button type="submit">LinkedIn</button>
	</form>
	<p>Or you can <a href="<c:url value="/signup"/>">signup</a> with a new account.</p>
	
</div>