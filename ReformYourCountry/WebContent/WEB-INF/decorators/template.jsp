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

<%-- IE7 not supported image --%>
<!--[if lt IE 8]><div style='background-color:orange;'>Vous utilisez une version préhistorique d'Internet Explorer (qui n'est plus utilisée que par une très infime fraction de la population). Ce site ne s'affichera pas correctement avec votre version. Merci de la mettre à jour vers une version plus récente, ou bien d'utiliser un navigateur moderne tel que Chrome ou Firefox.</div><![endif]-->

<div id="wrapper">
	<%@ include file="/WEB-INF/includes/header.jsp"%>
	
	<div id="main">
		<div class="main-area">
				<table>   <!-- divs with display:inline-block do not work for IE7-. There are hacks but mess the code up. A Table is just simple. http://giveupandusetables.com/ -->
				  <tr style="vertical-align:top;">
				    <td style="padding:0">
						<!-- ***************** - START Left list of articles - ***************** -->
						<div id="sub_nav">
						  <ryc:articlesNavBar/>
						  <img src="images/kidsdrawingbig.png"
						      style="position:absolute; bottom:0;" />  <%-- To have it at the bottom of the div (will be cut if the main area is not long enough --%>
						</div>
						<!-- ***************** - END Left list of articles - ***************** -->
				    </td>
				    <td style="padding:0">	 					<!-- ***************** - START Content - ***************** -->
						<div id="content">
						         <decorator:body />
							<br class="clear" />				
						</div><!-- end content -->
						<!-- ***************** - END content - ***************** -->
				    </td>
				  <tr>
				</table>
		</div><!-- main-area -->
    </div>
	

	<%@ include file="/WEB-INF/includes/footer.jsp"%>

</div><!--  end wrapper -->
</body>
</html>