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
	
	<%-- Google analytics - code copy/pasted from google-analytics 
	     This is asynchronous analytics => it should not hurt performance to place it here on top of the page. 
	     https://developers.google.com/analytics/devguides/collection/gajs/asyncTracking?hl=fr
	     --%>
	<script type="text/javascript">
       var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-35696677-1']);
	  _gaq.push(['_setDomainName', 'enseignement2.be']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	</script>

</head>


<body>

<%-- IE7 not supported image --%>
<!--[if lt IE 8]><div style='background-color:orange;'>Vous utilisez une version préhistorique d'Internet Explorer (qui n'est plus utilisée que par une très infime fraction de la population). Ce site ne s'affichera pas correctement avec votre version. Merci de la mettre à jour vers une version plus récente, ou bien d'utiliser un navigateur moderne tel que Chrome ou Firefox.</div><![endif]-->


    
<div id="wrapper">  <%-- for the top bg image --%>
<div id="wrapper2"> <%-- for the bottom bg image --%>

  <div class="wrapper-center-tight" style="padding-bottom:28px;">
	<%@ include file="/WEB-INF/includes/header.jsp"%>
  </div>
  	
  <div id="main-wrapper">  <%-- Wider than wrapper-center-tight, to include the background page borders repeated. --%>
  <div id="main-wrapper2">
		<div id="main">
		        <!-- divs with display:inline-block do not work for IE7-. There are hacks but mess the code up. A Table is just simple. http://giveupandusetables.com/ -->
				<table  >   
				  <tr style="vertical-align:top;">
				    <td style="padding:0;">
						<!-- ***************** - START Left list of articles - ***************** -->
						<div id="sub_nav" style="margin-top:-34px;">
						  <ryc:articlesNavBar/>
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
   </div> <%-- main wrapper 2 --%>
   </div> <%-- main wrapper --%>
	

  <div class="wrapper-center-tight">
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
  </div>
  
</div><%-- wrapper 2 --%>
</div><!--  end wrapper -->
</body>
</html>