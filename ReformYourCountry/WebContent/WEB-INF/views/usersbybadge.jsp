<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="css/int/content.css"  type="text/css" />
</head>
<body>
  
	<ryctag:badge badgeType="${badgeType}"/> ${badgeType.description} - ${badges.size()}: utilisateurs ont gagné ce badge. <br/>
	           
   	<c:forEach items="${badges}" var="badge">
    	${badge.user.firstName}<br />
	</c:forEach>
      
      
</body>
</html>