<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="logo"><a href="home">enseignement2.be</a></div>



<div class="login-link" >
     <c:choose>
        <c:when test="${current.user!=null}">
          <c:out value="${current.user.userName}"></c:out><br />
          <a href="home">Deconnexion</a></c:when>
        <c:otherwise><a href="login">Connexion</a></c:otherwise>
     </c:choose>
     Current: ${current}
</div>
