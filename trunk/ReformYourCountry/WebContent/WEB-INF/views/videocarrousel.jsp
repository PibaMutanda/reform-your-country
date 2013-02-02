<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 

<c:if test="${fn:length(videoList) gt 0}"> 

    <br/>
    <ryctag:separator/>
    <br/><%-- If we show at least a video, add some space before --%>

    <link href="css/ext/jsCarousel-2.0.0.css" rel="stylesheet" type="text/css" />
	<script src="js/ext/jsCarousel-2.0.0.js" type="text/javascript"></script>
	<c:if test="${fn:length(videoList) gt 3}">
	    <script type="application/javascript">
	        $(document).ready(function() {
	            $('#carouselh').jsCarousel({ autoscroll: false, circular: false, masked: false, itemstodisplay: 3, orientation: 'h' });
	        });       
	    </script>
	</c:if>
	
	
	<%-- VIDEOS --%>
	<div id="carouselh" >
		<c:forEach items="${videoList}" var="video">
			<div class="inline-block" style="margin-left:10px;">
				<iframe width="240" height="155" src="https://www.youtube-nocookie.com/embed/${video.idOnHost}?rel=0&hd=1" frameborder="0" allowfullscreen seamless></iframe>
			</div>
		</c:forEach>
	</div>
         

    
</c:if>   