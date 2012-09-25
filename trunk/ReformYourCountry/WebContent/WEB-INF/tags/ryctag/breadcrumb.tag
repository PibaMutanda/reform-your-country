<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="breadcrumb"%>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>
<div class="tools">
	<div class="holder">
		<div class="frame">
			<h1>${title}</h1>
			
			
			
			<!--|||||||||||||||||||||||||||     Ne sera utilie que pour les articles               |||||||||||||||||||||||||||||-->
			<c:if test ="${breadcrumb != null}">
				<p class="breadcrumb"><a href="home">Home</a><a href="home">Pages</a><span class='current_crumb'>Accueil</span></p>
			</c:if>
			</div><!-- end frame -->
			
		</div><!-- end holder -->
	</div><!-- end tools -->
	<!-- ***************** - END Title Bar - ***************** -->
	<div class="body-template">
		<input type="hidden" name="message" value="${message }"/>
		<div>${message}<br/></div>
			<jsp:doBody/>
	</div>
