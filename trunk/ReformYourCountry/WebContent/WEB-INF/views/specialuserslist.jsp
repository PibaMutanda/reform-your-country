<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<html>
<head>
</head>
<body>
<ryctag:pageheadertitle title="Liste des Partis politiques et Associations"/>
<br>
<c:forEach items="${values}"   var="specialType">

  <c:if test='${! empty userMapGroupByType[specialType.name]}'>
  <c:set var="tablename" value="${specialType.familyName}" scope ="request"/>
  <c:set var='listUserAndVotes' value='${userMapGroupByType[specialType.name]}' scope ="request"/>
  <c:set var='isVoteResultPage' value='${isVoteResultPage}' scope ="request"/>
  <%@include file="specialuserlistfragment.jsp" %>	  
  </c:if>
</c:forEach>


<p>Si vous désirez que votre parti ou association soit représenté ici, merci d'utiliser  <a href="contact">le formulaire de contact</a>.</p>

</body>
</html>