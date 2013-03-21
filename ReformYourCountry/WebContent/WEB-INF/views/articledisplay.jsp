<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="reformyourcountry.web.UrlUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
 

<head>
<!-- code used for "i like facebook" -->
	 <meta property="og:title" content="${article.title}" /> 
     <meta property="og:type" content="article" /> 
     <meta property="og:url" content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/article/${article.title}" /> 
     <meta property="og:image" content="http://<%= UrlUtil.getProdAbsoluteDomainName()%>/images/logo/2-logo.png" /> 
     <meta property="og:site_name" content="<%= UrlUtil.getProdAbsoluteDomainName()%>" /> 
     <meta property="fb:admins" content="${user.id}" /> 
     <meta property="og:description"  content="${article.description}" />
     
<link rel="stylesheet" href="css/ext/jquery-bubble-popup-v3.css"  type="text/css" />
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<link rel="stylesheet" href="css/ext/jquery.countdown.css" type="text/css"/>
<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>
<script type="text/javascript" src="js/ext/jquery.countdown-fr.js"></script>
<script type="text/javascript" src="js/int/untranslate.js"></script>

<meta name="robots" content="index, follow"/>
<meta name="description" content="${article.description}"/>
<meta name="title" content ="${article.title}"/>

<script type="text/javascript">
 var addthis_config = {"data_track_clickback":true};
</script> <script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
 
 
</head>



<body>

<div style="
	background-image: url('/images/inkspot.png');
	background-repeat: no-repeat;
	background-position: 615px 100px;
">

<ryctag:breadcrumb>
	<c:forEach items="${parentsPath}" var="subarticle">
		<ryctag:breadcrumbelement label="${subarticle.title}" link="/article/${subarticle.url}" />
	</c:forEach>
	<ryctag:breadcrumbelement label="${article.title}" />
</ryctag:breadcrumb>

<ryc:conditionDisplay privilege="MANAGE_ARTICLE" >
    <%@include file="articlelinkmenu.jsp" %>	
</ryc:conditionDisplay>

<%@include file="articlesocialandpdf.jsp" %>

<ryctag:pageheadertitle title="${article.title}" />
  


<c:if test="${!article.publicView}">
  <div>
	<br/>
		<p>Cet article n'est pas encore publié.
	   	  <c:choose>
		       <c:when test="${displayDate != null}"> 
  	         	 Il sera publié le ${displayDate}.  
			   </c:when> 
			   <c:otherwise>
			      Sa date de publication est indéterminée.
			   </c:otherwise>
		   </c:choose>
		 </p>
		 
   </div>
</c:if>

<!-- ARTICLE CONTENT -->
<c:choose>
  	  <c:when test="${showContent}">
		<div class="article_content">

				<%@include file="videocarrousel.jsp"%>
				<c:if test="${fn:length(videoList) gt 0}"> 
					<br />
					<ryctag:separator />
					<br />
				</c:if>
				
				<%-- SUMMARY --%>  
				<ryctag:framelarge>
							<div class="article_summary" >
							
							    <%---------- List of actions --%>
								<div style="float:right; margin-right:-70px">  <%-- Negative right margin to make part of the background images overflow on the right --%>
									<div>
										<c:set var="actionItems" value="${actionItemsParent}" scope="request"/>
			 						    <%@include file="actionsummary.jsp"%>
			 						    
										<ryc:conditionDisplay privilege="MANAGE_ARTICLE">
											<div class="article-liens" >
												<div style="text-align:right;  margin-right:50px;">
													<a href="/articleactionlinkedit?id=${article.id}">éditer actions</a>
												</div>
											</div>
											<c:if test="${!article.children.isEmpty()}">
												
											</c:if>
										</ryc:conditionDisplay> 
										
										<div>
										<c:set var="actionItems" value="${actionItemsChildren}" scope="request"/>
										<%@include file="actionsummary.jsp"%>
										</div>
									</div>
									
									<%---------- List of arguments --%>
									
									<div class="goodexample-list">
									
									<div class="action-goodexample-title"  >
											bons exemples
										</div>
									
									<c:forEach items="${lastFiveExample}" var="example">
										<div>
									   	  <a href="/goodexample/${article.url}">${example.title}</a>
									    </div>
									</c:forEach>
			                        <div class="article-liens">
			                                <div style="text-align: right;">
			                                    <a href="/goodexample/${article.url}" style="text-align: rignt;">plus ... </a>
			                                </div>
			                            </div>
			                        </div>
								</div>
								
							    <%----------- Summary text --%>
							    <div style="font-size:1.15em;margin-bottom:10px; color:#bc1c1c;">RESUME</div>
								${article.lastVersionRenderdSummary}
						   </div>  <!--  article summary -->
					</ryctag:framelarge>					   
		    	<div style="text-align:right; margin:10px 30px 0 0;">
					       <img src="/images/kidsdrawing2.png" /><br/>
				</div>
		
			    <%@ include file="articlesocialandpdf.jsp" %>
		
				<ryctag:separator/>
				 
				<%-- ARTICLE MAIN TEXT --%> 			   
				<div style="font-size:.85em;margin-bottom:10px;">ARTICLE</div>
			    ${article.lastVersionRenderedContent}
			    <br/>
		    	<div style="text-align:right; margin:10px 30px 0 0;">
					       <img src="/images/kidsdrawing2.png" /><br/>
				</div>
			    
			    

				<%-- Sub articles --%>
				<c:if test="${! empty article.children}">
			       <%-- <ryctag:separator/> --%>
     			   <div style="font-size:1.15em;margin-bottom:10px; color:#bc1c1c;">SOUS-ARTICLES</div>
			       <ryc:articlesList parentArticle="${article}"/>
                </c:if>

			    <%@ include file="articlesocialandpdf.jsp" %>

			    
				<%-- TO CLASSIFY TEXT --%> 		
				<ryc:conditionDisplay privilege="VIEW_UNPUBLISHED_ARTICLE" >	   
					<ryctag:separator/>
     			    <div style="font-size:1.15em;margin-bottom:10px; color:#bc1c1c;">TEXTE A CLASSER DANS L'ARTICLE</div>
			        ${article.lastVersionRenderedToClassify}
			    </ryc:conditionDisplay>
			    
	  	</div>
	  </c:when>
	
	  <c:otherwise> <%-- We do not show the text, but the countdown --%>
		<c:if test="${displayDate != null}">
  		  <br/><br/><br/>
          <!-- COUNT DOWN -->
		   <div id="defaultCountdown" ></div>
			<script type="text/javascript">
			    $(document).ready(function () {
						var publishDay = new Date('${publishYear}', '${publishMonth}', '${publishDay}');
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

<!-- Used by javascript (generatedpdf.js) -->
<input type = "hidden" name = "articleId" value="${article.id}" >
<input type = "hidden" name = "articleUrl" value="${article.url}" >

</div> <%-- Ink inkspot bg image div --%>
</body>
</html>   

