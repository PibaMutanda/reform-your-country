<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="title" required="true"%>

<head>
	<title>${title}</title>
</head>
  <!-- ***************** START Title Bar ***************** -->
<div class="tools">
	<div class="holder">
		<div class="frame">
			<h1>${title}</h1>
				<span class="breadcrumb">
					<jsp:doBody/>
				</span>
		</div><!-- end frame -->
			
	</div><!-- end holder -->
</div><!-- end tools -->
<!-- ***************** - END Title Bar - ***************** -->
	

<div><c:if test='${message != null}'>${message}</c:if><c:if test='${param.message != null}'>${param.message}</c:if><br/></div>
	