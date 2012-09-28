<%@ page language="java" contentType="text/html;"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html>
<html>
<head>
	<base href="${pageContext.request.contextPath}/"/>
	
	<meta charset="UTF-8" />
	
	<%@ include file="/WEB-INF/includes/import.jsp"%>
	
	<decorator:head />
	
	<link rel="shortcut icon" href="#"/>
	<title><decorator:title /></title>
</head>
<body>
<div id="wrapper">
	<%@ include file="/WEB-INF/includes/header.jsp"%>
	<div id="main">
		<div class="main-area">
			<div style="padding-left:160px; padding-top:0px; " >
				<img src="images/_global/topline.png" width="1px"height="30px"/>
			</div>
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
	
<!-- ******************* BETA ************************ -->	
<div style= "position:absolute; top:10px;right:10px;" class="tooltip" data-tooltip="${tooltip}">
	<img src= "images/beta_sign.jpg" alt= "Plate-forme en construction, pas encore destinée au grand public. Sortie prévue: janvier 2013"
	title="Plate-forme en construction, pas encore destinée au grand public. Sortie prévue: janvier 2013"/>
</div>

</body>
</html>