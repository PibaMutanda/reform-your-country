<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 
<%@ page import="reformyourcountry.web.Current" %>
<%@ page import="reformyourcountry.util.NotificationUtil" %>

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
<script type="text/javascript" src="js/int/votelistaction.js"></script>
<!-- CU3ER content JavaScript part ends here   -->



<title>${p_home_title}</title>
</head>
<body>


<!-- CU3ER content HTML part starts here   -->
<div id="CU3ER-container" style="vertical-align:top; display: inline-block;" >
	
		<div id="CU3ER"  >
		
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



<div style="height:311px; width:349px; vertical-align: top; display: inline-block; margin-left:-4px">
			    <table style="border-spacing:0px;">   <%-- Table with 3 cells: left (border image), center (text), right (border image) --%>
				 <tr> <%-- First row = top background image --%>
				    <td style="background-image:url(/images/paper/homepaperheader.png); background-repeat: repeat-x; 
			                width:349px; height:30px;  <%-- must be the same width as the bg image. --%> 
			                padding:0; " colspan="2"></td>
				 </tr> 
			     <tr>
			       <td style="height:227px; padding:23px; background-color: #F8F8F8; vertical-align:top;
			           background-image:url(/images/2grey.png); background-repeat: no-repeat; 
			           background-position-x: 212px; background-position-y:127px;">

			          ${p_home_body_text}
			          
				   </td>
			   	   <%-- right border --%>
			       <td style="background-image:url(/images/paper/summaryright.png); background-repeat: repeat-y; width:5px; padding:0"></td>
 			     </tr>
				 <tr> <%-- Last row = bottom background image --%>
				    <td style="background-image:url(/images/paper/homepaperfooter.png); background-repeat: repeat-x; 
			                height:12px;  <%-- must be the same width as the bg image. --%> 
			                padding:0; " colspan="2"></td>
				 </tr> 
				</table>						
			
</div>
<br/>

<%-- VIDEOs --%>
<%@include file="videocarrousel.jsp"%>
<%-- END VIDEOS --%>

<br/>
<ryctag:separator/>
<br/><br/>

<div style="width:100%; ">

 <%-- LEFT COLUMN --%>
 <div style="width:49%; display:inline-block; vertical-align:top;"> 
	<ryctag:frame1tape title="Article publiés dernièrement">	  
	  <c:forEach items="${articleListByDate}" var="art">
			 <div style="margin-bottom:15px;">
					 <div style= "float:right">
						 <span class="list-date">${art.difference}</span>
					 </div>
					 <a href= "/article/${art.article.url}" class="list-title">${art.article.title}</a>
					 
					 <c:if test="${art.article.description != null}">
				        <div class="list-description" style="padding-top:3px;">
					    	${art.article.description}
					    </div>							
					 </c:if>
			 </div>
	  </c:forEach> 
    </ryctag:frame1tape>
    
    <br/><br/>

	<ryctag:frame1tape title="Bons exemples récents">    
	  <c:forEach items="${goodExampleListByDate}" var="goodex">
			 <div style="margin-bottom:15px;">
	 			<div style="float:right;">
					 <span class="list-date">${goodex.difference}</span>
				</div>
	 			
	 			<a href="/goodexample/single/${goodex.goodExample.id}" class="list-title">${goodex.goodExample.title}</a>
	 		</div>
	  </c:forEach>
	  
	  <div style="text-align:right; font-size:0.8em; margin-bottom:-13px;">
	     <a href="/goodexample" title="voir les autres bon exemples, par date de création">plus</a>
	  </div>
    </ryctag:frame1tape>
    
 </div>



 <%-- RIGHT COLUMN --%>
 <div style="width:49%;  display:inline-block; vertical-align:top;">
      <ryctag:frame2tapes title="Actions créées dernièrement">
		 
		    <c:forEach items="${actionListByDate}" var="act">
			   <div style="margin-bottom:15px;">
					 <div style="padding-bottom:9px;">
						  <a href ="/action/${act.action.url}" class="list-title">${act.action.title}</a><br/>
						  <c:if test="${act.action.shortDescription != null}">
						      <div class="list-description" style="padding-top:3px;">
									${act.action.shortDescription}					
							  </div>	
						  </c:if>
					 
					 </div>
					 
					 <div style="text-align:right; padding-bottom:5px;">
						 <span class="list-date">${act.difference}</span>
			 		     <div style="float:left;">
							<c:set var="vote" value="${act.voteAction}" scope="request"/>
							<c:set var="id" value="${act.action.id}" scope="request" />
					     	<%@include file="voteactionwidget.jsp"%>
					     </div>	
					 </div>
				</div>
		   </c:forEach>
		   
		   <div style="text-align:right; font-size:0.8em;">
	          <a href="/action" title="lister toutes les actions déjà créées">plus</a>
	       </div>
		   
       </ryctag:frame2tapes>		   
   
		   
 </div>
 

</div>

</body>
</html>