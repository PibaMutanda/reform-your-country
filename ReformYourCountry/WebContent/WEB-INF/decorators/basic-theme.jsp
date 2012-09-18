<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<base href="/ReformYourCountry/">
<meta charset="UTF-8">

<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->

<!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<meta name="viewport" content="width=device-width">

<link rel="stylesheet" type="text/css" href="css/ryc-style.css" />
<link rel="stylesheet" type="text/css" href="css/template.css" />
<link rel="stylesheet" type="text/css" href="css/content.css" />

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




<div class="header-holder">
<div class="rays">
<div class="header-area">







<!-- ***************** - Main Navigation - ***************** -->

<!-- ***************** - LOGO - ***************** -->

<div class="logodiv"  onclick="location.href='home';"><img src="images/logo/enseignement2-logo-white-small.png"/></div>
<!-- ***************** - END LOGO - ***************** -->
<ul id="menu-main-nav">
<li><a href="content-3d-effects.html"><span><strong>Features</strong><span class="navi-description">about this theme</span></span></a>
<ul class="sub-menu">
	<li><a href="content-3d-effects.html"><span>Amazing 3D Effects</span></a></li>
	<li><a href="content-code-snippets.html"><span>100+ Code Snippets</span></a></li>
    <li><a href="content-pricing-tables.html"><span>Pricing Tables</span></a></li>
	<li><a href="content-20-page-layouts.html"><span>20 Page Layouts</span></a></li>
	<li><a href="content-20-color-variations.html"><span>20 Color Variations</span></a></li>
	<li><a href="content-ajax-contact-form.html"><span>AJAX + PHP Contact Form</span></a></li>
	<li><a href="content-top-notch-support.html"><span>Top Notch Customer Support</span></a></li>
	</ul>
</li>

<li class="current_page_item"><a href="index-jquery-2.html"><span><strong>Pages</strong><span class="navi-description">layout templates</span></span></a>
<ul class="sub-menu">
 <li><a href="index-jquery-2.html"><span>Homepage &#8211; jQuery 2</span></a></li>
 <li><a href="index-video-left.html"><span>Homepage &#8211; Video Left</span></a></li>
 <li><a href="index-video-right.html"><span>Homepage &#8211; Video Right</span></a></li>
 <li><a href="index-3d-1.html"><span>Homepage &#8211; 3D 1</span></a></li>
 <li><a href="index-3d-2.html"><span>Homepage &#8211; 3D 2</span></a></li>
 <li class="current-menu-item"><a href="template-leftnav.html"><span>Left Nav</span></a></li>
 <li><a href="template-leftnav-sidebar.html"><span>Left Nav + Sidebar</span></a></li>
 <li><a href="template-leftsidebar.html"><span>Left Sidebar + No Nav</span></a></li>
 <li><a href="template-leftsidebar-horizontal-nav.html"><span>Left Sidebar + Horizontal Nav</span></a></li>
 <li><a href="template-rightnav.html"><span>Right Nav</span></a></li>
 <li><a href="template-rightnav-sidebar.html"><span>Right Nav + Sidebar</span></a></li>
 <li><a href="template-rightsidebar.html"><span>Right Sidebar + No Nav</span></a></li>
 <li><a href="template-rightsidebar-horizontal-nav.html"><span>Right Sidebar + Horizontal Nav</span></a></li>
 <li><a href="template-fullwidth.html"><span>Full Width + No Nav</span></a></li>
 <li><a href="template-fullwidth-horizontal-nav.html"><span>Full Width + Horizontal Nav</span></a></li>
 <li><a href="template-contact-iphone.html"><span>Contact Page (iPhone)</span></a></li>
 <li><a href="template-contact-google-map.html"><span>Contact Page (Google map)</span></a></li>
 <li><a href="template-sitemap.html"><span>Sitemap</span></a></li>
 <li><a href="template-404error.html"><span>404 Error Page</span></a></li></ul>
</li>




<li class="login-link">
      <c:choose>
        <c:when test="${current.user!=null}">
          <a id="logout" href="logout"><span><strong>Déconnexion</strong></span></a>
          <a><span><c:out value="${current.user.userName}"></c:out></span></a><br />
        </c:when>
        <c:otherwise>
            <a class="login" style="cursor:pointer;"><span><strong>Connexion</strong></span></a><br />
            <a href="register"><span>Créer un compte</span></a>
         </c:otherwise>
     </c:choose>
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
</li></ul>
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
<p class="breadcrumb"><a href="Home">Home</a><a href="Home">Pages</a><span class='current_crumb'>Accueil</span></p></div><!-- end frame -->

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