<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<%@page import="reformyourcountry.web.Current" %>
<%@page import="reformyourcountry.util.NotificationUtil" %>
<html>
<head>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<meta name="description" lang="${p_lang}" content="${p_home_description}" />
<meta name="keywords" content="${p_home_keywords}" />
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

<div id="CU3ER-container"  >
	<!-- CU3ER content HTML part starts here   -->
		
		<div style="width:300px; float:right; background:none;">
			${p_home_body_text}
		</div>
		
		<div id="CU3ER" style="width:49%;" >
		
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


<input type="submit" id="notificationButton" >

<div style="width:100%; ">

 <div style="width:49%; display:inline-block; vertical-align:top;"> 
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

 <div style="width:49%;  display:inline-block; vertical-align:top;">
    <div class="h7">
      Actions dernièrement crées
    </div>
    <c:forEach items="${actionListByDate}" var="act">
		 <div class="listaction">
 		     <div style="float:right;">
		     	<%@include file="voteactionwidget.jsp"%>
		     </div>	
			 <div >
				 <a href ="/action/${act.action.url}">${act.action.title}</a><br/>
				  <c:choose>
						<c:when test="${act.action.shortDescription != null}">
							${act.action.shortDescription}						
						</c:when>
						<c:otherwise>
							Pas de description disponible pour cet action.
						</c:otherwise>
				  </c:choose>
			 
			 </div>
			 <div style="text-align:right;">
				 <span>${act.difference}</span>
			 </div>
		</div>
   </c:forEach>
 </div>
 
 <div style="width:49%;">
 	<div class="h7">
 	  Bons examples dernièrement crées	
 	</div>
 	<c:forEach items="${goodExampleListByDate}" var="goodex">
 		<div class="listgoodexample">
 			<div style= "float:left">
 				<a href ="/goodexample/${goodex.goodexample.url}">${goodex.goodexample.title}</a><br/>
 				<c:choose>
						<c:when test="${goodex.goodexample.description != null}">
							${goodex.goodexample.description}						
						</c:when>
						<c:otherwise>
							Pas de description disponible pour ce bon example.
						</c:otherwise>
				  </c:choose>
 			</div>
 			<div style="float:right;">
				 <span>${goodex.difference}</span>
			 </div>
 		
 		</div>
 	
 	</c:forEach>
 </div>

</div>

</body>
</html>