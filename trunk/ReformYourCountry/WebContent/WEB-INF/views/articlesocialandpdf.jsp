<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="addthis_toolbox addthis_default_style" style="padding-top:0px; padding-bottom:0px;">
				<!-- AddThis Button BEGIN -->
				 <div style="float:left">
   					<a class="addthis_button_facebook_like" fb:like:layout="button_count"></a>
					<a class="addthis_button_tweet"></a>
					<a class="addthis_button_google_plusone" g:plusone:size="medium"></a>
					<a class="addthis_counter addthis_pill_style"></a>
				 </div>
			    <!-- AddThis Button END -->
				 <div style="float:right;vertical-align:bottom">
				    <%-- These images launch a JavaScript function. --%>	
					<c:if test="${empty article.children}">
					  <img class="generatepdfarticle" style="cursor:pointer;text-align:right;" src="images/pdf_button.png" title="Générez un PDF pour lire/imprimer cet article"></img>
			    	</c:if>
				    <c:if test="${!empty article.children}">
					  <img class="generatepdfgroup" style="cursor:pointer;text-align:right;" src="images/pdf_button.png" title="Générez un PDF pour lire/imprimer cet article (et ses sous-articles)"></img>
				    </c:if>
				 </div>	
</div>
