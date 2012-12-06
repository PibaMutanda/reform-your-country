<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="reformyourcountry.web.ContextUtil"%>
<%@ page import="reformyourcountry.exception.*"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.*"%>
<html>
<head>
<script src="js/int/redirect.js"></script>
<script type ="text/javascript">
  var redirectUrl = "${redirectUrl}";  // Used by redirect.js
</script>

<title>Error page</title>
</head>
<body>
	<center>
		<font face="Arial">
		<h2>Ooooops!</h2>
		<p>Vous avez cassé le système...</p>
		
		<img src="/images/error.jpg" />
		
        <p>${message}</p>
        
    	<c:if test="${redirectUrl != null}">
           <p>Vous allez automatiquement être redirigé vers la page d'accueil dans <span id ="count"></span> secondes.</p>
        </c:if>
        </font>
	</center>

	<c:if test="<%=ContextUtil.devMode%>">    
		<c:if test="${stackTrace != null}">
			Exception:<br/>
			<font size="2" color="red">
			  <pre>  <!-- To take the line returns into account in the stack trace -->
			     ${stackTrace}
			  </pre>
			</font>
		</c:if> 
	</c:if>
</body>
</html>