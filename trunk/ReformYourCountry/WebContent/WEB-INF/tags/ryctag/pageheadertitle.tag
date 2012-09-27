<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="breadcrumb"%>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>
<header>
	<title>${title}</title>
</header>
  <!-- ***************** START Title Bar ***************** -->
<div class="tools">
	<div class="holder">
		<div class="frame">
			<h1>${title}</h1>
			
			<c:if test ="${breadcrumb != null}">
				<p class="breadcrumb">
					<jsp:doBody/>
				</p>
			</c:if>
		</div><!-- end frame -->
			
	</div><!-- end holder -->
</div><!-- end tools -->
<!-- ***************** - END Title Bar - ***************** -->
	

<div><c:if test='${message != null}'>${message}</c:if><c:if test='${param.message != null}'>${param.message}</c:if><br/></div>
	