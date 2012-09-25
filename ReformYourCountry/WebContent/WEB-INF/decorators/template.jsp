<%@ page language="java" contentType="text/html;"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="reformyourcountry.web.ContextUtil" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html>
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
	<%@ include file="/WEB-INF/includes/header.jsp"%>
	<div id="main">
		<div class="main-area">
			<div class="main-holder">
				<!-- ***************** - START sub-nav - ***************** -->
				<div id="sub_nav">
				  <ryc:articlesTree link="true" />
				</div>
				<!-- ***************** - END sub-nav - ***************** -->
				<!-- ***************** - START Content - ***************** -->
				<div id="content">
					<div class="body-template">
				         <decorator:body />
					</div>
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