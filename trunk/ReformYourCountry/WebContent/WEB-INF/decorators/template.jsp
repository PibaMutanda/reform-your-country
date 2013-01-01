<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="reformyourcountry.web.UrlUtil" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<!DOCTYPE html>
<html lang="fr">
<head>
	<base href="<%= UrlUtil.getAbsoluteUrl("") %>"/>
	<meta charset="UTF-8" />
	
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /><%-- force IE to doesn't use is compataibility mode--%>
    
	<meta name="viewport" content="width=device-width"/><%--Define the base-width as the screen width --%>
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
				<!-- ***************** - START Left list of articles - ***************** -->
				<div id="sub_nav">
				  <ryc:articlesNavBar/>
				  <img src="images/kidsdrawingbig.png"
				      style="position:absolute; bottom:0;" />  <%-- To have it at the bottom of the div (will be cut if the main area is not long enough --%>
				</div>
				<!-- ***************** - END Left list of articles - ***************** -->
				<!-- ***************** - START Content - ***************** -->
				<div id="content">
				         <decorator:body />
					<br class="clear" />				
				</div><!-- end content -->
				<!-- ***************** - END content - ***************** -->
			</div><!-- end main-holder -->
		</div><!-- main-area -->
	<div class="mainbottom">
		<div class="main-ar">

			<div style="padding-left:160px; bottom:0px" >
				<img src="images/_global/bottomline.png" width="1px"height="48px"/>
			</div>
			</div>
	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
</div>
	

</div><!--  end wrapper -->
</body>
</html>