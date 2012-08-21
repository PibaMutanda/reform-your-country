<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html >
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/test-style.css" />
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/template.css" />
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/content.css" />
<!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->



<!-- for imageupload page -->

<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<!-- jQuery UI styles -->
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css" id="theme">
<!-- jQuery Image Gallery styles -->
<link rel="stylesheet" href="http://blueimp.github.com/jQuery-Image-Gallery/css/jquery.image-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the jQuery UI progress bars -->
<link rel="stylesheet" href="css/jquery.fileupload-ui.css">
<!-- Generic page styles -->
<link rel="stylesheet" href="css/style.css">



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
					<decorator:body />
				</div>
			</div>
			<div class="footer-template">
				<%@ include file="/WEB-INF/includes/footer.jsp"%>
			</div>
		</div>
</body>
</html>