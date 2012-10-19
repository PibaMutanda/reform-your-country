<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<%@page import="reformyourcountry.web.Current" %>

<html>
<head>
<meta name="Description" lang="${p_lang}" content="${p_home_description}" />
<meta name="Keywords" content="${p_home_keywords}" />
<meta name="robots" content="index, follow"/>
<link rel="canonical" href="${p_webSite_Adress}"/>
<meta name="googlebot" content="noarchive" />

<!-- CU3ER content JavaScript part starts here   -->
<script type="text/javascript" src="js/ext/swfobject.js"></script>
<script type="text/javascript" src="js/ext/CU3ER.js"></script>
<script type="text/javascript" src="js/ext/home.js"></script>
<!-- CU3ER content JavaScript part ends here   -->
<title>${p_home_title}</title>
</head>
<body>
<!-- ***************** - Homepage 3D Slider - ***************** --><!-- locations where are the pictures -->

<div id="CU3ER-container"class="div-align-center" >
	<!-- CU3ER content HTML part starts here   -->
		<div id="CU3ER" >
		
			<!-- modify this content to provide users without Flash or enabled Javascript 
			   with alternative content information -->
			<p>
			Click to get Flash Player<br />
			<a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a>
			</p>
			<p>or try to enable JavaScript and reload the page</p>
		</div>
</div>

<!-- ***************** - END Homepage 3D Slider - ***************** -->
<div class="callout-wrap">
	<p>
Lorem ipsum dolor sit amet, consectetaur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sitriam.
</p>
</div>

<div style="width:450px;"> 
<div class="h7">
Articles dernièrement publiés

</div>
	<c:forEach items="${articleListByDate}" var="art">
		<div class ="listarticle" >
			<div style= "float:left">
				<a href= "/article/${art.article.url}">${art.article.title}</a><br/>
				
					<c:choose>
						<c:when test="${art.article.description != null}">
							${art.article.description}						
						</c:when>
						<c:otherwise>
							Pas de description disponible pour cet article.
						</c:otherwise>
					</c:choose>
				
			</div>
			<div style= "float:right">
				<span>${art.difference}</span>
			</div>
		</div>
	</c:forEach>
</div>


</body>
</html>