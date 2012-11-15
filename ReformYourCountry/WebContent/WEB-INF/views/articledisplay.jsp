﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<link rel="stylesheet" href="css/ext/jquery-bubble-popup-v3.css"  type="text/css" />
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<link rel="stylesheet" href="css/ext/jquery.countdown.css" type="text/css"/>
<link href="css/ext/jsCarousel-2.0.0.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>
<script type="text/javascript" src="js/ext/jquery.countdown-fr.js"></script>
<script type="text/javascript" src="js/int/untranslate.js"></script>
<script src="js/ext/jsCarousel-2.0.0.js" type="text/javascript"></script>
<meta name="robots" content="index, follow"/>
<meta name="description" content="${article.description}"/>
<c:if test="${fn:length(videoList) gt 3}">
    <script type="application/javascript">
        $(document).ready(function() {
            $('#carouselh').jsCarousel({ autoscroll: true, circular: true, masked: false, itemstodisplay: 3, orientation: 'h' });
        });       
    </script>
</c:if>
 <script type="text/javascript">
 var addthis_config = {"data_track_clickback":true};
 </script> <script type="text/javascript"
 src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
</head>
<body>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
 <ryctag:pageheadertitle title="${article.title}">
 	<c:forEach items="${parentsPath}" var="subarticle">
		<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title}" />
 </ryctag:pageheadertitle>

   

	<div style="display:inline-block"><!-- DO NOT REMOVE OTHERWISE TITLE AND MENU ARE UPSIDE DOWN	 -->
	    <ryc:conditionDisplay privilege="EDIT_ARTICLE">
			<ul style="float:left">
						<li><a href="/article/a_classer/${article.url}">Afficher "A classer"</a></li>
					</ul>
			<div class="article-options">
				    <ul class="list sitemap-list">
						 <li><a href="/article/edit?id=${article.id}">Editer l'article</a></li>
						 <li><a href="/article/parentedit?id=${article.id}">Editer l'article parent</a></li>
						 <li><a href="/article/contentedit?id=${article.id}">Editer le contenu de l'article</a></li>
						 <li><a href="/article/summaryedit?id=${article.id}">Editer le résumé de l'article</a></li>
						 <li><a href="/article/toclassifyedit?id=${article.id}">Editer le contenu à classer de l'article</a></li>
						 <li><a href="/article/version/${article.url}">Afficher l'historique des versions</a></li>
						 <li><a href="/video/manager?id=${article.id}">Vidéo</a></li>
					</ul>	
			</div>
		</ryc:conditionDisplay>
		<div class="article-title" >
			<ryc:conditionDisplay privilege="EDIT_ARTICLE">
		    	<span class="tooltip" data-tooltip='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
			</ryc:conditionDisplay>	
		    <c:if test="${!article.publicView}">
				<p>Cet article n'est pas disponible au public.
		     	<c:choose>
		  	       <c:when test="${displayDate != null}"> 
	  	         	 Il sera publié le ${displayDate}.  
				   </c:when> 
				   <c:otherwise>
				      Sa date de publication est indéterminée.
				   </c:otherwise>
				 </c:choose>
				 </p>
		   </c:if>
		</div>
		
	</div>

<!-- ARTICLE CONTENT -->

    <c:choose>
  	  <c:when test="${showContent}">
  
  	  	
		<div class="article_content">
		  	  <ryc:conditionDisplay privilege="EDIT_ARTICLE">
		 		 <hr/>
			  </ryc:conditionDisplay>
			  <!-- AddThis Button BEGIN -->
				<div class="addthis_toolbox addthis_default_style ">
				<a class="addthis_button_facebook_like" fb:like:layout="button_count"></a>
				<a class="addthis_button_tweet"></a>
				<a class="addthis_button_google_plusone" g:plusone:size="medium"></a>
				<a class="addthis_counter addthis_pill_style"></a>
				</div>
			  <!-- AddThis Button END -->
				<div id="carouselh">
					<c:forEach items="${videoList}" var="video">
						<div class="inline-block">
							<iframe width="250" height="141" src="https://www.youtube-nocookie.com/embed/${video.idOnHost}?rel=0&hd=1" frameborder="0" allowfullscreen seamless></iframe>
						</div>
					</c:forEach>
				</div>
				<div class="article_summary"><div style="font-size:.85em;margin-bottom:10px;">RESUME</div>${article.lastVersionRenderdSummary}</div>
			   <hr/>
			  ${article.lastVersionRenderedContent}
	  	</div>
	  </c:when>
	
	  <c:otherwise> <%-- We do not show the text, but the countdown --%>
		<c:if test="${displayDate != null}">
  		  <br/><br/><br/>
          <!-- COUNT DOWN -->
		  <div   id="defaultCountdown" ></div>
			<script type="text/javascript">
			    $(document).ready(function () {
						var publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
						function reload() { 
							window.location.reload(); 
						} 
						$('#defaultCountdown').countdown(
								{until: publishDay, onExpiry:reload, format: 'dHMS',
								 layout: '<span style="font-size:100px;">{dn}</span> {dl} ,  &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{hn}</span> {hl}, &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{mn}</span> {ml} et  &nbsp; &nbsp; &nbsp;  <span style="font-size:100px;">{sn}</span> {sl}'}); 
				});
			</script>
			<br/><br/><br/> avant publication.
		</c:if>
	  
	  </c:otherwise>
</c:choose>

</body>
</html>   

