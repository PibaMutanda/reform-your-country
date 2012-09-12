<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
<div class="logo" ><a href="home">enseignement2.be</a></div>



<div class="login-link" >
      <c:choose>
        <c:when test="${current.user!=null}">
          <c:out value="${current.user.userName}"></c:out><br />
          <a id="logout" href="logout">Déconnexion</a>
        </c:when>
        <c:otherwise>
            <a id="login" style="cursor:pointer;">Connexion</a><br />
            <a href="register">Créer un compte</a>
         </c:otherwise>
     </c:choose>
    
</div>


<div id ="logindialog" style = "display:none;">
<label id ="errorMsg" style ="color:red;"></label>
<%@include file="/WEB-INF/views/login.jsp" %>	
</div>

</body>