<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="reformyourcountry.web.Current" %>

<!-- ***************** - Top Footer - ***************** --> 
<div id="footer">
<div class="footer-area">
<div class="footer-wrapper">
<div class="footer-holder">


<!-- ***************** - Footer Content Starts Here - *****************  -->
<div class="one_fourth">
	 <h3>Utilisateurs</h3>
	 <div class="footer_post">
		 <ul class="list sitemap-list">
		 <li><a href="grouplist"><span>Groupes</span></a></li>
			<c:if test="${current.user!=null}"><li><a href="user/${current.user.userName}"><span>Mon profil</span></a></li></c:if>
			<li><a href="user"><span>Autres utilisateurs</span></a></li>
			<li><a href="/specialuserslist"><span>Partis politiques et Associations</span></a></li>
			<li><a href="/badge/"><span>Gommettes</span></a>
		 
		 </ul>
	</div><!-- end footer_post -->
</div><!-- end first one_fourth_column -->

<div class="one_fourth">
	<h3>Contenu</h3>
	<div class="footer_post">
	     <ul class="list sitemap-list">
	     <li><a href="/article">Articles</a></li>
	     <li><a href="/action">Actions</a></li>
	     <li><a href="/book">Bibliographie</a></li>
	     </ul> 
	</div>
 </div><!-- end second one_fourth_column -->

<div class="one_fourth">
	<h3>A propos</h3>
	<div class="footer_post">
		<ul class="list sitemap-list">
			 <li><a href="/about/about-us.jsp">Qui sommes nous?</a></li>
			 <li><a href="/about/pourquoi.jsp">Pourquoi ce site?</a></li>
			 <li><a href="/fonctionnalites">Fonctionnalités</a></li>
			 <li><a href="/about/contribuer.jsp#">Comment contribuer?</a></li>
			 <li><a href="/contact">Contactez-nous</a></li>
		</ul>	
	</div><!-- end footer_post -->
</div><!-- end third one_fourth_column -->

<div class="one_fourth_last">
	<h3>Follow Us</h3>
	<ul class="social_icons">
<!-- 		<li><a href="#" onclick="window.open(this.href);return false;" class="rss">rss</a></li> -->
<!-- 		<li><a href="http://www.twitter.com/truethemes" class="twitter" onclick="window.open(this.href);return false;">Twitter</a></li> -->
<!-- 		<li><a href="#" class="facebook" onclick="window.open(this.href);return false;">Facebook</a></li> -->
<!-- 		<li><a href="#" class="flickr" onclick="window.open(this.href);return false;">Flickr</a></li> -->
		<li><a href="${p_webSite_youtubeaddress}" class="youtube" onclick="window.open(this.href);return false;">YouTube</a></li> 
<!-- 		<li><a href="#" class="linkedin" onclick="window.open(this.href);return false;">LinkedIn</a></li> -->
<!-- 		<li><a href="#" class="foursquare" onclick="window.open(this.href);return false;">FourSquare</a></li> -->
<!-- 		<li><a href="#" class="delicious" onclick="window.open(this.href);return false;">Delicious</a></li> -->
<!-- 		<li><a href="#" class="digg" onclick="window.open(this.href);return false;">Digg</a></li> -->
	</ul>
</div><!-- end fourth one_fourth_column -->


<!-- ***************** - END Footer Content - *****************  -->
</div><!-- footer-holder -->
</div><!-- end footer-wrapper -->
</div><!-- end footer-area -->


<!-- /***************** - Bottom Footer - *****************  -->
<div id="footer_bottom">
 <div class="info" >
 
 	<a rel="license" href="/about/copyright.jsp" title="Cette œuvre est mise à disposition selon les termes de la Licence Creative Commons Attribution 2.0 Belgique.">
 		<img alt="Licence Creative Commons" style="border-width:0" src="http://i.creativecommons.org/l/by/2.0/be/80x15.png" />
 	</a>
<!--  	http://creativecommons.org/licenses/by/2.0/be/ -->
<%--    2012 ${p_website_name} - ${p_version}  --%>
 </div><!-- end info -->
</div><!-- end footer_bottom -->


<!-- /***************** - END Bottom Footer - *****************  -->
</div><!-- end footer -->


<!-- /***************** - END Top Footer Area - *****************  -->