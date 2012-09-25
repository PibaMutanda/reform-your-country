<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="reformyourcountry.web.ContextUtil" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

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

<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->

<!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<meta name="viewport" content="width=device-width"/>

<link rel="stylesheet" type="text/css" href="css/ryc-style.css" />
<link rel="stylesheet" type="text/css" href="css/template.css" />
<link rel="stylesheet" type="text/css" href="css/content.css" />

<!-- <link href="/favicon.ico" rel="shortcut icon" type="image/x-icon" /> --><%--maybe must we put an ie condition --%>
<link rel="icon" type="image/png" href="images/favicon.png"/>

<link rel="stylesheet" href="css/jquery-ui-1.8.23.custom.css"/><%-- Jquery added here because they're used inside custom tags, done so to ease maintenance--%>
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

<!-- ***************** - LOGO - ***************** -->

<div class="logodiv" ><a href="home"><img src="images/logo/enseignement2-logo-white-small.png" style="display: block; "/></a>
<div style="color:#999999; font-size:10px; padding-top:16px;">Analyse ind&eacute;pendante de l'enseignement en Wallonie-Bruxelles.</div></div>
<!-- ***************** - END LOGO - ***************** -->	

<div style="float:right">
<div style="width:100%; ">
  	<div class="login-link">
	      <c:choose>
	        <c:when test="${current.user!=null}">
	          <a id="logout" href="logout">Déconnexion</a>
	          <a><c:out value="${current.user.userName}"></c:out></a>
	        </c:when>
	        <c:otherwise>
	            <a class="login" style="cursor:pointer;">Connexion</a>&nbsp;&nbsp;&nbsp;
	            <a href="register">Cr&eacute;er un compte</a>
	         </c:otherwise>
	     </c:choose>
	
	</div>
</div>


<!-- ***************** - Main Navigation - ***************** -->
<div>
<div style="float:left;">
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
		
	</li>

</ul>
</div>
<div style="float:right;padding-top: 30px;">
<form method="get" id="searchform" action="#" class="search-form">
		<fieldset>
		<span class="text">
		 <input type="submit" class="submit" value="search" id="searchsubmit" />
		 <input type="text" name="s" id="s" value="Rechercher" onfocus="this.value=(this.value=='Rechercher') ? '' : this.value;" onblur="this.value=(this.value=='') ? 'Rechercher' : this.value;" />
		</span>
		</fieldset>
		</form>
</div>
</div>
<!-- ***************** - END Main Navigation - ***************** -->
<!-- Hidden div that JavaScript will move in a dialog box when we press the login link -->
<div id ="logindialog" style = "display:none;">
 
</div>

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

</div><!-- end sub_nav -->

<!-- ***************** - END sub-nav - ***************** -->


<!-- ***************** - START Content - ***************** -->
<div id="content">

<decorator:body />

<br class="clear" />


</div><!-- end content -->

<!-- ***************** - END content - ***************** -->


		
</div><!-- end main-holder -->
</div><!-- main-area -->

<%@ include file="/WEB-INF/includes/footer.jsp"%>
</div>
</div>
<script type="text/javascript" src="js/jquery.cycle.all.min.js"></script>
<script type="text/javascript" src="js/jquery-1-slider.js"></script>
<script type="text/javascript" src="js/testimonial-slider.js"></script>
</body>
</html>