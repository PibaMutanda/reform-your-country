<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="reformyourcountry.web.ContextUtil" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<!-- <!DOCTYPE html > -->
<!-- <html> -->
<!-- <head> -->

<!-- </head> -->

<!-- <body> -->
<!-- 		<div class="header-template"> -->
<%-- 			<%@ include file="/WEB-INF/includes/header.jsp"%> --%>
<!-- 		</div> -->
<!-- 		<div class="container-template"> -->
<!-- 			<div> -->
<!-- 				<div class="menu-template"> -->
<%-- 					<%@ include file="/WEB-INF/includes/menu.jsp"%> --%>
<!-- 				</div> -->
<!-- 				<div class="body-template"> -->
<%-- 				<input type="hidden" name="message" value="${message }"> --%>
			
<%-- 				    <div>${message}<br/></div> --%>
<%-- 					<decorator:body /> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="footer-template"> -->
<%-- 				<%@ include file="/WEB-INF/includes/footer.jsp"%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- </body> -->
<!-- </html> -->



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">
<head>
<c:choose>
			<c:when test="<%= ContextUtil.devMode %>">				
				<base href="/ReformYourCountry/"/>
			</c:when>
  		    <c:otherwise>
			    <base href="/"/>
			</c:otherwise>
		</c:choose>
<meta charset="UTF-8"/>

<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->

<!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<meta name="viewport" content="width=device-width"/>

<link rel="stylesheet" type="text/css" href="css/ryc-style.css" />
<link rel="stylesheet" type="text/css" href="css/template.css" />
<link rel="stylesheet" type="text/css" href="css/content.css" />
<link href="/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link rel="stylesheet" href="css/jquery-ui-1.8.23.custom.css"><%-- Jquery added here because they're used inside custom tags, done so to ease maintenance--%>
<script src="js/ext/jquery-1.8.0.min.js"></script><%-- jquery depandencies --%>
<script src="js/ext/jquery-ui-1.8.23.custom.min.js"></script><%-- jquery depandencies --%>
<script src="js/int/datepicker.js" type="text/javascript"></script>
<script src="js/int/login.js"></script>
<script src="js/int/redirect.js"></script>
 <script src="js/int/focusevent.js"></script>
<title><decorator:title/></title>
<decorator:head/>
<!--[if lte IE 8]><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /><![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/karma.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/karma-royal-blue.css" rel="stylesheet" type="text/css" />
<link href="css/secondary-royal-blue.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="#"/>
<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="css/lt8.css" media="screen"/><![endif]-->
</head>
<body>
<div id="wrapper">
<div id="header">

	<div class="login-link">
	      <c:choose>
	        <c:when test="${current.user!=null}">
	          <a id="logout" href="logout">Déconnexion</a>
	          <a><c:out value="${current.user.userName}"></c:out></a>
	        </c:when>
	        <c:otherwise>
	            <a class="login" style="cursor:pointer;">Connexion</a>&nbsp;&nbsp;&nbsp;
	            <a href="register">Créer un compte</a>
	         </c:otherwise>
	     </c:choose>
	
	</div>


<div class="header-holder">
<div class="rays">
<div class="header-area">







<!-- ***************** - Main Navigation - ***************** -->

<!-- ***************** - LOGO - ***************** -->

<div class="logodiv" ><a href="home"><img src="images/logo/enseignement2-logo-white-small.png"/></a></div>
<!-- ***************** - END LOGO - ***************** -->
<ul id="menu-main-nav">
	<li><a href="content-3d-effects.html"><span><strong>UTILISATEURS</strong>
	<span class="navi-description">utilisateurs actifs</span></span></a>
		<ul class="sub-menu">
			<li><a href="content-code-snippets.html"><span>groupes</span></a></li>
		</ul>
	</li>
	
	<li ><a href="index-jquery-2.html"><span><strong>Bibliographie</strong>
		<span class="navi-description">voir liste des livres</span></span></a>

	</li>
	<li ><a href="index-jquery-2.html"><span><strong>A propos</strong><span class="navi-description">Qui sommes nous?</span></span></a>
		<ul class="sub-menu">
			 <li><a href="index-jquery-2.html"><span>Pourquoi ce site?</span></a></li>
			 <li><a href="index-video-left.html"><span>Fonctionnalités</span></a></li>
			 <li><a href="index-video-right.html"><span>Comment contribuer?</span></a></li>
			 <li><a href="index-3d-1.html"><span>Contactez-nous</span></a></li>
		 </ul>
	</li>
	
	
	
	
	
	
	
	<li>
		<form method="get" id="searchform" action="#" class="search-form">
		<fieldset>
		<span class="text">
		 <input type="submit" class="submit" value="search" id="searchsubmit" />
		 <input type="text" name="s" id="s" value="Search" onfocus="this.value=(this.value=='Search') ? '' : this.value;" onblur="this.value=(this.value=='') ? 'Search' : this.value;" />
		</span>
		</fieldset>
		</form>
	</li>

</ul>


<!-- ***************** - END Main Navigation - ***************** -->
<!-- Hidden div that JavaScript will move in a dialog box when we press the login link -->
<div id ="logindialog" style = "display:none;">
 
</div>



</div><!-- header-area -->

</div><!-- end rays -->
</div><!-- end header-holder -->
</div><!-- end header -->
 
 

 
<div id="main">
<div class="main-area">






<div class="main-holder">

<!-- ***************** - START sub-nav - ***************** -->
<div id="sub_nav">
<ryc:articlesTree link="true" />
<ul class="sub-menu">
<li class="current_page_item"><a href="#"><span>Left Nav</span></a></li>
<li><a href="template-leftnav-sidebar.html"><span>Left Nav + Sidebar</span></a></li>
<li><a href="template-leftsidebar.html"><span>Left Sidebar + No Nav</span></a></li>
<li><a href="template-leftsidebar-horizontal-nav.html"><span>Left Sidebar + Horizontal Nav</span></a></li>
<li><a href="template-rightnav.html"><span>Right Nav</span></a></li>
<li><a href="template-rightnav-sidebar.html"><span>Right Nav + Sidebar</span></a></li>
<li><a href="template-rightsidebar.html"><span>Right Sidebar + No Nav</span></a></li>
<li><a href="template-rightsidebar-horizontal-nav.html"><span>Right Sidebar + Horizontal Nav</span></a></li>
<li><a href="template-fullwidth.html"><span>Full Width + No Nav</span></a></li>
<li><a href="template-fullwidth-horizontal-nav.html"><span>Full Width + Horizontal Nav</span></a></li></ul>
</div><!-- end sub_nav -->

<!-- ***************** - END sub-nav - ***************** -->


<!-- ***************** - START Content - ***************** -->
<div id="content">

<!-- ***************** - START Title Bar - ***************** -->
<div class="tools">
<div class="holder">
<div class="frame">
<h1>Accueil</h1>



<!--|||||||||||||||||||||||||||     Ne sera utilie que pour les articles               |||||||||||||||||||||||||||||-->
<p class="breadcrumb"><a href="home">Home</a><a href="home">Pages</a><span class='current_crumb'>Accueil</span></p></div><!-- end frame -->

</div><!-- end holder -->
</div><!-- end tools -->
<!-- ***************** - END Title Bar - ***************** -->
<div class="body-template">
				<input type="hidden" name="message" value="${message }">
			
				    <div>${message}<br/></div>
					<decorator:body />
</div>
<br class="clear" />


</div><!-- end content -->

<!-- ***************** - END content - ***************** -->


		
</div><!-- end main-holder -->
</div><!-- main-area -->






<!-- ***************** - Top Footer - ***************** --> 
<div id="footer">
<div class="footer-area">
<div class="footer-wrapper">
<div class="footer-holder">


<!-- /***************** - Footer Content Starts Here - ***************** --> 
<div class="one_fourth">
 <h3>Utilisateurs</h3>
 <div class="footer_post">
 <ul class="list sitemap-list">
 <li><a href="userlist">utilisateurs actifs</a>
 <li><a href="grouplist">groupes</a></ul>
</div><!-- end footer_post -->
</div><!-- end first one_fourth_column -->

<div class="one_fourth">
 <h3>Bibliographie</h3>
 
</div><!-- end second one_fourth_column -->

<div class="one_fourth">
<h3>A propos</h3>
<div class="footer_post">
<ul class="list sitemap-list">
 <li><a href="#">Qui sommes nous?</a>
 <li><a href="#">Pourquoi ce site?</a>
 <li><a href="#">fonctionnalités</a>
 <li><a href="#">Comment contribuer?</a>
 <li><a href="#">Contactez-nous</a>
</div><!-- end footer_post -->
</div><!-- end third one_fourth_column -->

<div class="one_fourth_last">
<h3>Follow Us</h3>
<ul class="social_icons">
<li><a href="#" onclick="window.open(this.href);return false;" class="rss">rss</a></li>
<li><a href="http://www.twitter.com/truethemes" class="twitter" onclick="window.open(this.href);return false;">Twitter</a></li>
<li><a href="#" class="facebook" onclick="window.open(this.href);return false;">Facebook</a></li>
<li><a href="#" class="flickr" onclick="window.open(this.href);return false;">Flickr</a></li>
<li><a href="#" class="youtube" onclick="window.open(this.href);return false;">YouTube</a></li>
<li><a href="#" class="linkedin" onclick="window.open(this.href);return false;">LinkedIn</a></li>
<li><a href="#" class="foursquare" onclick="window.open(this.href);return false;">FourSquare</a></li>
<li><a href="#" class="delicious" onclick="window.open(this.href);return false;">Delicious</a></li>
<li><a href="#" class="digg" onclick="window.open(this.href);return false;">Digg</a></li></ul>
</div><!-- end footer_post -->
</div><!-- end fourth one_fourth_column -->






<!-- ***************** - END Footer Content - ***************** --> 




</div><!-- footer-holder -->
</div><!-- end footer-wrapper -->
</div><!-- end footer-area -->
<!-- /***************** - Bottom Footer - ***************** --> 

<div id="footer_bottom">
 <div class="info" >
  <a href="#">2012 enseignement2.be - v0.0.1.</a> 
 </div><!-- end info -->
<!--</div><!-- end footer_bottom -->

<!-- /***************** - END Bottom Footer - ***************** --> 
</div><!-- end footer -->


<!-- /***************** - END Top Footer Area - ***************** --> 










</div>
<script type="text/javascript" src="js/jquery.cycle.all.min.js"></script>
<script type="text/javascript" src="js/jquery-1-slider.js"></script>
<script type="text/javascript" src="js/testimonial-slider.js"></script>
</body>
</html>