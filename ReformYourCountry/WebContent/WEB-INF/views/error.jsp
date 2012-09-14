<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="reformyourcountry.web.ContextUtil"%>
<%@ page import="reformyourcountry.exception.*"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/ext/jquery-1.8.0.min.js"></script><%-- jquery depandencies --%>
<script src="js/ext/jquery-ui-1.8.23.custom.min.js"></script>
<script src="js/int/redirect.js"></script>
<script type ="text/javascript">
  var redirectUrl = "${redirectUrl}";  // Used by redirect.js
</script>

<title>Error page</title>
</head>
<body>
	<center>
		<h2>Ooooops!</h2>
		<p>Vous avez cassé le système...</p>
		
		<img src="images/404.jpg" />
		
    	<c:if test="${redirectUrl != null}">
           <p>Vous allez automatiquement être redirigé vers la page d'accueil dans <span id ="count"></span> secondes.</p>
        </c:if>
	</center>
    
    
	<c:if test="${stackTrace != null}">
		Exception:<br/>
		<font size="2" color="red">
		  <pre>  <!-- To take the line returns into account in the stack trace -->
		    ${stackTrace}
		  </pre>
		</font>
	</c:if>
</body>
</html>