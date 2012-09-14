<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html >
<html>
<head>
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
<title><decorator:title/></title>
<decorator:head/>
</head>

<body>
		<div class="header-template">
			<%@ include file="/WEB-INF/includes/header.jsp"%>
		</div>
		<div class="container-template">
			<div>
				<div class="menu-template">
					<%@ include file="/WEB-INF/includes/menu.jsp"%>
				</div>
				<div class="body-template">
				<input type="hidden" name="message" value="${message }">
			
				    <div>${message}<br/></div>
					<decorator:body />
				</div>
			</div>
			<div class="footer-template">
				<%@ include file="/WEB-INF/includes/footer.jsp"%>
			</div>
		</div>
</body>
</html>