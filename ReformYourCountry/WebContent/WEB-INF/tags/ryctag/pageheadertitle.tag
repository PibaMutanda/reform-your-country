<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="title" required="true" type="java.lang.String"%>

<head>
	<title>${title}</title>
</head>
  <%-- ***************** START Title Bar ***************** --%>
<!-- <div id="titleContainer">
 --><div class="tools">
	<div class="holder">
		<div class="frame">
			<h1>${title}</h1>
				<span class="breadcrumb">
					<jsp:doBody/>
				</span>
		</div><!-- end frame -->
	</div><!-- end holder -->
</div><!-- end tools -->
<%-- ***************** - END Title Bar - ***************** --%>
<c:if test="${not empty message}">
    <span class="errorMessage">${message}</span>
    <br />
</c:if>
<c:if test="${not empty param.message}">
    <span class="errorMessage">${param.message}</span>
    <br />
</c:if>

<!-- </div>
 -->